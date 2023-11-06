package com.mergeco.oiljang.message.controller;

import com.mergeco.oiljang.message.dto.MsgDTO;
import com.mergeco.oiljang.message.service.MsgService;
import org.springframework.web.bind.annotation.PostMapping;

public class MsgController {

    public final MsgService msgService;

    public MsgController(MsgService msgService) {
        this.msgService = msgService;
    }

//    @GetMapping("/regist")
//    public void registPage(){}

    @PostMapping("/regist")
    public String msgAnswer(MsgDTO msgInfo){
        msgService.insertMsg(msgInfo);

        return "redirect:/";
    }
}
