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

    public List<MyCalendarDTO> selectCalendarList(int refUserCode) {
        List<MyCalendar> myCalendarList = myCalendarRepository.findByRefUserCode(refUserCode);

        return myCalendarList.stream()
                .map(myCalendar -> modelMapper.map(myCalendar, MyCalendarDTO.class))
                .collect(Collectors.toList());
    }

    public void insertMyCalendar(MyCalendarDTO myCalendarDTO) {
        myCalendarRepository.save(modelMapper.map(myCalendarDTO, MyCalendar.class));
    }

    @Transactional
    public void updateMyCalendar(MyCalendarDTO myCalendarDTO) {
        MyCalendar myCalendar = myCalendarRepository.findById(myCalendarDTO.getMyCalendarCode()).orElseThrow(IllegalAccessError::new);
        myCalendar = myCalendar = myCalendar.calendarContent(myCalendarDTO.getCalendarContent())
                .calendarDate(myCalendarDTO.getCalendarDate()).builder();
    }

    @Transactional
    public void deleteMyCalendar(int myCalendarCode) {
        MyCalendar myCalendar = myCalendarRepository.findById(myCalendarCode).orElse(null);
        if(myCalendar != null) {
            myCalendarRepository.delete(myCalendar);
        }
    }
}
