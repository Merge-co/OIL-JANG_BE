package com.mergeco.oiljang.myCalendar.service;

import com.mergeco.oiljang.myCalendar.dto.MyCalendarDTO;
import com.mergeco.oiljang.myCalendar.entity.MyCalendar;
import com.mergeco.oiljang.myCalendar.repository.MyCalendarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class MyCalendarServiceTests {

    @Autowired
    private MyCalendarService myCalendarService;

    @Autowired
    private MyCalendarRepository myCalendarRepository;

    @Test
    void myCalendarServiceExist() {

        //then
        Assertions.assertNotNull(myCalendarService);
    }

    @Test
    void selectCalendarList() {

        //given
        int refUserCode = 2;

        //when
        List<MyCalendarDTO> calendarList = myCalendarService.selectCalendarList(refUserCode);

        //then
        Assertions.assertTrue(calendarList.size() >= 0);
    }

    @Test
    @Transactional
    void insertCalendar() {

        //given
        Long beforeCount = myCalendarRepository.count();
        MyCalendarDTO myCalendarDTO = new MyCalendarDTO();
        myCalendarDTO.setRefUserCode(2);
        myCalendarDTO.setCalendarDate(LocalDate.now());
        myCalendarDTO.setCalendarContent("테스트 내용");
        myCalendarDTO.setCalendarTime(LocalTime.MAX);

        //when
        myCalendarService.registMyCalendar(myCalendarDTO);
        Long afterCount = myCalendarRepository.count();

        //then
        Assertions.assertTrue(beforeCount + 1 == afterCount);
    }

    @Test
    @Transactional
    void updateCalendar() {

        //given
        MyCalendarDTO myCalendarDTO = new MyCalendarDTO();
        int myCalendarCode = 1;
        myCalendarDTO.setCalendarDate(LocalDate.of(2023, 11, 20));
        myCalendarDTO.setCalendarContent("테스트 업데이트");
        myCalendarDTO.setCalendarTime(LocalTime.MAX);

        //when
        myCalendarService.updateMyCalendar(myCalendarCode, myCalendarDTO);
        MyCalendar myCalendar = myCalendarRepository.findById(myCalendarCode).orElseThrow(IllegalAccessError::new);

        //then
        Assertions.assertEquals(myCalendar.getCalendarContent(),"테스트 업데이트");
        Assertions.assertEquals(myCalendar.getCalendarDate(),LocalDate.of(2023, 11, 20));
        Assertions.assertEquals(myCalendar.getCalendarTime(),LocalTime.MAX);
    }

    @Test
    @Transactional
    void deleteCalendar() {

        //given
        MyCalendarDTO myCalendarDTO = new MyCalendarDTO();
        myCalendarDTO.setRefUserCode(2);
        myCalendarDTO.setCalendarDate(LocalDate.now());
        myCalendarDTO.setCalendarContent("테스트 내용");
        myCalendarService.registMyCalendar(myCalendarDTO);
        int myCalendarCode = 1;

        //when
        myCalendarService.deleteMyCalendar(myCalendarCode);

        //then
        Assertions.assertFalse(myCalendarRepository.existsById(myCalendarCode));
    }
}
