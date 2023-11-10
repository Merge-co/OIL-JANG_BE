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
    private int userCode;
    private int receiverCode;
    private String id;
    private String name;
}
