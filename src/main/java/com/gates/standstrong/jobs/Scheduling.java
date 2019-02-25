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
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.Date;
import java.util.ArrayList;
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

    @Inject
    public Scheduling(MotherService motherService, AudioService audioService, AwardService awardService, ActivityService activityService, ProximityService proximityService) {
        this.motherService = motherService;
        this.audioService = audioService;
        this.awardService = awardService;
        this.activityService = activityService;
        this.proximityService = proximityService;
    }


    @Scheduled(fixedDelay = 10000000)
    public void run(){

        log.info("Running social security award");
        generateSocialSecurityAwards();

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

            List<Award> awards = new ArrayList<>();

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

                    if(awardDb==null){

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_ROUTINE, AwardConstants.AWARD_LEVEL_ONE,proximityDates.get(i).toLocalDate());
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    consecutiveValue =1;
                    continue;
                }

                if(StringUtils.isMatch( charts[i-1], charts[i], 80) && consecutiveValue == 1 ){

                    awardDb = awardService.getTopAward(mother.getId(), AwardConstants.AWARD_ROUTINE);

                    log.info("Routine Level 2 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel() < AwardConstants.AWARD_LEVEL_TWO) {

                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_ROUTINE, AwardConstants.AWARD_LEVEL_TWO,proximityDates.get(i).toLocalDate());
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    consecutiveValue =2;
                    continue;
                }

                if(StringUtils.isMatch( charts[i-1], charts[i], 80) && consecutiveValue == 2 ){

                    awardDb = awardService.getTopAward(mother.getId(), AwardConstants.AWARD_ROUTINE);

                    log.info("Routine Level 3 award achieved.");

                    if(awardDb!=null && awardDb.getAwardLevel() < AwardConstants.AWARD_LEVEL_THREE) {
                        Award award = awardService.buildAward(mother, AwardConstants.AWARD_ROUTINE, AwardConstants.AWARD_LEVEL_THREE,proximityDates.get(i).toLocalDate());
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    break;
                }
                consecutiveValue=0;
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
            int nextBonusLevel = 1;

            if (awardDb != null) {
                nextBonusLevel = awardDb.getAwardLevel() + 1;
            }

            while (nextBonusLevel != 3) {

                if (awardService.satisfiesNextBonusLevel(mother.getId(), nextBonusLevel)) {

                    Award anyAward = awardService.getAnyAward(mother.getId(), nextBonusLevel);

                    log.info("Bonus Award Level {} award achieved.", nextBonusLevel);

                    Award award = new Award();
                    award.setAwardLevel(nextBonusLevel);
                    award.setAwardType(AwardConstants.AWARD_BONUS);
                    award.setMother(mother);
                    award.setAwardForDate(anyAward.getAwardForDate());
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

            List<Award> awards = new ArrayList<>();

            log.info("Generating Self Care Awards for mom {}", mother.getIdentificationNumber());
            List<SelfCare> selfCaredDays = proximityService.getSelfCaredDays(mother.getId());

            int consecutiveValue = 0;
            Date previousDay=null;
            for(SelfCare selfCare: selfCaredDays){
                log.info("Capture Day: {} ", selfCare.getChartDay().toString());

                if( consecutiveValue == 0 ){

                    log.info("Self Care Level 1 award achieved.");

                    if(awardDb==null){
                        Award award = new Award();
                        award.setAwardLevel(1);
                        award.setAwardForDate(selfCare.getChartDay().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_SELF_CARE);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");
                    }
                    previousDay = selfCare.getChartDay();
                    consecutiveValue =1;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(selfCare.getChartDay()) && consecutiveValue == 1 ){

                    log.info("Self Care Level 2 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==1) {
                        Award award = new Award();
                        award.setAwardLevel(2);
                        award.setAwardForDate(selfCare.getChartDay().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_SELF_CARE);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    previousDay = selfCare.getChartDay();
                    consecutiveValue =2;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(selfCare.getChartDay())){

                    log.info("Self Care Level 3 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==2) {
                        Award award = new Award();
                        award.setAwardLevel(3);
                        award.setAwardForDate(selfCare.getChartDay().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_SELF_CARE);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    break;
                }

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

            List<Award> awards = new ArrayList<>();

            log.info("Generating Movement Awards for mom {}", mother.getIdentificationNumber());
            List<Movement> movements = activityService.getMovements(mother.getId());

            int consecutiveValue = 0;
            Date previousDay = null;

            for(Movement movement: movements){
                log.info("Capture Day: {} ", movement.getCaptureDate().toString());
                log.info("Number of movement that day: {} ", movement.getMovementCount());

                if(movement.getMovementCount()>=1 && consecutiveValue ==0 ){

                    log.info("Movement Level 1 award achieved.");

                    if(awardDb==null){
                        Award award = new Award();
                        award.setAwardLevel(1);
                        award.setAwardForDate(movement.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_MOVEMENT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    previousDay = movement.getCaptureDate();
                    consecutiveValue =1;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(movement.getCaptureDate()) && movement.getMovementCount()>=2 && consecutiveValue ==1 ){

                    log.info("Movement Level 2 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==1) {
                        Award award = new Award();
                        award.setAwardLevel(2);
                        award.setAwardForDate(movement.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_MOVEMENT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    previousDay = movement.getCaptureDate();
                    consecutiveValue =2;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(movement.getCaptureDate()) && movement.getMovementCount()>=3 && consecutiveValue ==2 ){

                    log.info("Movement Level 3 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==2) {
                        Award award = new Award();
                        award.setAwardLevel(3);
                        award.setAwardForDate(movement.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_MOVEMENT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    break;
                }
                previousDay = null;
                consecutiveValue=0;

            }
        }
    }


    private void generateSocialSecurityAwards(){

        for(Mother mother:motherService.findAll()){

            if (awardService.hasHighestAward(mother, AwardConstants.AWARD_SOCIAL_SUPPORT)) {
                log.info("Social Support Awards for mom {} already reached to level 3", mother.getIdentificationNumber());
                continue;
            }

            Award awardDb = awardService.getTopAward(mother.getId(), AwardConstants.AWARD_SOCIAL_SUPPORT);

            List<Award> awards = new ArrayList<>();

            log.info("Generating Social Support Awards for mom {}", mother.getIdentificationNumber());
            List<Speech> speeches = audioService.getSpeeches(mother.getId());

            int consecutiveValue = 0;
            Date previousDay = null;
            for(Speech speech: speeches){
                log.info("Capture Day: {} ", speech.getCaptureDate().toString());
                log.info("Number of speech that day: {} ", speech.getSpeechCount());

                if(speech.getSpeechCount()>=2 && consecutiveValue ==0 ){

                    log.info("Social Support Level 1 award achieved.");

                    if(awardDb==null){
                        Award award = new Award();
                        award.setAwardLevel(1);
                        award.setAwardForDate(speech.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_SOCIAL_SUPPORT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    previousDay = speech.getCaptureDate();
                    consecutiveValue =1;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(speech.getCaptureDate()) && speech.getSpeechCount()>=4 && consecutiveValue ==1 ){

                    log.info("Social Support Level 2 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==1) {
                        Award award = new Award();
                        award.setAwardLevel(2);
                        award.setAwardForDate(speech.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_SOCIAL_SUPPORT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    previousDay = speech.getCaptureDate();
                    consecutiveValue =2;
                    continue;
                }

                if(previousDay!=null && DateUtils.addDays(previousDay, 1).equals(speech.getCaptureDate()) && speech.getSpeechCount()>=6 && consecutiveValue ==2 ){

                    log.info("Social Support Level 3 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==2) {
                        Award award = new Award();
                        award.setAwardLevel(3);
                        award.setAwardForDate(speech.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_SOCIAL_SUPPORT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);

                        log.info("Saved");

                    }
                    break;
                }
                previousDay = null;
                consecutiveValue=0;
            }
        }


    }
}