package com.gates.standstrong.jobs;

import com.gates.standstrong.domain.award.Award;
import com.gates.standstrong.domain.award.AwardConstants;
import com.gates.standstrong.domain.award.AwardService;
import com.gates.standstrong.domain.data.activity.ActivityService;
import com.gates.standstrong.domain.data.activity.Movement;
import com.gates.standstrong.domain.data.audio.AudioService;
import com.gates.standstrong.domain.data.audio.Speech;
import com.gates.standstrong.domain.data.proximity.ProximityChart;
import com.gates.standstrong.domain.data.proximity.ProximityService;
import com.gates.standstrong.domain.data.proximity.SelfCare;
import com.gates.standstrong.domain.mother.Mother;
import com.gates.standstrong.domain.mother.MotherService;
import com.gates.standstrong.utils.DateUtils;
import com.gates.standstrong.utils.StringUtils;
import com.gates.standstrong.viber.WebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class Scheduling {

    private MotherService motherService;

    private AudioService audioService;

    private AwardService awardService;

    private ActivityService activityService;

    private ProximityService proximityService;

    private WebhookService webhookService;

    @Inject
    public Scheduling(MotherService motherService, AudioService audioService, AwardService awardService, ActivityService activityService, ProximityService proximityService, WebhookService webhookService) {
        this.motherService = motherService;
        this.audioService = audioService;
        this.awardService = awardService;
        this.activityService = activityService;
        this.proximityService = proximityService;
        this.webhookService = webhookService;
    }

    /*
        Running the job at 2 AM UTC
     */
    @Scheduled(cron="0 0 2 * * *")
    public void run(){

        log.info("Running social security award");
        generateSocialSupportAwards();

        log.info("Running movement award");
        generateMovementAwards();

        log.info("Running Self Care award");
        generateSelfCareAwards();

        log.info("Running Routine award");
        generateRoutineAwards();

        log.info("Running Bonus award");
        generateBonusAwards();

    }

    private void generateRoutineAwards() {

        for (Mother mother : motherService.findAll()) {

            if (awardService.hasHighestAward(mother, AwardConstants.AWARD_ROUTINE)) {
                log.info("Routine Awards for mom {} already reached to level 3", mother.getIdentificationNumber());
                continue;
            }

            Award awardDb = awardService.getTopAward(mother.getId(), AwardConstants.AWARD_ROUTINE);
            int nextLevel = AwardConstants.AWARD_LEVEL_ONE;
            int savedLevel = 0;
            if (awardDb != null) {
                savedLevel = awardDb.getAwardLevel();
            }

            log.info("Generating Routine Awards for mom {}", mother.getIdentificationNumber());
            List<Date> proximityDates = proximityService.getDates(mother.getId());

            if (proximityDates.size() < 2) {
                continue;
            }

            String[] charts = new String[proximityDates.size()];
            Arrays.fill(charts, "");
            List<ProximityChart> proximityCharts = proximityService.getProximityChartsByMother(mother.getId());
            if (proximityCharts != null && proximityCharts.size() > 0) {

                int i = 0;
                String previousChartDay = proximityCharts.get(0).getChartDay();
                for (ProximityChart proximityChart : proximityCharts) {
                    if (!previousChartDay.equals(proximityChart.getChartDay())) {

                        i++;
                        previousChartDay = proximityChart.getChartDay();


                    }

                    charts[i] = StringUtils.fill(charts[i], Integer.parseInt(proximityChart.getChartHour()), proximityChart.getChartValue());

                }
            }

            int consecutiveValue =0;

            for (int i = 0; i < charts.length; i++) {

                charts[i] = org.apache.commons.lang3.StringUtils.rightPad(charts[i], 24, "0");

                if(i == 0) {
                    continue;
                }

                if(StringUtils.isMatch( charts[i-1], charts[i], 80) && consecutiveValue == 0 ){

                    log.info("Routine Level 1 award achieved.");

                    if(nextLevel > savedLevel ){

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_ROUTINE, AwardConstants.AWARD_LEVEL_ONE,proximityDates.get(i).toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    nextLevel = AwardConstants.AWARD_LEVEL_TWO;
                    consecutiveValue =1;
                    continue;
                }

                if(StringUtils.isMatch( charts[i-1], charts[i], 80) && consecutiveValue == 1 ){

                    log.info("Routine Level 2 award achieved.");
                    if(nextLevel > savedLevel){

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_ROUTINE, AwardConstants.AWARD_LEVEL_TWO,proximityDates.get(i).toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    nextLevel = AwardConstants.AWARD_LEVEL_THREE;
                    consecutiveValue =2;
                    continue;
                }

                if(StringUtils.isMatch( charts[i-1], charts[i], 80) && consecutiveValue == 2 ){

                    log.info("Routine Level 3 award achieved.");

                    if(nextLevel > savedLevel){
                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_ROUTINE, AwardConstants.AWARD_LEVEL_THREE,proximityDates.get(i).toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    break;
                }
                consecutiveValue = 0;
                nextLevel = AwardConstants.AWARD_LEVEL_ONE;
            }
        }
    }





    private void generateBonusAwards() {

        for(Mother mother:motherService.findAll()) {

            if (awardService.hasHighestAward(mother, AwardConstants.AWARD_BONUS)) {
                log.info("Bonus Awards for mom {} already reached to level 3", mother.getIdentificationNumber());
                continue;
            }

            log.info("Generating Bonus Awards for mom {}", mother.getIdentificationNumber());


            Award awardDb = awardService.getTopAward(mother.getId(), AwardConstants.AWARD_BONUS);
            int nextBonusLevel = AwardConstants.AWARD_LEVEL_ONE;

            if (awardDb != null) {
                nextBonusLevel = awardDb.getAwardLevel() + 1;
            }

            while (nextBonusLevel != 3) {

                if (awardService.satisfiesNextBonusLevel(mother.getId(), nextBonusLevel)) {

                    Award anyAward = awardService.getAnyAward(mother.getId(), nextBonusLevel);

                    log.info("Bonus Award Level {} award achieved.", nextBonusLevel);

                    Award award = awardService.buildAward(mother, AwardConstants.AWARD_BONUS, nextBonusLevel, anyAward.getAwardForDate());
                    awardService.save(award);

                    log.info("Saved");

                    nextBonusLevel++;

                } else {
                    break;
                }
            }
        }
    }

    private void generateSelfCareAwards() {

        for(Mother mother:motherService.findAll()){

            if (awardService.hasHighestAward(mother, AwardConstants.AWARD_SELF_CARE)) {
                log.info("Self Care Awards for mom {} already reached to level 3", mother.getIdentificationNumber());
                continue;
            }

            Award awardDb = awardService.getTopAward(mother.getId(), AwardConstants.AWARD_SELF_CARE);
            int nextLevel = AwardConstants.AWARD_LEVEL_ONE;
            int savedLevel = 0;
            if (awardDb != null) {
                savedLevel = awardDb.getAwardLevel();
            }

            log.info("Generating Self Care Awards for mom {}", mother.getIdentificationNumber());
            List<SelfCare> selfCaredDays = proximityService.getSelfCaredDays(mother.getId());

            int consecutiveValue = 0;
            Date previousDay=null;
            for(SelfCare selfCare: selfCaredDays){
                log.info("Capture Day: {} ", selfCare.getChartDay().toString());

                if( consecutiveValue == 0 ){

                    log.info("Self Care Level 1 award achieved.");

                    if(nextLevel>savedLevel){
                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_SELF_CARE, AwardConstants.AWARD_LEVEL_ONE, selfCare.getChartDay().toLocalDate());
                        awardService.save(award);

                        log.info("Saved");
                        savedLevel++;
                    }
                    nextLevel = AwardConstants.AWARD_LEVEL_TWO;
                    previousDay = selfCare.getChartDay();
                    consecutiveValue =1;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(selfCare.getChartDay()) && consecutiveValue == 1 ){

                    log.info("Self Care Level 2 award achieved.");
                    if(nextLevel > savedLevel){
                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_SELF_CARE, AwardConstants.AWARD_LEVEL_TWO, selfCare.getChartDay().toLocalDate());
                        awardService.save(award);

                        log.info("Saved");
                        savedLevel++;

                    }
                    nextLevel = AwardConstants.AWARD_LEVEL_THREE;
                    previousDay = selfCare.getChartDay();
                    consecutiveValue =2;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(selfCare.getChartDay())){

                    log.info("Self Care Level 3 award achieved.");
                    if(nextLevel == AwardConstants.AWARD_LEVEL_THREE){
                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_SELF_CARE, AwardConstants.AWARD_LEVEL_THREE, selfCare.getChartDay().toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    break;
                }

                nextLevel=AwardConstants.AWARD_LEVEL_ONE;
                previousDay=null;
                consecutiveValue=0;

            }
        }
    }

    private void generateMovementAwards() {

        for(Mother mother:motherService.findAll()){

            if (awardService.hasHighestAward(mother, AwardConstants.AWARD_MOVEMENT)) {
                log.info("Movement Awards for mom {} already reached to level 3", mother.getIdentificationNumber());
                continue;
            }

            Award awardDb = awardService.getTopAward(mother.getId(), AwardConstants.AWARD_MOVEMENT);
            int nextLevel = AwardConstants.AWARD_LEVEL_ONE;
            int savedLevel = 0;

            if (awardDb != null) {
                savedLevel = awardDb.getAwardLevel();
            }

            log.info("Generating Movement Awards for mom {}", mother.getIdentificationNumber());
            List<Movement> movements = activityService.getMovements(mother.getId());

            int consecutiveValue = 0;
            Date previousDay = null;

            for(Movement movement: movements){
                log.info("Capture Day: {} ", movement.getCaptureDate().toString());
                log.info("Number of movement that day: {} ", movement.getMovementCount());

                if(movement.getMovementCount()>=1 && consecutiveValue ==0 ){

                    log.info("Movement Level 1 award achieved.");

                    if(nextLevel > savedLevel){

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_MOVEMENT, AwardConstants.AWARD_LEVEL_ONE, movement.getCaptureDate().toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    nextLevel = AwardConstants.AWARD_LEVEL_TWO;
                    previousDay = movement.getCaptureDate();
                    consecutiveValue =1;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(movement.getCaptureDate()) && movement.getMovementCount()>=2 && consecutiveValue ==1 ){

                    log.info("Movement Level 2 award achieved.");
                    if(nextLevel > savedLevel){

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_MOVEMENT, AwardConstants.AWARD_LEVEL_TWO, movement.getCaptureDate().toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    nextLevel = AwardConstants.AWARD_LEVEL_THREE;
                    previousDay = movement.getCaptureDate();
                    consecutiveValue =2;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(movement.getCaptureDate()) && movement.getMovementCount()>=3 && consecutiveValue ==2 ){

                    log.info("Movement Level 3 award achieved.");
                    if(nextLevel > savedLevel){

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_MOVEMENT, AwardConstants.AWARD_LEVEL_THREE, movement.getCaptureDate().toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    break;
                }
                previousDay = null;
                consecutiveValue=0;
                nextLevel = AwardConstants.AWARD_LEVEL_ONE;
            }
        }
    }


    private void generateSocialSupportAwards(){

        for(Mother mother:motherService.findAll()){

            if (awardService.hasHighestAward(mother, AwardConstants.AWARD_SOCIAL_SUPPORT)) {
                log.info("Social Support Awards for mom {} already reached to level 3", mother.getIdentificationNumber());
                continue;
            }

            Award awardDb = awardService.getTopAward(mother.getId(), AwardConstants.AWARD_SOCIAL_SUPPORT);
            int nextLevel = AwardConstants.AWARD_LEVEL_ONE;
            int savedLevel = 0;
            if (awardDb != null) {
                log.info("Social Support Awards for mom {} already reached to level {}", mother.getIdentificationNumber(), awardDb.getAwardLevel());
                savedLevel = awardDb.getAwardLevel();
            }


            log.info("Generating Social Support Awards for mom {}", mother.getIdentificationNumber());
            List<Speech> speeches = audioService.getSpeeches(mother.getId());

            int consecutiveValue = 0;
            Date previousDay = null;
            for(Speech speech: speeches){
                log.info("Capture Day: {} ", speech.getCaptureDate().toString());
                log.info("Number of speech that day: {} ", speech.getSpeechCount());

                if(speech.getSpeechCount()>=2 && consecutiveValue ==0 ){

                    log.info("Social Support Level 1 award achieved.");

                    if( nextLevel > savedLevel ){

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_SOCIAL_SUPPORT, AwardConstants.AWARD_LEVEL_ONE, speech.getCaptureDate().toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    nextLevel = AwardConstants.AWARD_LEVEL_TWO;
                    previousDay = speech.getCaptureDate();
                    consecutiveValue =1;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(speech.getCaptureDate()) && speech.getSpeechCount()>=4 && consecutiveValue ==1 ){

                    log.info("Social Support Level 2 award achieved.");
                    if(nextLevel > savedLevel){

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_SOCIAL_SUPPORT, AwardConstants.AWARD_LEVEL_TWO, speech.getCaptureDate().toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    nextLevel = AwardConstants.AWARD_LEVEL_THREE;
                    previousDay = speech.getCaptureDate();
                    consecutiveValue =2;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(speech.getCaptureDate()) && speech.getSpeechCount()>=6 && consecutiveValue ==2 ){

                    log.info("Social Support Level 3 award achieved.");
                    if( nextLevel > savedLevel ){

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_SOCIAL_SUPPORT, AwardConstants.AWARD_LEVEL_THREE, speech.getCaptureDate().toLocalDate());
                        awardService.save(award);

                        log.info("Saved");

                        savedLevel++;

                    }
                    break;
                }
                previousDay = null;
                consecutiveValue=0;
                nextLevel = AwardConstants.AWARD_LEVEL_ONE;
            }
        }
    }

    /*
        Running messaging service from 9AM to 8PM UTC.
     */
    @Scheduled(cron="0 0 4-17 * * *")
    public void runMessagingJob(){
        try {
            webhookService.setupWebHook();
            webhookService.sendMessages();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}