package com.mergeco.oiljang.message.controller;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.message.dto.MsgInsertDTO;
import com.mergeco.oiljang.message.dto.MsgProUserInfoDTO;
import com.mergeco.oiljang.message.dto.MsgUserDTO;
import com.mergeco.oiljang.message.service.MsgService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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



    @GetMapping("/messages/{msgCode}/{token}/{userCode}")
    public ResponseEntity<ResponseMessage> selectSenderReceiver(@RequestBody MsgUserDTO msgUserDTO, @PathVariable int msgCode, @PathVariable String token, @PathVariable UUID userCode){

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            List<MsgUserDTO> msgUserDTOList = msgService.selectSenderReceiver(msgCode, token, userCode);
            System.out.println("controller : " + msgUserDTO);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("msgUserList", msgUserDTOList);

            ResponseMessage responseMessage = new ResponseMessage(200,"쪽지 유저 정보" ,responseMap);

            return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

//    @GetMapping("/messages/{msgCode}")
//    public ResponseEntity<ResponseMessage> selectMsgDetail(@PathVariable int msgCode){
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        List<MsgProUserInfoDTO> msgProUserInfoDTOList = msgService.selectMsgDetail(msgCode);
//        System.out.println("controller: " + msgProUserInfoDTOList);
//
//        Map<String, Object> responesMap = new HashMap<>();
//        responesMap.put("msgProUserList", msgProUserInfoDTOList);
//
//        ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 상세 조회" , responesMap);
//
//        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
//    }
}
