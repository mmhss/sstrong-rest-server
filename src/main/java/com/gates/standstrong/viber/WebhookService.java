package com.gates.standstrong.viber;

import com.gates.standstrong.domain.post.Post;

import java.net.MalformedURLException;

public interface WebhookService {

    void setupWebHook() throws MalformedURLException;
    boolean sendMessage(Post post) throws MalformedURLException;
    void sendMessages();
}
