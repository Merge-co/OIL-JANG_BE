package com.mergeco.oiljang.inquiry;

import com.mergeco.oiljang.inquiry.dto.*;
import com.mergeco.oiljang.inquiry.service.InqService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
public class InquiryTests {

    @Autowired
    private InqService inqService;


    private static Stream<Arguments> getInqInfos(){
        LocalDate inqTime = LocalDate.now();

        return Stream.of(Arguments.of("문의드립니다", "저 로그인이 안돼요", " ", inqTime, 1, 3, "시스템 문의", "N"));
    }

    @DisplayName("문의 등록 테스트")
    @ParameterizedTest
    @MethodSource("getInqInfos")
    void inqInsertTest(String inqTitle, String inqContent, String inqAnswer, LocalDate inqTime,
                       int refUserCode, int inqCateCode, String inqCateName, String inqStatus){
        InqInsertDTO inqInfo = new InqInsertDTO(
                inqTitle,
                inqContent,
                inqAnswer,
                inqTime,
                refUserCode,
                new InqCategoryDTO(inqCateCode, inqCateName),
                inqStatus
        );

        System.out.println("inq insert test : " + inqInfo);
        inqService.insertInquiry(inqInfo);

        System.out.println("inqCatecode : " + inqCateCode);


        Assertions.assertDoesNotThrow(
                () -> inqService.insertInquiry(inqInfo)
        );
    }

    @Test
    @DisplayName("문의 정보 상세 조회")
    public void selectAnsInqInfo(){
        int inqCode = 5;

        List<InqSelectDetailDTO> inqDetail = inqService.selectInqDetail(inqCode);

        System.out.println("문의 상세조회 테스트 : " + inqDetail);

        Assertions.assertNotNull(inqDetail);
    }


    @Test
    @DisplayName("문의 리스트 조회")
    public void selectInqListUser(){
        int userCode = 2;
        int offset = 0;
        int limit = 9;

        List<InqSelectListDTO> inqList = inqService.selectInqList(userCode, offset, limit);
        log.info("문의 상세조회 테스트 : {}", inqList);
        System.out.println("문의 상세조회 테스트 : " + inqList);


        Assertions.assertTrue(inqList.size() >= 0);
    }



}
