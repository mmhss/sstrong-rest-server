package com.gates.standstrong.jobs;

import com.gates.standstrong.domain.award.Award;
import com.gates.standstrong.domain.award.AwardConstants;
import com.gates.standstrong.domain.award.AwardService;
import com.gates.standstrong.domain.data.activity.ActivityService;
import com.gates.standstrong.domain.data.activity.Movement;
import com.gates.standstrong.domain.data.audio.AudioService;
import com.gates.standstrong.domain.data.audio.Speech;
import com.gates.standstrong.domain.mother.Mother;
import com.gates.standstrong.domain.mother.MotherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Scheduling {

    private MotherService motherService;

    private AudioService audioService;

    private AwardService awardService;

    private ActivityService activityService;

//    private AwardExecutionService awardExecutionService;

    @Inject
    public Scheduling(MotherService motherService, AudioService audioService, AwardService awardService, ActivityService activtiyService) {
        this.motherService = motherService;
        this.audioService = audioService;
        this.awardService = awardService;
        this.activityService = activtiyService;
    }

    @Scheduled(cron="0 8 * * * *")
    public void run(){

        log.info("Running social security award job");
        generateSocialSecurityAwards();

        log.info("Running movement award job");
        generateMovementAwards();

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
                    }
                    consecutiveValue =1;
                    continue;
                }

                if(movement.getMovementCount()>=2 && consecutiveValue ==1 ){

                    log.info("Movement Level 2 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==1) {
                        Award award = new Award();
                        award.setAwardLevel(2);
                        award.setAwardForDate(movement.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_MOVEMENT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);
                    }
                    consecutiveValue =2;
                    continue;
                }

                if(movement.getMovementCount()>=3 && consecutiveValue ==2 ){

                    log.info("Movement Level 3 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==2) {
                        Award award = new Award();
                        award.setAwardLevel(3);
                        award.setAwardForDate(movement.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_MOVEMENT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);
                    }
                    break;
                }

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
                    }
                    consecutiveValue =1;
                    continue;
                }

                if(speech.getSpeechCount()>=4 && consecutiveValue ==1 ){

                    log.info("Social Support Level 2 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==1) {
                        Award award = new Award();
                        award.setAwardLevel(2);
                        award.setAwardForDate(speech.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_SOCIAL_SUPPORT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);
                    }
                    consecutiveValue =2;
                    continue;
                }

                if(speech.getSpeechCount()>=6 && consecutiveValue ==2 ){

                    log.info("Social Support Level 3 award achieved.");
                    if(awardDb!=null && awardDb.getAwardLevel()==2) {
                        Award award = new Award();
                        award.setAwardLevel(3);
                        award.setAwardForDate(speech.getCaptureDate().toLocalDate());
                        award.setAwardType(AwardConstants.AWARD_SOCIAL_SUPPORT);
                        award.setMother(mother);
                        awards.add(award);
                        awardService.save(award);
                    }
                    break;
                }

                consecutiveValue=0;

            }
        }


    }

//    private List<Date> getCaptureDatesToProcess(Mother mother) {
//        log.info("Generating social support award for mom %s", mother.getIdentificationNumber());
//
//        List<Date> capturedDates = audioService.getAudioCapturedDates(mother.getId());
//        List<Date> ranForCapturedDates = awardExecutionService.getRanForCaptureDates("SocialSupport", mother.getId());
//        List<Date> awardForCaptureDates = new ArrayList<>();
//
//        for(Date captureDate: capturedDates){
//            boolean found = false;
//            for(Date ranForCaptureDate:ranForCapturedDates ){
//               if(captureDate.equals(ranForCaptureDate)){
//                   found=true;
//                   break;
//               }
//            }
//
//            if(found==false){
//                awardForCaptureDates.add(captureDate);
//            }
//        }
//        return awardForCaptureDates;
//    }
}