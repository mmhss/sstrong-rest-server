package com.gates.standstrong.viber;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViberMessage {

    private String token;
    private String type;
    private String receiver;
    private String text;
    private String senderName;
    private String media;
}
