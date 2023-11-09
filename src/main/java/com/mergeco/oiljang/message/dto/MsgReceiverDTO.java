package com.mergeco.oiljang.message.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MsgReceiverDTO {
    private int msgCode;
    private UUID receiverCode;
    private int productCode;
    private UUID userCode;
    private String name;
    private String id;
}
