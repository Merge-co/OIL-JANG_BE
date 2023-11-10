package com.mergeco.oiljang.message.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
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
    private int senderCode;
    private int receiverCode;
    private MsgDeleteDTO msgDeleteInfoMsgDeleteDTO;

    public MsgInsertDTO(String msgContent, String msgStatus, LocalDate msgTime, int refProductCode, int senderCode, int receiverCode, MsgDeleteDTO msgDeleteDTO) {
        this.msgContent = msgContent;
        this.msgStatus = msgStatus;
        this.msgTime = msgTime;
        this.refProductCode = refProductCode;
        this.senderCode = senderCode;
        this.receiverCode = receiverCode;
        this.msgDeleteInfoMsgDeleteDTO = msgDeleteDTO;
    }
}
