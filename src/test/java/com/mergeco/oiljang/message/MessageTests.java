package com.mergeco.oiljang.message;

import com.mergeco.oiljang.message.dto.*;
import com.mergeco.oiljang.message.entity.Message;
import com.mergeco.oiljang.message.entity.MsgDeleteInfo;
import com.mergeco.oiljang.message.repository.MsgRepository;
import com.mergeco.oiljang.message.service.MsgService;
import com.mergeco.oiljang.report.entity.Report;
import com.mergeco.oiljang.user.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
public class MessageTests {

    @Autowired
    private MsgService msgService;

    @Autowired
    private MsgRepository msgRepository;


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


        return Stream.of(Arguments.of("맥북 사고싶어요", "N", msgTime, 2,
                1, 2, 1, "N")
        );
    }


    @DisplayName("쪽지 등록 테스트")
    @ParameterizedTest
    @MethodSource("getMsgInfos")
    void msgInsertTest(String msgContent, String msgStatus,
                       LocalDateTime msgTime,int refProductCode, int senderCode, int receiverCode,
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



        Assertions.assertDoesNotThrow(
                () -> msgService.insertMsg(msgInfo)
        );


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


//    @Test
//    @DisplayName("쪽지 모달 receiver 조회 테스트")
//    public void selectReceiver(){
//
//        int msgCode = 1;
//        int userCode = 1;
//        int productCode = 1;
//
//        List<MsgReceiverDTO> receiver = msgService.selectReceiver(userCode, productCode);
//
//        Assertions.assertFalse(receiver.isEmpty());
//        receiver.forEach(row -> {
//            for(MsgReceiverDTO msgReceiverDTO : receiver){
//                System.out.println("receiverCode : " + msgReceiverDTO.getReceiverCode());
//                System.out.println("productCode : " + msgReceiverDTO.getProductCode());
//                System.out.println("userCode : " + msgReceiverDTO.getUserCode());
//                System.out.println("userName : " + msgReceiverDTO.getName());
//                System.out.println("id : " + msgReceiverDTO.getId());
//            }
//        });
//
//    }


    @Test
    @DisplayName("쪽지 내용 상세 조회")
    public void selectMsgDetail(){

        int msgCode = 3;

        List<MsgProUserInfoDTO> msgDetail = msgService.selectMsgDetail(msgCode);
        System.out.println("test: " + msgDetail);

//            Assertions.assertFalse(msgDetail.isEmpty());
        msgDetail.forEach(detail -> {
            System.out.println("msgCode: " + detail.getMsgCode());
            System.out.println("msgContent: " + detail.getMsgContent());
            System.out.println("msgStatus : " + detail.getMsgStatus());
            System.out.println("msgTime : " + detail.getMsgTime());
            System.out.println("senderCode: " + detail.getSenderCode());
            System.out.println("receiverCode : " + detail.getReceiverCode());
            System.out.println("id : " + detail.getId());
            System.out.println("name : " + detail.getName());
            System.out.println("productCode: " + detail.getRefProductCode());
            System.out.println("productName : " + detail.getProductName());
            System.out.println("productDesc : " + detail.getProductDesc());
            System.out.println("msgDeleteInfoMsgDeleteDTO : " + detail.getMsgDeleteCode());
            System.out.println("msgDeleteInfoMsgDeleteDTO : " + detail.getMsgDeleteStatus());
        });
        msgService.updateMsgStatus(3);

        Assertions.assertEquals(3, msgService.updateMsgStatus(3));
    }



    @Test
    void updateMsgStatus(){

        int msg = msgService.updateMsgStatus(5);

        Assertions.assertEquals(5, msg);
    }


    @Test
    @DisplayName("쪽지함 조회")
    public void selectMsgList(){
        int userCode = 1;
        int page = Integer.parseInt(null);
        int offset = 0;
        int limit = 9;
        String keyword = "맥";

        List<MsgListDTO> msgList = msgService.getMessages(userCode, page, offset, limit, false, keyword);

        Assertions.assertTrue(msgList.size() >= 0);
        for (MsgListDTO msgListDTO : msgList) {
            System.out.println(msgListDTO);
        }
    }


    /*
     * 1 -> B
     * 2 -> R
     * 3 -> S
     * 4 -> N
     * */

    @Test
    @DisplayName("쪽지 삭제에 따른 상태값 변경 테스트")
    public void deleteMsg() {

        int msgCode = 76;
//        int senderCode = 1;
//        int receiverCode = 3;
        Optional<Message> message = msgRepository.findById(msgCode);

        // 쪽지가 존재하면
        if (message.isPresent()) {
            // 쪽지 삭제 상태 업데이트
            int result = msgService.updateDeleteCode(msgCode);

            // 쪽지가 성공적으로 업데이트되었는지 확인
            Assertions.assertEquals(1, result);

            // 업데이트된 쪽지를 다시 조회
            Optional<Message> updatedMessage = msgRepository.findById(msgCode);

            // 업데이트된 쪽지 상태 확인
            Assertions.assertTrue(updatedMessage.isPresent());
            Assertions.assertEquals(2, updatedMessage.get().getMsgDeleteInfo().getMsgDeleteCode());
        } else {
            Assertions.fail("쪽지가 존재하지 않습니다.");
        }
    }




//    @DisplayName("LIKE 연산자를 활용한 조회")
//    @Test
//      public void selectMsgLike(){
//
//        int userCode = 1;
//        int offset = 0;
//        int limit = 9;
//        String keyword = "맥";
//
//        List<MsgListDTO> msgList = msgService.selectMsgLike(userCode, offset, limit, true, keyword);
//
//        msgList.forEach(System.out::println);
//        Assertions.assertNotNull(msgList);
//
//
//    }
}
