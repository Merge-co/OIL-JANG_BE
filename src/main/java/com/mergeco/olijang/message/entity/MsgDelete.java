package com.mergeco.olijang.message.entity;

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
}
