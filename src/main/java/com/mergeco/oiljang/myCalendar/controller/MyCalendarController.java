package com.mergeco.oiljang.myCalendar.controller;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.myCalendar.dto.MyCalendarDTO;
import com.mergeco.oiljang.myCalendar.service.MyCalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "캘린터 관련")
@RestController
public class MyCalendarController {
    
    private final MyCalendarService myCalendarService;

    @Autowired
    public MyCalendarController(MyCalendarService myCalendarService) {
        this.myCalendarService = myCalendarService;
    }

    @ApiOperation(value = "회원의 캘린더 내용 조회")
    @GetMapping("/users/{userCode}/myCalendar")
    public ResponseEntity<ResponseMessage> selectCalendarList(@PathVariable int userCode) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<MyCalendarDTO> myCalendarDTOList = myCalendarService.selectCalendarList(userCode);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("myCalendarList", myCalendarDTOList);

        ResponseMessage responseMessage = new ResponseMessage(200, "회원의 캘린더 내용 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "회원의 캘린더 내용 등록")
    @PostMapping("/myCalendar")
    public ResponseEntity<ResponseMessage> registCalendar(@RequestBody MyCalendarDTO myCalendarDTO) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", myCalendarService.registMyCalendar(myCalendarDTO));

        ResponseMessage responseMessage = new ResponseMessage(200, "회원의 캘린더 내용 등록", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "회원의 캘린더 내용 수정")
    @PutMapping("/myCalendar/{myCalendarCode}")
    public ResponseEntity<ResponseMessage> updateCalendar(@PathVariable int myCalendarCode, @RequestBody MyCalendarDTO myCalendarDTO) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", myCalendarService.updateMyCalendar(myCalendarCode, myCalendarDTO));

        ResponseMessage responseMessage = new ResponseMessage(200, "회원의 캘린더 내용 수정", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "회원의 캘린더 내용 삭제")
    @DeleteMapping("/myCalendar/{myCalendarCode}")
    public ResponseEntity<ResponseMessage> deleteCalendar(@PathVariable int myCalendarCode) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", myCalendarService.deleteMyCalendar(myCalendarCode));

        ResponseMessage responseMessage = new ResponseMessage(200, "회원의 캘린더 내용 삭제", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }
}
