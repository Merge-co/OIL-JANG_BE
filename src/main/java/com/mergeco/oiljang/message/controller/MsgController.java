package com.mergeco.oiljang.message.controller;

import com.mergeco.oiljang.common.paging.JpqlPagingButton;
import com.mergeco.oiljang.common.restApi.LoginMessage;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.message.dto.*;
import com.mergeco.oiljang.message.repository.MsgRepository;
import com.mergeco.oiljang.message.service.MsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "쪽지 컨트롤러")
public class MsgController {

    public final MsgService msgService;

    public final MsgRepository msgRepository;

    public MsgController(MsgService msgService, MsgRepository msgRepository) {
        this.msgService = msgService;
        this.msgRepository = msgRepository;
    }

//    @GetMapping("/regist")
//    public void registPage(){}

    @ApiOperation(value = "쪽지 등록")
    @PostMapping("/messages")
    public ResponseEntity<?> msgAnswer(@RequestBody MsgInsertDTO msgInfo) {

        //  HttpHeaders headers = new HttpHeaders();

        //  headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //  msgService.insertMsg(msgInfo);
        System.out.println("controller : " + msgInfo);
        //  Map<String, Object> responseMap = new HashMap<>();

        // ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 등록 성공", responseMap);
        return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "쪽지 등록 성공", msgService.insertMsg(msgInfo)));


    }


//

    @ApiOperation(value = "쪽지 상세 조회")
    @GetMapping("/messages/{msgCode}")
    public ResponseEntity<List<MsgProUserInfoDTO>> selectMsgDetail(@PathVariable int msgCode) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<MsgProUserInfoDTO> msgProUserInfoDTOList = msgService.selectMsgDetail(msgCode);
        System.out.println("controller: " + msgProUserInfoDTOList);
        msgService.updateMsgStatus(msgCode);

        Map<String, Object> responesMap = new HashMap<>();
        responesMap.put("msgProUserList", msgProUserInfoDTOList);

        ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 상세 조회", responesMap);

        return new ResponseEntity<>(msgProUserInfoDTOList, headers, HttpStatus.OK);
    }



    @ApiOperation(value = "쪽지 리스트 조회")
    @GetMapping("/users/{userCode}/messages")
    public ResponseEntity<ResponseMessage> getMessages(
            @RequestParam(required = false) Integer page,
            @PathVariable int userCode,
            @RequestParam Boolean isReceived,
            @RequestParam(required = false) String keyword) {

        if(page == null){
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        int limit = 10;

        int offset = limit * (page - 1);

        List<MsgListDTO> msgList = msgService.getMessages(userCode, page, offset, limit, isReceived, keyword);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("msgList", msgList);
        double totalMsg = Long.valueOf(msgService.countMsgList(page, userCode, offset, limit, isReceived, keyword)).doubleValue();
        System.out.println("totalMsg :" + totalMsg);
        int totalPage = (int) Math.ceil(totalMsg / limit);
        System.out.println("totalMsg :" + totalMsg);

        if(page >= totalPage){
            page = totalPage;
        }else if(page < 1){
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);

        responseMap.put("totalMsg", totalMsg);
        responseMap.put("pagingBtn", pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 리스트 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "쪽지 삭제(수정)")
    @DeleteMapping("/messages/{msgCode}")
    public ResponseEntity<ResponseMessage> updateDeleteCode(@PathVariable int msgCode){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        int result = msgService.updateDeleteCode(msgCode);

        System.out.println(result);


        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("msgCode", msgCode);
        ResponseMessage responseMessage = new ResponseMessage(200,"쪽지 삭제 성공",responseMap);
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }


//
//    @ApiOperation(value = "쪽지 검색 후 리스트 조회")
//    @GetMapping("/users/{userCode}/messages/search")
//    public ResponseEntity<ResponseMessage> selectMsgLike(
//             @RequestParam(required = false) Integer page,
//             @PathVariable int userCode,
//             @RequestParam(required = false) Boolean isReceived,
//             @RequestParam(required = false) String keyword){
//
//        if(page == null){
//            page = 1;
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//
//        int limit = 3;
//        int offset = limit * (page - 1);
//
//        List<MsgListDTO> msgListDTOList = msgService.selectMsgLike(userCode, offset, limit, isReceived, keyword);
//        double totalMsg = Long.valueOf(msgService.countMsgList()).doubleValue();
//        int totalPage = (int) Math.ceil(totalMsg / limit);
//
//        if(page >= totalPage){
//            page = totalPage;
//        }else if(page < 1){
//            page = 1;
//        }
//
//        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);
//
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("msgListDTOList", msgListDTOList);
//        responseMap.put("pageingBtn", pagingBtn);
//
//        ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 리스트 조회 성공", responseMap);
//
//        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
//    }

}

