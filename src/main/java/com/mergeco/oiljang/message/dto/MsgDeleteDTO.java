package com.mergeco.oiljang.message.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MsgDeleteDTO {
    private int msgDeleteCode;
    private String msgDeleteStatus;
}
