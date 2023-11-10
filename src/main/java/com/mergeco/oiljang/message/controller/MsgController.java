package com.mergeco.oiljang.message.controller;

import com.mergeco.oiljang.common.paging.JpqlPagingButton;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.message.dto.*;
import com.mergeco.oiljang.message.service.MsgService;
import com.mergeco.oiljang.product.dto.ProductListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Api(tags = "쪽지 컨트롤러")
public class MsgController {

    public final MsgService msgService;

    public MsgController(MsgService msgService) {
        this.msgService = msgService;
    }

//    @GetMapping("/regist")
//    public void registPage(){}

    @ApiOperation(value = "쪽지 등록")
    @PostMapping("/messages")
    public ResponseEntity<ResponseMessage> msgAnswer(@RequestBody MsgInsertDTO msgInfo) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            msgService.insertMsg(msgInfo);
            System.out.println("controller : " + msgInfo);
            Map<String, Object> responseMap = new HashMap<>();

            ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 등록 성공", responseMap);
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }


//    @GetMapping("/messages/{msgCode}/{userCode}")
//    public ResponseEntity<ResponseMessage> selectSenderReceiver(@RequestBody MsgUserDTO msgUserDTO, @PathVariable int msgCode, @PathVariable UUID userCode){
//
//            HttpHeaders headers = new HttpHeaders();
//
//            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//            List<MsgUserDTO> msgUserDTOList = msgService.selectSenderReceiver(msgCode, userCode);
//            System.out.println("controller : " + msgUserDTO);
//
//            Map<String, Object> responseMap = new HashMap<>();
//            responseMap.put("msgUserList", msgUserDTOList);
//
//            ResponseMessage responseMessage = new ResponseMessage(200,"쪽지 유저 정보" ,responseMap);
//
//            return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
//    }

    @ApiOperation(value = "쪽지 모달 조회")
    @GetMapping("/messages")
    public ResponseEntity<ResponseMessage> selectReceiver(@RequestBody MsgReceiverDTO msgReceiverDTO) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<MsgReceiverDTO> msgReceiverList = msgService.selectReceiver();
        System.out.println("controller: " + msgReceiverList);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("msgReceiveList", msgReceiverList);

        ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 모달 receiver", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

    @ApiOperation(value = "쪽지 상세 조회")
    @GetMapping("/messages/{msgCode}")
    public ResponseEntity<ResponseMessage> selectMsgDetail(@PathVariable int msgCode) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<MsgProUserInfoDTO> msgProUserInfoDTOList = msgService.selectMsgDetail(msgCode);
        System.out.println("controller: " + msgProUserInfoDTOList);

        Map<String, Object> responesMap = new HashMap<>();
        responesMap.put("msgProUserList", msgProUserInfoDTOList);

        ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 상세 조회", responesMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }


    @GetMapping("/users/{userCode}/messages?offset={offset}&limit={limit}&isRecevied={isRecevied}")
    public ResponseEntity<List<MsgListDTO>> getMessages(
            @RequestParam(required = false) Integer page,
            @RequestParam String pageKind,
            @PathVariable int userCode,
            @RequestParam int offset,
            @RequestParam int limit,
            @RequestParam Boolean isReceived) {


        if(page == null){
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        limit = 0;
        switch (pageKind){
            case "merge":
                limit = 6;
            case "list":
                limit = 8;
        }

        offset = limit * (page -1);

        List<MsgListDTO> msgListDTOList = msgService.getMessages(userCode, offset, limit, isReceived);
        double totalMsg = Long.valueOf(msgService.countMsgList()).doubleValue();
        int totalPage = (int) Math.ceil(totalMsg / limit);

        if(page >= totalPage){
            page = totalPage;
        }else if(page < 1){
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("msgListDTOList", msgListDTOList);
        responseMap.put("pageingBtn", pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 리스트 조회 성공", responseMap);

        return new ResponseEntity<>(msgListDTOList, HttpStatus.OK);
    }

}

