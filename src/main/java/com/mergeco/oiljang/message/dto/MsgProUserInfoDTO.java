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
    private UUID senderCode;
    private UUID receiverCode;
    private String id;
    private String name;
    private String token;
    private int productCode;
    private String productName;
    private String productDesc;
    private int msgDeleteCode;
    private String msgDeleteStatus;

}
