package com.mergeco.oiljang.message.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MsgListDTO {
    private int userCode;
    private String name;
    private String id;
    private int msgCode;
    private int senderCode;
    private int receiverCode;
    private String msgContent;
    private String msgStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate msgTime;
    private int msgDeleteCode;
//    private boolean isReceived;
}
