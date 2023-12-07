package com.mergeco.oiljang.message.service;

import com.mergeco.oiljang.message.dto.*;
import com.mergeco.oiljang.message.entity.Message;
import com.mergeco.oiljang.message.entity.MsgDeleteInfo;
import com.mergeco.oiljang.message.repository.MsgDeleteRepository;
import com.mergeco.oiljang.message.repository.MsgRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Service
public class MsgService {

    @PersistenceContext
    private EntityManager entityManager;

    private final MsgRepository msgRepository;
    private final ModelMapper modelMapper;

    private final MsgDeleteRepository msgDeleteRepository;

    public MsgService(MsgRepository msgRepository,
                      ModelMapper modelMapper,
                      MsgDeleteRepository msgDeleteRepository) {
        this.msgRepository = msgRepository;
        this.modelMapper = modelMapper;
        this.msgDeleteRepository = msgDeleteRepository;
    }

    @Transactional
    public String insertMsg(MsgInsertDTO msgInfo) {
        int result = 0;

        try{
            System.out.println("msgInfo: " + msgInfo);
            msgRepository.save(modelMapper.map(msgInfo, Message.class));
            result = 1;
        }catch(Exception e){
            System.out.println("Exception! " + e);
        }
        return (result > 0) ? "쪽지 등록 성공" : "쪽지 등록 실패";
    }


//    public List<MsgUserDTO> selectSenderReceiver(int msgCode, UUID userCode) {
//        String jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgUserDTO(m.msgCode, u.userCode ,m.senderCode, m.receiverCode, u.id, u.name) "
//                + "FROM message_and_delete m "
//                + "LEFT JOIN User u ON m.senderCode = :userCode OR m.receiverCode = :userCode "
//                + "WHERE m.msgCode = :msgCode";
//        List<MsgUserDTO> userList = entityManager.createQuery(jpql, MsgUserDTO.class)
//                .setParameter("msgCode", msgCode)
//                .setParameter("userCode", userCode)
//                .getResultList();
//
//        return userList;
//
//        //ON (m.senderCode = :userCode OR m.receiverCode = :userCode) AND
//
//    }





//    public List<MsgReceiverDTO> selectReceiver(int userCode, int productCode) {
//        String query = "SELECT m.receiver_code, m.ref_product_code, p.product_code, u.user_code, u.name, u.id "
//                + "FROM message m "
//                + "LEFT JOIN user_info u ON m.receiver_code = u.user_code "
//                + "LEFT JOIN product_info p ON m.ref_product_code = p.product_code "
//                + "WHERE m.receiver_code = :userCode AND p.product_code = :productCode";
//
//        List<Object[]> resultList = entityManager.createNativeQuery(query)
//                .setParameter("productCode", productCode)
//                .setParameter("userCode", userCode)
//                .getResultList();
//        System.out.println("resultList : " + resultList);
//        List<MsgReceiverDTO> receiverList = new ArrayList<>();
//        System.out.println("userCode : ===================="+ userCode);
//
//        for(Object[] result : resultList){
//            MsgReceiverDTO dto = new MsgReceiverDTO();
//            dto.setReceiverCode((Integer) result[1]);
//            dto.setRefProductCode((Integer) result[2]);
//            dto.setProductCode((Integer) result[3]);
//            dto.setUserCode((Integer) result[4]);
//            dto.setName((String) result[5]);
//            dto.setId((String) result[6]);
//
//            System.out.println("result!!!:" + Arrays.toString(result));
//
//            receiverList.add(dto);
//
//
//        }
//        return receiverList;
//    }


    //내 이름.아이디는 토큰에서 가져오기
    public List<MsgProUserInfoDTO> selectMsgDetail(int msgCode) {
        String jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgProUserInfoDTO(m.msgCode, m.msgContent, m.msgStatus, m.msgTime, m.senderCode, m.receiverCode, u.userCode, u.id, u.name, m.refProductCode, p.productCode, p.productName, p.productDesc, md.msgDeleteCode, md.msgDeleteStatus) "
                + "FROM message_and_delete m "
                + "LEFT JOIN User u ON m.senderCode = u.userCode OR m.receiverCode = u.userCode "
                + "LEFT JOIN Product p ON m.refProductCode = p.productCode "
                + "JOIN m.msgDeleteInfo md "
                + "WHERE m.msgCode = :msgCode";

        List<MsgProUserInfoDTO> msgProUserList = entityManager.createQuery(jpql, MsgProUserInfoDTO.class)
                .setParameter("msgCode", msgCode)
                .getResultList();


        System.out.println("service : " + msgProUserList);


        return msgProUserList;
    }

