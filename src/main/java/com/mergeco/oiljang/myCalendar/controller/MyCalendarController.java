package com.mergeco.oiljang.myCalendar.controller;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.myCalendar.dto.MyCalendarDTO;
import com.mergeco.oiljang.myCalendar.service.MyCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyCalendarController {
    
    private final MyCalendarService myCalendarService;

    @Autowired
    public MyCalendarController(MyCalendarService myCalendarService) {
        this.myCalendarService = myCalendarService;
    }

    public ResponseEntity<ResponseMessage> selectCalendarList(int refUserCode) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<MyCalendarDTO> myCalendarDTOList = myCalendarService.selectCalendarList(refUserCode);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("myCalendarList", myCalendarDTOList);

        ResponseMessage responseMessage = new ResponseMessage(200, "회원의 캘린더 내용 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessage> registCalendar(MyCalendarDTO myCalendarDTO) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", myCalendarService.registMyCalendar(myCalendarDTO));

        ResponseMessage responseMessage = new ResponseMessage(200, "회원의 캘린더 내용 등록", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessage> updateCalendar(MyCalendarDTO myCalendarDTO) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", myCalendarService.updateMyCalendar(myCalendarDTO));

        ResponseMessage responseMessage = new ResponseMessage(200, "회원의 캘린더 내용 수정", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }
}
