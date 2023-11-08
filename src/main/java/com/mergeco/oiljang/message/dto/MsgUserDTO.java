package com.mergeco.oiljang.message.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MsgUserDTO {
    private int msgCode;
    private UUID userCode;
    private UUID senderCode;
    private UUID receiverCode;
    private String id;
    private String name;
    private String token;
}
