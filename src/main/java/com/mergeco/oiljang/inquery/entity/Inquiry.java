package com.mergeco.oiljang.inquery.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;

@Entity(name = "inquiry_and_category")
@Table(name = "inquiry")
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

    @Column(name = "inq_status")
    private String inqStatus;

    @Column(name = "ref_user_code")
    private int refUserCode;

    @JoinColumn(name = "inq_cate_code")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private InqCategory inqCategory;

    protected Inquiry() {
    }
}
