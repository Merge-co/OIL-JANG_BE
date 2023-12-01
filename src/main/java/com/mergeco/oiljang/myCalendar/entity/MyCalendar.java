package com.mergeco.oiljang.myCalendar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
@ToString
@Entity(name = "MyCalendar")
@Table(name = "my_calendar_info")
public class MyCalendar {

    @Id
    @Column(name = "my_calendar_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int myCalendarCode;

    @Column(name = "ref_user_code")
    private int refUserCode;

    @Column(name = "calendar_content")
    private String calendarContent;

    @Column(name = "calendar_date")
    private LocalDate calendarDate;
    @Column(name = "calendar_time")
    private LocalTime calendarTime;

    protected MyCalendar() {
    }

    public MyCalendar calendarContent(String val) {
        calendarContent = val;
        return this;
    }

    public MyCalendar calendarDate(LocalDate val) {
        calendarDate = val;
        return this;
    }

    public MyCalendar calendarTime(LocalTime val) {
        calendarTime = val;
        return this;
    }

    public MyCalendar builder() {
        return new MyCalendar(myCalendarCode, refUserCode, calendarContent, calendarDate, calendarTime);
    }

}
