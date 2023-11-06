package com.mergeco.oiljang.message.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "message_and_delete")
@Table(name = "message")
@Getter
@ToString
@AllArgsConstructor
public class Message {
    @Id
    @Column(name = "msg_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int msgCode;

    @Column(name = "msg_content")
    private String msgContent;

    @Column(name = "msg_status")
    private String msgStatus;

    @Column(name = "msg_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate msgTime;

    @Column(name = "ref_product_code")
    private int refProductCode;

    @Column(name = "sender_code")
    private String senderCode;

    @Column(name = "receiver_code")
    private String receiverCode;

    @JoinColumn(name = "msg_delete_code")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private MsgDelete msgDelete;

    protected Message() {
    }

}
