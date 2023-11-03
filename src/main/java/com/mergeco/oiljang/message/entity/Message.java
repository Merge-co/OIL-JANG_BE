package com.mergeco.oiljang.message.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "message_and_delete")
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "msg_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int msgCode;

    @Column(name = "msn_content")
    private String msgContent;

    @Column(name = "msg_status")
    private String msgStatus;

    @Column(name = "msg_time")
    private LocalDate msgTime;

    @Column(name = "ref_product_code")
    private int refProductCode;

    @Column(name = "msg_sender_nick")
    private String msgSenderNick;

    @Column(name = "msg_receiver_nick")
    private String msgReceiverNick;

    @JoinColumn(name = "msg_delete_code")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private MsgDelete msgDelete;
}
