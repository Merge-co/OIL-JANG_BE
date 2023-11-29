package com.mergeco.oiljang.myCalendar.repository;

import com.mergeco.oiljang.myCalendar.entity.MyCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyCalendarRepository extends JpaRepository<MyCalendar, Integer> {
    List<MyCalendar> findByRefUserCode(int refUserCode);
}
