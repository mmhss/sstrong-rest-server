package com.gates.standstrong.controller;

import com.gates.standstrong.utils.WebhookResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/webhook")
public class WebHookController {

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    WebhookResponse webhook(@RequestBody String body){

        System.out.println(body);

        return new WebhookResponse("Hello! " + body, "Text " + body);
    }
}
