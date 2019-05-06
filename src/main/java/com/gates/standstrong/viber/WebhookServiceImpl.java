package com.gates.standstrong.viber;

import com.gates.standstrong.domain.post.Post;
import com.gates.standstrong.domain.post.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.List;


@Service
@Slf4j
public class WebhookServiceImpl implements WebhookService{

    private PostService postService;

    @Inject
    public WebhookServiceImpl(PostService postService){

        this.postService = postService;

    }

    @Override
    public void setupWebHook() throws MalformedURLException {

        log.info("Setting up the webhook");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String input = "{\"auth_token\":\""+ WebhookConstants.VIBER_AUTH_TOKEN +"\",\"url\":\""+WebhookConstants.URL_STANDSTRONG_WEBHOOK+"\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(input, headers);

        ResponseEntity<String> response = restTemplate
                .exchange(WebhookConstants.URL_VIBER_WEB_SETUP, HttpMethod.POST, entity, String.class);

    }

    @Override
    public void sendMessages(){

        List<Post> pendingPosts =  postService.getPendingPostsToMothers();
        for (Post post:pendingPosts) {
            try {

                log.info(post.getMessage());

                if(sendMessage(post)){
                    post.setStatus(Post.STATUS_DELIVERED);
                    postService.update(post);

                    log.info("Message delivered.");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean sendMessage(Post post) throws MalformedURLException {

        if(post.getMother()==null){
            log.error("Mother not set for the post", post.getId());
            return false;
        }

        if(post.getMother()!=null && org.apache.commons.lang3.StringUtils.isEmpty(post.getMother().getViberId())){
            log.info("Mom {} has no Viber account set up", post.getMother().getIdentificationNumber());
            return false;
        }

        log.info("Sending message to mom {}", post.getMother().getIdentificationNumber());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        String input = "{\n" +
                "\"auth_token\": \""+WebhookConstants.VIBER_AUTH_TOKEN+"\",\n" +
                "\"receiver\": \""+post.getMother().getViberId()+"\",\n" +
                "\"type\": \"text\",\n" +
                "\"text\": \""+post.getMessage()+"\",\n" +
                "\"sender\":{\n" +
                "      \"name\":\"StandStrong\",\n" +
                "   }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(input, headers);

        ResponseEntity<String> response = restTemplate
                .exchange(WebhookConstants.URL_VIBER_WEB_SEND_MESSAGE, HttpMethod.POST, entity, String.class);

        if(response.getStatusCodeValue()==200){
            return true;
        }

        return false;

    }






}
