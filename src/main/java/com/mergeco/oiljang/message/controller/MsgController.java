package com.mergeco.oiljang.message.controller;

import com.mergeco.oiljang.message.dto.MsgInsertDTO;
import com.mergeco.oiljang.message.service.MsgService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MsgController {

    public final MsgService msgService;

    public MsgController(MsgService msgService) {
        this.msgService = msgService;
    }

//    @GetMapping("/regist")
//    public void registPage(){}

    @PostMapping("/messages")
    public ResponseEntity<String> msgAnswer(@RequestBody MsgInsertDTO msgInfo){
        try{
            msgService.insertMsg(msgInfo);
            System.out.println("controller : " + msgInfo);
            return ResponseEntity.status(HttpStatus.CREATED).body("Message created successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating message: " + e.getMessage());
        }

    }
}
