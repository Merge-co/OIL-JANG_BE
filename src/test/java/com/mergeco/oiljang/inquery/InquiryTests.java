package com.mergeco.oiljang.inquery;

import com.mergeco.oiljang.inquery.dto.InqCategoryDTO;
import com.mergeco.oiljang.inquery.dto.InqInsertDTO;
import com.mergeco.oiljang.inquery.dto.InqUserDTO;
import com.mergeco.oiljang.inquery.service.InqService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.stream.Stream;

@SpringBootTest
public class InquiryTests {

    @Autowired
    private InqService inqService;


    private static Stream<Arguments> getInqInfos(){
        LocalDate inqTime = LocalDate.now();

        return Stream.of(Arguments.of("문의드립니다", "저 로그인이 안돼요", " ", inqTime, 1, 1, 2, "N"));
    }

    @DisplayName("문의 등록 테스트")
    @ParameterizedTest
    @MethodSource("getInqInfos")
    void inqInsertTest(String inqTitle, String inqContent, String inqAnswer, LocalDate inqTime,
                       int refUserCode, int inqCateCode, String inqCateName, String inqStatus){
        InqInsertDTO inqInsertDTO = new InqInsertDTO(
                inqTitle,
                inqContent,
                inqAnswer,
                inqTime,
                refUserCode,
                new InqCategoryDTO(inqCateCode, inqCateName),
                inqStatus
        );

        System.out.println("inq insert test : " + inqInsertDTO);
        inqService.insertInquiry(inqInsertDTO);

        Assertions.assertDoesNotThrow(
                () -> inqService.insertInquiry(inqInsertDTO)
        );
    }
}
