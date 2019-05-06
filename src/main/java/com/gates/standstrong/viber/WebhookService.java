package com.gates.standstrong.viber;

import com.gates.standstrong.domain.post.Post;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;

public interface WebhookService {

    void setupWebHook() throws MalformedURLException;
    boolean sendMessage(Post post) throws MalformedURLException, ParseException;
    void sendMessages();
}
