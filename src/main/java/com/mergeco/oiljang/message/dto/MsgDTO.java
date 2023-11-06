package com.mergeco.oiljang.message.dto;

import com.mergeco.oiljang.message.entity.MsgDelete;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MsgDTO {
    private int msgCode;
    private String msgContent;
    private String msgStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate msgTime;
    private MsgProInfoDTO products;
    private MsgUserDTO msgUser;
    private MsgDelete msgDelete;

    public MsgDTO(int msgCode, String msgContent, String msgStatus, LocalDate msgTime, MsgProInfoDTO products, MsgUserDTO msgUser, MsgDeleteDTO msgDeleteDTO) {
    }
}
