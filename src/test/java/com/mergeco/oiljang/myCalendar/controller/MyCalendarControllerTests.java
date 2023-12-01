package com.mergeco.oiljang.myCalendar.controller;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.myCalendar.dto.MyCalendarDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class MyCalendarControllerTests {

    @Autowired
    private MyCalendarController myCalendarController;

    @Test
    void myCalendarControllerExist() {

        //then
        Assertions.assertNotNull(myCalendarController);
    }

    @Test
    void controllerSelectMyCalendar() {

        //given
        int userCode = 2;

        //when
        ResponseEntity<ResponseMessage> result = myCalendarController.selectCalendarList(userCode);

        //then
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "회원의 캘린더 내용 조회 성공");
        Assertions.assertTrue(result.getBody().getResults().size() >= 0);
    }

    @Test
    @Transactional
    void controllerRegistMyCalendar() {

        //given
        MyCalendarDTO myCalendarDTO = new MyCalendarDTO();
        myCalendarDTO.setCalendarContent("테스트 입니다.");
        myCalendarDTO.setCalendarDate(LocalDate.of(2023,11,20));
        myCalendarDTO.setCalendarTime(LocalTime.MAX);
        myCalendarDTO.setRefUserCode(2);

        //when
        ResponseEntity<ResponseMessage> result = myCalendarController.registCalendar(myCalendarDTO);

        //then
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "회원의 캘린더 내용 등록");
        Assertions.assertTrue(result.getBody().getResults().get("result") != null);
    }

    @Test
    @Transactional
    void controllerUpdateCalendar() {

        //given
        int myCalendarCode = 1;
        MyCalendarDTO myCalendarDTO = new MyCalendarDTO();
        myCalendarDTO.setMyCalendarCode(myCalendarCode);
        myCalendarDTO.setCalendarContent("테스트 입니다.");
        myCalendarDTO.setCalendarDate(LocalDate.of(2023,11,20));

        //when
        ResponseEntity<ResponseMessage> result = myCalendarController.updateCalendar(myCalendarCode, myCalendarDTO);

        //then
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "회원의 캘린더 내용 수정");
        Assertions.assertTrue(result.getBody().getResults().get("result").equals("캘린더에 내용 수정 성공"));
    }

    @Test
    @Transactional
    void controllerDeleteCalendar() {

        //given
        int myCalendarCode = 1;

        //when
        ResponseEntity<ResponseMessage> result = myCalendarController.deleteCalendar(myCalendarCode);

        //then
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "회원의 캘린더 내용 삭제");
        Assertions.assertTrue(result.getBody().getResults().get("result").equals("캘린더에 내용 삭제 성공"));
    }
}
