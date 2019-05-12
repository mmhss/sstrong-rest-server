package com.gates.standstrong.domain.viber;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {

    @Inject
    public MessageServiceImpl(MessageRepository messageRepository){
        super(messageRepository);
    }
}
