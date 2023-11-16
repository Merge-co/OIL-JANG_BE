package com.mergeco.oiljang.inquiry.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "inquiry_and_category")
@Table(name = "inquiry")
@Getter
@AllArgsConstructor
@ToString
public class Inquiry {

    @Id
    @Column(name = "inq_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inqCode;

    @Column(name = "inq_title")
    private String inqTitle;

    @Column(name = "inq_content")
    private String inqContent;

    @Column(name = "inq_answer")
    private String inqAnswer;

    @Column(name = "inq_time")
    private LocalDate inqTime;

    @Column(name = "ref_user_code")
    private int refUserCode;

    @JoinColumn(name = "inq_cate_code")
    @ManyToOne()
    private InqCategory inqCategory;

    @Column(name = "inq_status")
    private String inqStatus;

    protected Inquiry() {
    }
    public Inquiry inqCode(int val){
        inqCode = val;
        return this;
    }
    public Inquiry inqTitle(String val){
        inqTitle = val;
        return this;
    }
    public Inquiry inqContent(String val){
        inqContent = val;
        return this;
    }
    public Inquiry inqAnswer(String val){
        inqAnswer = val;
        return this;
    }
    public Inquiry inqTime(LocalDate val){
        inqTime = val;
        return this;
    }
    public Inquiry refUserCode(int val){
        refUserCode = val;
        return this;
    }
    public Inquiry inqCategory(int val, String val2){
        inqCategory = new InqCategory(val, val2);
        return this;
    }
    public Inquiry inqStatus(String val){
        inqStatus = val;
        return this;
    }

    public Inquiry builder(){
        return new Inquiry(inqCode, inqTitle, inqContent, inqAnswer, inqTime, refUserCode, inqCategory, inqStatus);
    }

}
