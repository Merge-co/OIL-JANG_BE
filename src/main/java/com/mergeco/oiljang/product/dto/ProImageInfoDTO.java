package com.mergeco.oiljang.product.dto;

public class ProImageInfoDTO {
    private int proImageCode;
    private int refProductCode;
    private String proImageOriginName;
    private String proImageDbName;
    private String proImageOriginAddr;


    public ProImageInfoDTO() {
    }

    public ProImageInfoDTO(int proImageCode, int refProductCode, String proImageOriginName, String proImageDbName, String proImageOriginAddr) {
        this.proImageCode = proImageCode;
        this.refProductCode = refProductCode;
        this.proImageOriginName = proImageOriginName;
        this.proImageDbName = proImageDbName;
        this.proImageOriginAddr = proImageOriginAddr;
    }

    public int getProImageCode() {
        return proImageCode;
    }

    public void setProImageCode(int proImageCode) {
        this.proImageCode = proImageCode;
    }

    public int getRefProductCode() {
        return refProductCode;
    }

    public void setRefProductCode(int refProductCode) {
        this.refProductCode = refProductCode;
    }

    public String getProImageOriginName() {
        return proImageOriginName;
    }

    public void setProImageOriginName(String proImageOriginName) {
        this.proImageOriginName = proImageOriginName;
    }

    public String getProImageDbName() {
        return proImageDbName;
    }

    public void setProImageDbName(String proImageDbName) {
        this.proImageDbName = proImageDbName;
    }
    public String getProImageOriginAddr() {
        return proImageOriginAddr;
    }

    public void setProImageOriginAddr(String proImageOriginAddr) {
        this.proImageOriginAddr = proImageOriginAddr;
    }

    @Override
    public String toString() {
        return "ProImageInfoDTO{" +
                "proImageCode=" + proImageCode +
                ", refProductCode=" + refProductCode +
                ", proImageOriginName='" + proImageOriginName + '\'' +
                ", proImageDbName='" + proImageDbName + '\'' +
                ", proImageOriginAddr='" + proImageOriginAddr + '\'' +
                '}';
    }
}