    @Transactional
    public int updateMsgStatus(int msgCode) {
        Message message = msgRepository.findById(msgCode).orElseThrow(IllegalArgumentException::new);

        message.msgStatus("Y").builder();
        message = message.msgStatus("Y").builder();
        msgRepository.save(message);


        System.out.println(message);
        return msgCode;
    }


    public List<MsgListDTO> getMessages(int userCode, int page, int offset, int limit, Boolean isReceived, String keyword) {
        String jpql;
        if (isReceived != null && isReceived) {
            jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.msgCode, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
                    + "FROM message_and_delete m "
                    + "LEFT JOIN User u ON m.senderCode = u.userCode "
                    + "JOIN m.msgDeleteInfo md "
                    + "WHERE m.receiverCode = :userCode AND md.msgDeleteCode IN (1, 3) ";


            System.out.println("거치는지 확인");

            if(keyword != null && keyword != ""){
                jpql += "AND m.msgContent LIKE :keyword OR u.name LIKE :keyword ORDER BY m.msgTime DESC ";

                TypedQuery<MsgListDTO> query = entityManager.createQuery(jpql, MsgListDTO.class)
                        .setParameter("userCode", userCode)
                        .setParameter("keyword", "%" + keyword + "%")
                        .setFirstResult(offset)
                        .setMaxResults(limit);
                return query.getResultList();
            }

        } else {
            jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.msgCode, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
                    + "FROM message_and_delete m "
                    + "LEFT JOIN User u ON m.receiverCode = u.userCode "
                    + "JOIN m.msgDeleteInfo md "
                    + "WHERE m.senderCode = :userCode AND md.msgDeleteCode IN (1, 2) ";



            if(keyword != null && keyword != ""){
                jpql += "AND m.msgContent LIKE :keyword OR u.name LIKE :keyword ORDER BY m.msgTime DESC ";

                TypedQuery<MsgListDTO> query = entityManager.createQuery(jpql, MsgListDTO.class)
                        .setParameter("userCode", userCode)
                        .setParameter("keyword", "%" + keyword + "%")
                        .setFirstResult(offset)
                        .setMaxResults(limit);
                return query.getResultList();
            }

            System.out.println("거치는지 확인 2222222222222222");
        }

        if(keyword == null || keyword.isEmpty()){
            keyword = "";
        }
        jpql += "ORDER BY m.msgTime DESC";

        TypedQuery<MsgListDTO> query = entityManager.createQuery(jpql, MsgListDTO.class)
                .setParameter("userCode", userCode)
                .setFirstResult(offset)
                .setMaxResults(limit);

        return query.getResultList();
    }


    public Long countMsgList(Integer page, int userCode, int offset, int limit, Boolean isReceived, String keyword) {
        Long countPage = msgRepository.count();
        return countPage;
    }

    private boolean isDeletedBySender(int sender, MsgDeleteInfo msgDeleteInfo){
        if(msgDeleteInfo.getMsgDeleteCode() == 2){
            return true;
        }
        return false;
    }

    private boolean isDeletedByReceiver(int receiver, MsgDeleteInfo msgDeleteInfo){
        if(msgDeleteInfo.getMsgDeleteCode() == 3){
            return true;
        }
        return false;
    }

