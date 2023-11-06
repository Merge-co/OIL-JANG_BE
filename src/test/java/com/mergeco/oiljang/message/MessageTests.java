package com.mergeco.oiljang.message;

import com.mergeco.oiljang.message.dto.MsgDTO;
import com.mergeco.oiljang.message.dto.MsgDeleteDTO;
import com.mergeco.oiljang.message.dto.MsgProInfoDTO;
import com.mergeco.oiljang.message.dto.MsgUserDTO;
import com.mergeco.oiljang.message.service.MsgService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
public class MessageTests {

    @Autowired
    private MsgService msgService;


//    private int productCode;
//    private String productName;
//    private String productDesc;


    private static Stream<Arguments> getMsgInfos() {
        LocalDate msgTime = LocalDate.parse("2023-11-06");
        UUID sunsu = UUID.fromString("7b3ce47b-4080-49a5-b463-ecf3148117f4");
        UUID bumbum = UUID.fromString("7b3ce47b-4080-49a5-b463-ecf3148117f4");

        return Stream.of(
                Arguments.of(1, "맥북 사고싶어요", "N", msgTime, 1,
                        "맥북팔아요", "새로 산 지 얼마 안돼서...", 1, sunsu, bumbum, 1, "N")
        );
    }
    @DisplayName("쪽지 등록 테스트")
    @ParameterizedTest
    @MethodSource("getMsgInfos")
    void msgInsertTest(int msgCode, String msgContent, String msgStatus,
                       LocalDate msgTime, int productCode, String productName,
                       String productDesc,int userCode, UUID senderCode, UUID receiverCode,
                       int msgDeleteCode, String msgDeleteStatus){


        MsgDTO msgInfo = new MsgDTO(
                msgCode,
                msgContent,
                msgStatus,
                msgTime,
                new MsgProInfoDTO(msgCode, productCode, productName, productDesc),
                new MsgUserDTO(userCode, senderCode, receiverCode),
                new MsgDeleteDTO(msgDeleteCode, msgDeleteStatus)

        );
        Assertions.assertDoesNotThrow(
                () -> msgService.insertMsg(msgInfo)
        );
    }
}
