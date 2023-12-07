package com.mergeco.oiljang.message.dto;

import com.mergeco.oiljang.message.entity.MsgDeleteInfo;
import com.mergeco.oiljang.product.dto.ProductDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MsgDTO {
    private int msgCode;
    private String msgContent;
    private String msgStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime msgTime;
    private int refProductCode;
    private int senderCode;
    private int receiverCode;
    private MsgDeleteInfo msgDeleteInfo;

}
