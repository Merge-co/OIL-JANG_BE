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
public class MsgProUserInfoDTO {
    private int msgCode;
    private String msgContent;
    private String msgStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate msgTime;
    private int senderCode;
    private int receiverCode;
    private int userCode;
    private String id;
    private String name;
    private int refProductCode;
    private int productCode;
    private String productName;
    private String productDesc;
    private int msgDeleteCode;
    private String msgDeleteStatus;

}
