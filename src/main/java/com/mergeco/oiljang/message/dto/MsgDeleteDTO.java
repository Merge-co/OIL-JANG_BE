package com.mergeco.oiljang.message.dto;

public class MsgDeleteDTO {
    private int msgDeleteCode;
    private String msgDeleteStatus;

    public MsgDeleteDTO() {
    }

    public MsgDeleteDTO(int msgDeleteCode, String msgDeleteStatus) {
        this.msgDeleteCode = msgDeleteCode;
        this.msgDeleteStatus = msgDeleteStatus;
    }

    public int getMsgDeleteCode() {
        return msgDeleteCode;
    }

    public void setMsgDeleteCode(int msgDeleteCode) {
        this.msgDeleteCode = msgDeleteCode;
    }

    public String getMsgDeleteStatus() {
        return msgDeleteStatus;
    }

    public void setMsgDeleteStatus(String msgDeleteStatus) {
        this.msgDeleteStatus = msgDeleteStatus;
    }

    @Override
    public String toString() {
        return "MsgDeleteDTO{" +
                "msgDeleteCode=" + msgDeleteCode +
                ", msgDeleteStatus='" + msgDeleteStatus + '\'' +
                '}';
    }
}
