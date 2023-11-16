package com.mergeco.oiljang.inquiry;

import com.mergeco.oiljang.inquiry.dto.*;
import com.mergeco.oiljang.inquiry.entity.InqCategory;
import com.mergeco.oiljang.inquiry.entity.Inquiry;
import com.mergeco.oiljang.inquiry.repository.InqRepository;
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
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
public class InquiryTests {

    @Autowired
    private InqService inqService;

    @Autowired
    private InqRepository inqRepository;


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
        int userCode = 4;
        int offset = 0;
        int limit = 9;

        List<InqSelectListDTO> inqList = inqService.selectInqList(userCode, offset, limit);
        //log.info("문의 상세조회 테스트 : {}", inqList);
        System.out.println("문의 상세조회 테스트 : " + inqList);


        Assertions.assertTrue(inqList.size() >= 0);
    }



    @Test
    @DisplayName("카테고리로 리스트 조회")
    public void selectListCategory(){
        int userCode = 1;
        int inqCateCode = 1;
        int offset = 0;
        int limit = 9;

        List<InqSelectListDTO> inqListCate = inqService.selectInqListCate(userCode, inqCateCode ,offset, limit);
        System.out.println("카테고리로 리스트 조회 : " + inqListCate);

        Assertions.assertTrue(inqListCate.size() >= 0);
    }


    @Test
    @DisplayName("답변여부 리스트 조회-관리자")
    public void selectInqStatus(){
        int userCode = 4;
        String inqStatus = "N";
        int offset = 0;
        int limit = 9;

        List<InqSelectListDTO> inqStatusY = inqService.selectInqStatus(userCode, inqStatus, offset, limit);
        System.out.println("답변완료 : " + inqStatusY);

        Assertions.assertTrue(inqStatusY.size() >= 0);
    }


    @Test
    @DisplayName("LIKE 연산자로 검색하여 리스트 조회")
    public void selectInqLike(){

        int userCode = 1;
        int offset = 0;
        int limit = 9;
        String keyword = "문";

        List<InqSelectListDTO> inqSelectListDTOList = inqService.selectInqLike(userCode, offset, limit, keyword);

        inqSelectListDTOList.forEach(System.out::println);
        Assertions.assertNotNull(inqSelectListDTOList);
    }



    @Test
    @DisplayName("문의 수정(사용자/관리자)")
    public void updateInquiry(){


        InqDTO inqDTO = new InqDTO();
                inqDTO.setInqCode(1);
                inqDTO.setInqTitle("문의문의");
                inqDTO.setInqContent("로그인또안돼요");
                inqDTO.setInqAnswer(" ");
                inqDTO.setInqTime(LocalDate.now());
                inqDTO.setRefUserCode(1);
                inqDTO.setInqCateCode(3);
                inqDTO.setInqCateName("기타");
                inqDTO.setInqStatus("N");

        int userCode = 4;
        int inqCode = 1;

        System.out.println("테스트 inqDTO: " + inqDTO);
        int inq = inqService.updateInq(inqDTO, userCode, inqCode);


        //inqService.updateInqStatus(1);
        Assertions.assertEquals(1, inq);
    }

//    @Test
//    @DisplayName("답변여부 상태값 변경")
//    void updateMsgStatus(){
//
//        String inq = inqService.updateInqStatus(1);
//
//        Assertions.assertEquals("문의 상태 수정 성공", inq);
//    }

    @Test
    @DisplayName("문의 삭제")
    public void deleteInq(){

        int inqCode = 1;

        int result = inqService.deleteInq(inqCode);

        Assertions.assertEquals(1, result);
    }
}
