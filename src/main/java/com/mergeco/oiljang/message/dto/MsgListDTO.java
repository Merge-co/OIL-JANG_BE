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
    private UUID userCode;
    private String senderOrReceiver;
    private String msgContent;
    private String msgStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate msgTime;
    private boolean isReceived;
}
