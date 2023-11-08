package com.mergeco.oiljang.message.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

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
    private UUID senderCode;

    @Column(name = "receiver_code")
    private UUID receiverCode;

    @JoinColumn(name = "msg_delete_code", insertable = false, updatable = false)
    @ManyToOne
    private MsgDeleteInfo msgDeleteInfo;

    protected Message() {
    }

    public Message msgCode(int val){
        msgCode = val;
        return this;
    }

    public Message msgContent(String val){
        msgContent = val;
        return this;
    }
    public Message msgStatus(String val){
        msgStatus = val;
        return this;
    }
    public Message msgTime(LocalDate val){
        msgTime = val;
        return this;
    }
    public Message refProductCode(int val){
        refProductCode = val;
        return this;
    }
    public Message senderCode(UUID val){
        senderCode = val;
        return this;
    }
    public Message receiverCode(UUID val){
        receiverCode = val;
        return this;
    }
    public Message msgDeleteInfo(MsgDeleteInfo val){
        msgDeleteInfo = val;
        return this;
    }

    public Message builder(){
        return new Message(msgCode, msgContent, msgStatus, msgTime, refProductCode, senderCode, receiverCode, msgDeleteInfo);
    }


}
