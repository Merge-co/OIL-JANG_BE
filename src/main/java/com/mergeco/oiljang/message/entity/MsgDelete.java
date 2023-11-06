package com.mergeco.oiljang.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "msgDelete")
@Table(name = "msgDeleteStatus")
public class MsgDelete {

    @Id
    @Column(name = "msg_delete_code")
    private int msgDeleteCode;

    @Column(name = "msg_delete_status")
    private String msgDeleteStatus;

    protected MsgDelete() {
    }

    public int getMsgDeleteCode() {
        return msgDeleteCode;
    }

    public String getMsgDeleteStatus() {
        return msgDeleteStatus;
    }

    @Override
    public String toString() {
        return "MsgDelete{" +
                "msgDeleteCode=" + msgDeleteCode +
                ", msgDeleteStatus='" + msgDeleteStatus + '\'' +
                '}';
    }
}
