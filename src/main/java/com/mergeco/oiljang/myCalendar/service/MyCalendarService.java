package com.mergeco.oiljang.myCalendar.service;

import com.mergeco.oiljang.myCalendar.dto.MyCalendarDTO;
import com.mergeco.oiljang.myCalendar.entity.MyCalendar;
import com.mergeco.oiljang.myCalendar.repository.MyCalendarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyCalendarService {

    private final MyCalendarRepository myCalendarRepository;
    private final ModelMapper modelMapper;

    public MyCalendarService(MyCalendarRepository myCalendarRepository, ModelMapper modelMapper) {
        this.myCalendarRepository = myCalendarRepository;
        this.modelMapper = modelMapper;
    }

    public List<MyCalendarDTO> selectCalendarList(int userCode) {
        List<MyCalendar> myCalendarList = myCalendarRepository.findByRefUserCode(userCode);

        return myCalendarList.stream()
                .map(myCalendar -> modelMapper.map(myCalendar, MyCalendarDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public String registMyCalendar(MyCalendarDTO myCalendarDTO) {
        String result = "캘린더에 내용 등록 실패";

        myCalendarRepository.save(modelMapper.map(myCalendarDTO, MyCalendar.class));

        result = "캘린더에 내용 등록 성공";
        return result;
    }

    @Transactional
    public String updateMyCalendar(int myCalendarCode, MyCalendarDTO myCalendarDTO) {
        String result = "캘린더에 내용 수정 실패";

        MyCalendar myCalendar = myCalendarRepository.findById(myCalendarCode).orElseThrow(IllegalAccessError::new);

        myCalendar = myCalendar = myCalendar.calendarContent(myCalendarDTO.getCalendarContent())
                .calendarDate(myCalendarDTO.getCalendarDate()).builder();

        result = "캘린더에 내용 수정 성공";
        return result;
    }

    @Transactional
    public String deleteMyCalendar(int myCalendarCode) {
        String result = "캘린더에 내용 삭제 실패";

        MyCalendar myCalendar = myCalendarRepository.findById(myCalendarCode).orElse(null);
        if(myCalendar != null) {
            myCalendarRepository.delete(myCalendar);
        }

        result = "캘린더에 내용 삭제 성공";
        return result;
    }
}
