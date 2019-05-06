package com.gates.standstrong.viber;

import com.gates.standstrong.domain.post.Post;
import com.gates.standstrong.domain.post.PostService;
import com.gates.standstrong.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


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

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean sendMessage(Post post) throws ParseException {

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

        JSONObject payload = new JSONObject();
        payload.put("auth_token", WebhookConstants.VIBER_AUTH_TOKEN);
        payload.put("receiver", post.getMother().getViberId() );
        payload.put("text", post.getMessage());

        if(post.getMedia()!=null) {
            payload.put("type", "picture");
            payload.put("media", post.getMedia());
        }else{
            payload.put("type", "text");
        }

        Map m = new LinkedHashMap(1);
        m.put("name", "StandStrong");

        // putting address to JSONObject
        payload.put("sender", m);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(payload.toJSONString(), headers);

        ResponseEntity<String> response = restTemplate
                .exchange(WebhookConstants.URL_VIBER_WEB_SEND_MESSAGE, HttpMethod.POST, entity, String.class);

        log.info(response.toString());


        String status = JSONUtils.parse(response.getBody(), "status");

        if(response.getStatusCodeValue()==200 && status.equals("0")){
            return true;
        }

        return false;

    }






}
