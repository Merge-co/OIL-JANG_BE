package com.mergeco.oiljang.myCalendar.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyCalendarDTO {
    private int myCalendarCode;
    private int refUserCode;
    private String calendarContent;
    private LocalDate calendarDate;
}