    @Transactional
    public int updateDeleteCode(int msgCode) {


        int result = 0;

        try {
            Message message = msgRepository.findById(msgCode).orElseThrow(IllegalArgumentException::new);

            int sender = message.getSenderCode();
            int receiver = message.getReceiverCode();

            MsgDeleteInfo msgDeleteInfo;

            // MsgDeleteInfo 가져오기
            if (isDeletedBySender(sender, message.getMsgDeleteInfo()) && isDeletedByReceiver(receiver, message.getMsgDeleteInfo())) {
                msgDeleteInfo = (message.getMsgDeleteInfo().getMsgDeleteCode() != 4 && message.getMsgDeleteInfo().getMsgDeleteCode() != 1)
                        ? msgDeleteRepository.findByMsgDeleteCode(4)
                        : message.getMsgDeleteInfo();
            } else if (isDeletedBySender(sender, message.getMsgDeleteInfo())) {
                msgDeleteInfo = (message.getMsgDeleteInfo().getMsgDeleteCode() != 2 && message.getMsgDeleteInfo().getMsgDeleteCode() == 1)
                        ? msgDeleteRepository.findByMsgDeleteCode(2)
                        : message.getMsgDeleteInfo();
            } else if (isDeletedByReceiver(receiver, message.getMsgDeleteInfo())) {
                msgDeleteInfo = (message.getMsgDeleteInfo().getMsgDeleteCode() != 3 && message.getMsgDeleteInfo().getMsgDeleteCode() == 1)
                        ? msgDeleteRepository.findByMsgDeleteCode(3)
                        : message.getMsgDeleteInfo();
            } else {
                if (message.getMsgDeleteInfo().getMsgDeleteCode() == 1) {
                    msgDeleteInfo = (message.getMsgDeleteInfo().getMsgDeleteCode() != 2 && message.getMsgDeleteInfo().getMsgDeleteCode() != 4)
                            ? msgDeleteRepository.findByMsgDeleteCode(2)
                            : (
                                    (message.getMsgDeleteInfo().getMsgDeleteCode() != 3 && message.getMsgDeleteInfo().getMsgDeleteCode() != 4)
                                    ? msgDeleteRepository.findByMsgDeleteCode(3)
                                    : message.getMsgDeleteInfo()
                            );
                } else {
                    msgDeleteInfo = message.getMsgDeleteInfo();
                }
            }

            // 업데이트된 Message 인스턴스 생성
            Message updatedMessage = message.msgDeleteInfo(msgDeleteInfo).builder();

            // 업데이트된 메시지 저장
            msgRepository.save(updatedMessage);

            return 1;
        } catch (Exception e) {
            System.out.println("exception!!!!!!!!! " + e);
            throw new RuntimeException(e);
        }
    }



//    public List<MsgListDTO> selectMsgLike(int userCode, int offset, int limit, Boolean isReceived, String keyword) {
//        String jpql;
//
//
//        if(keyword == null || keyword.equals("")) {
//            keyword = "";
//            if ((isReceived != null && isReceived)) {
//                jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.msgCode, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
//                        + "FROM message_and_delete m "
//                        + "LEFT JOIN User u ON m.receiverCode = u.userCode "
//                        + "JOIN m.msgDeleteInfo md "
//                        + "WHERE m.receiverCode = :userCode AND md.msgDeleteCode IN (1, 3) AND m.senderCode <> u.userCode";
//            } else {
//                jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.msgCode, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
//                        + "FROM message_and_delete m "
//                        + "LEFT JOIN User u ON m.senderCode = u.userCode "
//                        + "JOIN m.msgDeleteInfo md "
//                        + "WHERE m.senderCode = :userCode AND md.msgDeleteCode IN (1, 2)";
//            }
//
//        }else{
//            if ((isReceived != null && isReceived)) {
//                jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.msgCode, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
//                        + "FROM message_and_delete m "
//                        + "LEFT JOIN User u ON m.receiverCode = u.userCode "
//                        + "JOIN m.msgDeleteInfo md "
//                        + "WHERE m.receiverCode = :userCode AND md.msgDeleteCode IN (1, 3) AND m.senderCode <> u.userCode "
//                        + "AND (m.msgContent LIKE CONCAT('%', :keyword, '%') OR u.name LIKE CONCAT('%', :keyword, '%'))";
//
//                System.out.println("확인==================");
//            } else {
//                jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.msgCode, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
//                        + "FROM message_and_delete m "
//                        + "LEFT JOIN User u ON m.senderCode = u.userCode "
//                        + "JOIN m.msgDeleteInfo md "
//                        + "WHERE m.senderCode = :userCode AND md.msgDeleteCode IN (1, 2) "
//                        + "AND (m.msgContent LIKE CONCAT('%', :keyword, '%') OR u.name LIKE CONCAT('%', :keyword, '%'))";
//
//                System.out.println("확인2==================");
//            }
//        }
//
//
//
//
//
//        TypedQuery<MsgListDTO> query = entityManager.createQuery(jpql, MsgListDTO.class);
//        query.setParameter("userCode", userCode);
//        query.setParameter("keyword", keyword);
//
//        List<MsgListDTO> resultList = query.getResultList();
//
//        if (resultList.isEmpty()) {
//            System.out.println("검색 결과가 없습니다. 알림을 표시하세요."); // 여기에서는 간단히 콘솔에 출력하는 예시입니다.
//        }
//
//
//
//
//
//
//        return query.getResultList();
//    }



}

