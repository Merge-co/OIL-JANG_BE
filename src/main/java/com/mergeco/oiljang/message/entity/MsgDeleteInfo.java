package com.mergeco.oiljang.message.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "msgDeleteInfo")
@Table(name = "msg_delete_info")
@Getter
@ToString
@AllArgsConstructor
public class MsgDeleteInfo {

    @Id
    @Column(name = "msg_delete_code")
    private int msgDeleteCode;

    @Column(name = "msg_delete_status")
    private String msgDeleteStatus;

    protected MsgDeleteInfo() {
    }

    public MsgDeleteInfo msgDeleteCode(int val){
        msgDeleteCode = val;
        return this;
    }

    public MsgDeleteInfo msgDeleteStatus(String val){
        msgDeleteStatus = val;
        return this;
    }

    public MsgDeleteInfo builder(){
        return new MsgDeleteInfo(msgDeleteCode, msgDeleteStatus);
    }

}
