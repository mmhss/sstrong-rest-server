package com.gates.standstrong.controller;

import com.gates.standstrong.domain.post.Post;
import com.gates.standstrong.domain.post.PostService;
import com.gates.standstrong.domain.viber.Message;
import com.gates.standstrong.domain.viber.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private PostService postService;
    private MessageService messageService;

    @Inject
    public MessageController(PostService postService, MessageService messageService){
        this.postService = postService;
        this.messageService = messageService;
    }

    @PostMapping("/callback")
    public ResponseEntity<?> callback(@RequestBody String callback) {

        Message message = new Message();
        message.setCallback(callback);
        messageService.save(message);
        Post post = postService.buildPost(callback);
        postService.save(post);
        return ResponseEntity.ok("Saved");

    }
}
