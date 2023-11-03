package com.mergeco.olijang.message.dto;

import java.time.LocalDate;

public class MsgDTO {
    private int msgCode;
    private String msgContent;
    private String msgStatus;
    private LocalDate msgTime;
    private int refProductCode;
    private String msgSenderNick;
    private String msgReceiverNick;
    private int refMsgDeleteStatus;

    public MsgDTO() {
    }

    public MsgDTO(int msgCode, String msgContent, String msgStatus, LocalDate msgTime, int refProductCode, String msgSenderNick, String msgReceiverNick, int refMsgDeleteStatus) {
        this.msgCode = msgCode;
        this.msgContent = msgContent;
        this.msgStatus = msgStatus;
        this.msgTime = msgTime;
        this.refProductCode = refProductCode;
        this.msgSenderNick = msgSenderNick;
        this.msgReceiverNick = msgReceiverNick;
        this.refMsgDeleteStatus = refMsgDeleteStatus;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public LocalDate getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(LocalDate msgTime) {
        this.msgTime = msgTime;
    }

    public int getRefProductCode() {
        return refProductCode;
    }

    public void setRefProductCode(int refProductCode) {
        this.refProductCode = refProductCode;
    }

    public String getMsgSenderNick() {
        return msgSenderNick;
    }

    public void setMsgSenderNick(String msgSenderNick) {
        this.msgSenderNick = msgSenderNick;
    }

    public String getMsgReceiverNick() {
        return msgReceiverNick;
    }

    public void setMsgReceiverNick(String msgReceiverNick) {
        this.msgReceiverNick = msgReceiverNick;
    }

    public int getRefMsgDeleteStatus() {
        return refMsgDeleteStatus;
    }

    public void setRefMsgDeleteStatus(int refMsgDeleteStatus) {
        this.refMsgDeleteStatus = refMsgDeleteStatus;
    }

    @Override
    public String toString() {
        return "MsgDTO{" +
                "msgCode=" + msgCode +
                ", msgContent='" + msgContent + '\'' +
                ", msgStatus='" + msgStatus + '\'' +
                ", msgTime=" + msgTime +
                ", refProductCode=" + refProductCode +
                ", msgSenderNick='" + msgSenderNick + '\'' +
                ", msgReceiverNick='" + msgReceiverNick + '\'' +
                ", refMsgDeleteStatus=" + refMsgDeleteStatus +
                '}';
    }
}
