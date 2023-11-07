package com.mergeco.oiljang.message.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MsgInsertDTO {
    private int msgCode;
    private String msgContent;
    private String msgStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate msgTime;
    private int refProductCode;
    private UUID senderCode;
    private UUID receiverCode;
    private MsgDeleteDTO msgDeleteInfoMsgDeleteDTO;
}
