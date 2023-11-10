package com.mergeco.oiljang.message;

import com.mergeco.oiljang.message.dto.*;
import com.mergeco.oiljang.message.service.MsgService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
public class MessageTests {

    @Autowired
    private MsgService msgService;


//    private static Stream<Arguments> getMsgInfos() {
//        LocalDate msgTime = LocalDate.parse("2023-11-06");
//        UUID sunsu = UUID.fromString("7b3ce47b-4080-49a5-b463-ecf3148117f4");
//        UUID bumbum = UUID.fromString("7b3ce47b-4080-49a5-b463-ecf3148117f4");
//
//        return Stream.of(
//                Arguments.of(1, "맥북 사고싶어요", "N", msgTime, 1,
//                        "맥북팔아요", "새로 산 지 얼마 안돼서...", 1, sunsu, bumbum, 1, "N")
//        );
//    }
//    @DisplayName("쪽지 조회 테스트")
//    @ParameterizedTest
//    @MethodSource("getMsgInfos")
//    void msgInsertTest(int msgCode, String msgContent, String msgStatus,
//                       LocalDate msgTime, int productCode, String productName,
//                       String productDesc,int userCode, UUID senderCode, UUID receiverCode,
//                       int msgDeleteCode, String msgDeleteStatus){
//
//
//        MsgDTO msgInfo = new MsgDTO(
//                msgCode,
//                msgContent,
//                msgStatus,
//                msgTime,
//                new MsgProInfoDTO(msgCode, productCode, productName, productDesc),
//                new MsgUserDTO(userCode, senderCode, receiverCode),
//                new MsgDeleteDTO(msgDeleteCode, msgDeleteStatus)
//                  //메인은 Message라서 Message에 먼저 값을 넣고 그 다음에 dto객체들에 값을 넣는데,
                    //이 때 FK 제약조건 때문에, 값이 없다고 떠서 constraint 오류가 난다
                    //joinColumn 걸어준 곳에 insertable, updatable을 false로 해두면 조회용으로만 쓰고 insert는 되지 않는다.
//        );
//
//        System.out.println("test : " + msgInfo);
//        Assertions.assertDoesNotThrow(
//                () -> msgService.insertMsg(msgInfo)
//        );
//    }





    //-----------------------------------//

    private static Stream<Arguments> getMsgInfos() {
        LocalDate msgTime = LocalDate.parse("2023-11-06");


        return Stream.of(Arguments.of("맥북 사고싶어요", "N", msgTime, 1,
                2, 2, 1, "N")
        );
    }


    @DisplayName("쪽지 등록 테스트")
    @ParameterizedTest
    @MethodSource("getMsgInfos")
   void msgInsertTest(String msgContent, String msgStatus,
                      LocalDate msgTime,int refProductCode, int senderCode, int receiverCode,
                       int msgDeleteCode, String msgDeleteStatus){
        MsgInsertDTO msgInfo = new MsgInsertDTO(
                msgContent,
                msgStatus,
                msgTime,
                refProductCode,
                senderCode,
                receiverCode,
                new MsgDeleteDTO(msgDeleteCode, msgDeleteStatus)
               );

        System.out.println("test: " + msgInfo);
        msgService.insertMsg(msgInfo);



//        Assertions.assertDoesNotThrow(
//                () -> msgService.insertMsg(msgInfo)
//       );


    }


//    @Test
//    @DisplayName("sender와 receiver 조회 테스트")
//    public void selectSenderReceiver() {
//
//       int msgCode = 2;
//       UUID userCode = UUID.fromString("00000000-3132-3132-0000-000000000000");
//
//        List<MsgUserDTO> senderReceiver = msgService.selectSenderReceiver(msgCode, userCode);
//
//
//        Assertions.assertFalse(senderReceiver.isEmpty());
//        senderReceiver.forEach(row -> {
//            for(MsgUserDTO msgUserDTO : senderReceiver) {
//                System.out.println("msgCode: " + msgUserDTO.getMsgCode());
//                System.out.println("userCode : " + msgUserDTO.getUserCode());
//                System.out.println("senderCode : " + msgUserDTO.getSenderCode());
//                System.out.println("receiverCode : " + msgUserDTO.getReceiverCode());
//                System.out.println("id : " + msgUserDTO.getId());
//                System.out.println("name : " + msgUserDTO.getName());
//            }
//            System.out.println();
//        });
//    }


    @Test
    @DisplayName("쪽지 모달 receiver 조회 테스트")
    public void selectReceiver(){

        List<MsgReceiverDTO> receiver = msgService.selectReceiver();

        Assertions.assertFalse(receiver.isEmpty());
        receiver.forEach(row -> {
            for(MsgReceiverDTO msgReceiverDTO : receiver){
                System.out.println("msgCode : " + msgReceiverDTO.getMsgCode());
                System.out.println("receiverCode : " + msgReceiverDTO.getReceiverCode());
                System.out.println("productCode : " + msgReceiverDTO.getProductCode());
                System.out.println("userCode : " + msgReceiverDTO.getUserCode());
                System.out.println("userName : " + msgReceiverDTO.getName());
                System.out.println("id : " + msgReceiverDTO.getId());
            }
        });

    }


    @Test
    @DisplayName("쪽지 내용 상세 조회")
    public void selectMsgDetail(){

        int msgCode = 2;

        List<MsgProUserInfoDTO> msgDetail = msgService.selectMsgDetail(msgCode);
        System.out.println("test: " + msgDetail);
        Assertions.assertFalse(msgDetail.isEmpty());
        msgDetail.forEach(detail -> {
            System.out.println("msgCode: " + detail.getMsgCode());
            System.out.println("msgContent: " + detail.getMsgContent());
            System.out.println("msgStatus : " + detail.getMsgStatus());
            System.out.println("msgTime : " + detail.getMsgTime());
            System.out.println("senderCode: " + detail.getSenderCode());
            System.out.println("receiverCode : " + detail.getReceiverCode());
            System.out.println("id : " + detail.getId());
            System.out.println("name : " + detail.getName());
            System.out.println("productCode: " + detail.getProductCode());
            System.out.println("productName : " + detail.getProductName());
            System.out.println("productDesc : " + detail.getProductDesc());
            System.out.println("msgDeleteInfoMsgDeleteDTO : " + detail.getMsgDeleteCode());
            System.out.println("msgDeleteInfoMsgDeleteDTO : " + detail.getMsgDeleteStatus());
        });
    }





//    @Test
//    @DisplayName("쪽지함 조회")
//    public void selectMsgList(){
//
//        int userCode = 1;
//        int offset = 0;
//        int limit = 10;
//
//        // 가정: isReceived 값을 null로 설정하여 모든 쪽지를 조회
//        List<MsgListDTO> msgList = msgService.getMessages(userCode, offset, limit, true);
//
//        Assertions.assertFalse(msgList.isEmpty());
//        for (MsgListDTO msgListDTO : msgList) {
//            System.out.println(msgListDTO);
//        }
//    }
}
