package com.mergeco.oiljang.message.entity;

import com.mergeco.oiljang.message.dto.MsgReceiverDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "message_and_delete")
@Table(name = "message")
@Getter
@ToString
@AllArgsConstructor
//@SqlResultSetMapping(
//        name = "MsgReceiverDTOMapping",
//        classes = @ConstructorResult(
//                targetClass = MsgReceiverDTO.class,
//                columns = {
//                        @ColumnResult(name = "msg_code", type = Integer.class),
//                        @ColumnResult(name = "receiver_code", type = UUID.class),
//                        @ColumnResult(name = "product_code", type = Integer.class),
//                        @ColumnResult(name = "user_code", type = UUID.class),
//                        @ColumnResult(name = "name", type = String.class),
//                        @ColumnResult(name = "id", type = String.class)
//                }
//        )
//)
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime msgTime;

    @Column(name = "ref_product_code")
    private int refProductCode;

    @Column(name = "sender_code")
    private int senderCode;

    @Column(name = "receiver_code")
    private int receiverCode;

    @JoinColumn(name = "msg_delete_code")
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
    public Message msgTime(LocalDateTime val){
        msgTime = val;
        return this;
    }
    public Message refProductCode(int val){
        refProductCode = val;
        return this;
    }
    public Message senderCode(int val){
        senderCode = val;
        return this;
    }
    public Message receiverCode(int val){
        receiverCode = val;
        return this;
    }
//    public Message msgDeleteInfo(int val){
//        MsgDeleteInfo msgDeleteInfo = new MsgDeleteInfo(val, null);
//        return this;
//    }

    public Message msgDeleteInfo(MsgDeleteInfo val){
        msgDeleteInfo = val;
        return this;
    }
    public Message builder(){
        return new Message(msgCode, msgContent, msgStatus, msgTime, refProductCode, senderCode, receiverCode, msgDeleteInfo);
    }


}
