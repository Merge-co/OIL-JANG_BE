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
    public void insertMsg(MsgInsertDTO msgInfo) {
        System.out.println("msgInfo: " + msgInfo);
        msgRepository.save(modelMapper.map(msgInfo, Message.class));
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

    public List<MsgReceiverDTO> selectReceiver() {
        String query = "SELECT m.msg_code, m.receiver_code, p.product_code, u.user_code, u.name, u.id "
                + "FROM message m "
                + "LEFT JOIN user_info u ON m.receiver_code = u.user_code "
                + "RIGHT JOIN product_info p ON u.user_code = p.ref_user_code ";

        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
        System.out.println("resultList : " + resultList);
        List<MsgReceiverDTO> receiverList = new ArrayList<>();

        for(Object[] result : resultList){
            MsgReceiverDTO dto = new MsgReceiverDTO();
            dto.setMsgCode((Integer) result[0]);
            dto.setReceiverCode((Integer) result[1]);
            dto.setProductCode((Integer) result[2]);
            dto.setUserCode((Integer) result[3]);
            dto.setName((String) result[4]);
            dto.setId((String) result[5]);

            System.out.println("result[0] : " + result[0]);
            System.out.println("result[1] : " + result[1]);
            System.out.println("result[2] : " + result[2]);
            System.out.println("result[3] : " + result[3]);
            System.out.println("result[4] : " + result[4]);
            System.out.println("result[5] : " + result[5]);

//            dto.setMsgCode(dto.getMsgCode());
//            dto.setReceiverCode(dto.getReceiverCode());
//            dto.setProductCode(dto.getProductCode());
//            dto.setUserCode(dto.getUserCode());
//            dto.setName(dto.getName());
//            dto.setId(dto.getId());

            System.out.println("result!!!:" + Arrays.toString(result));

            receiverList.add(dto);


        }
        return receiverList;
    }


    //내 이름.아이디는 토큰에서 가져오기
    public List<MsgProUserInfoDTO> selectMsgDetail(int msgCode) {
        String jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgProUserInfoDTO(m.msgCode, m.msgContent, m.msgStatus, m.msgTime, m.senderCode, m.receiverCode, u.userCode, u.id, u.name, p.productCode, p.productName, p.productDesc, md.msgDeleteCode, md.msgDeleteStatus) "
                + "FROM message_and_delete m "
                + "LEFT JOIN User u ON m.senderCode = u.userCode OR m.receiverCode = u.userCode "
                + "LEFT JOIN Product p ON m.refProductCode = p.productCode "
                + "LEFT JOIN m.msgDeleteInfo md "
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


    public List<MsgListDTO> getMessages(int userCode, int offset, int limit, Boolean isReceived) {
        String jpql;
        if (isReceived != null && isReceived) {
            jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
                    + "FROM message_and_delete m "
                    + "LEFT JOIN User u ON m.receiverCode = :userCode "
                    + "LEFT JOIN m.msgDeleteInfo md "
                    + "WHERE m.receiverCode = :userCode AND md.msgDeleteCode IN (1, 2) AND m.senderCode <> :userCode";
        } else {
            jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
                    + "FROM message_and_delete m "
                    + "LEFT JOIN User u ON m.senderCode = :userCode "
                    + "LEFT JOIN m.msgDeleteInfo md "
                    + "WHERE m.senderCode = :userCode AND md.msgDeleteCode IN (1, 3)";
        }

        TypedQuery<MsgListDTO> query = entityManager.createQuery(jpql, MsgListDTO.class);
        query.setParameter("userCode", userCode);

        return query.getResultList();
    }


    public Long countMsgList() {
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


            System.out.println("service : " + msgCode);
            int sender = message.getSenderCode();
            int receiver = message.getReceiverCode();

            //MsgDeleteInfo msgDeleteInfo = message.getMsgDeleteInfo();

            //builder를 쓰면 새로 받아줘야하고, 현재 영속화 된 엔티티는 Message이기 때문에 , MsgDeleteInfo를 새로 객체생성해서 값을 받아줬으면
            //해당 값들을 다시 Message엔티티에 세팅해줘야한다.

            System.out.println("sender : " + sender + "receiver :" + receiver);
            System.out.println("message.getMsgDeleteInfo() : " + message.getMsgDeleteInfo());


                if (isDeletedBySender(sender, message.getMsgDeleteInfo()) && isDeletedByReceiver(receiver, message.getMsgDeleteInfo())) {
                    if (message.getMsgDeleteInfo().getMsgDeleteCode() != 4 && message.getMsgDeleteInfo().getMsgDeleteCode() != 1) {
                        System.out.println("4번");
                         message = message.msgDeleteInfo(new MsgDeleteInfo(4, "B")).builder();
                    }

                } else if (isDeletedBySender(sender, message.getMsgDeleteInfo())) {
                    if (message.getMsgDeleteInfo().getMsgDeleteCode() != 2 && message.getMsgDeleteInfo().getMsgDeleteCode() == 1) {
                        System.out.println("2번");

                        message = message.msgDeleteInfo(new MsgDeleteInfo(2, "S")).builder();
                    }
                } else if (isDeletedByReceiver(receiver, message.getMsgDeleteInfo())) {
                    if (message.getMsgDeleteInfo().getMsgDeleteCode() != 3 && message.getMsgDeleteInfo().getMsgDeleteCode() == 1) {
                        System.out.println("3번");
                        message = message.msgDeleteInfo(new MsgDeleteInfo(3, "R")).builder();
                    }

                } else {
                    if (message.getMsgDeleteInfo().getMsgDeleteCode() == 1) {
                        System.out.println("1번");
                        if(isDeletedBySender(sender, message.getMsgDeleteInfo())){
                            message = message.msgDeleteInfo(new MsgDeleteInfo(2, "S")).builder();
                        } else if(isDeletedByReceiver(receiver, message.getMsgDeleteInfo())){
                            message = message.msgDeleteInfo(new MsgDeleteInfo(3, "R")).builder();
                        }

                        System.out.println("1번 메시지 : " + message);
                    }


            }

                    msgRepository.save(message);
                    result = 1;

        } catch (Exception e) {
            System.out.println("exception!!!!!!!!! " + e);
            throw new RuntimeException(e);
        }

        return (result > 0) ? 1 : 2;
    }




    public List<MsgListDTO> selectMsgLike(int userCode, int offset, int limit, Boolean isReceived, String keyword) {
        String jpql;

        if (isReceived != null && isReceived) {
            jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
                    + "FROM message_and_delete m "
                    + "LEFT JOIN User u ON m.receiverCode = :userCode "
                    + "LEFT JOIN m.msgDeleteInfo md "
                    + "WHERE m.receiverCode = :userCode AND md.msgDeleteCode IN (2, 3) AND m.senderCode <> :userCode "
                    + "AND (m.msgContent LIKE :keyword OR u.name LIKE :keyword)";

        } else {
            jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, u.name, u.id, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime, md.msgDeleteCode) "
                    + "FROM message_and_delete m "
                    + "LEFT JOIN User u ON m.senderCode = :userCode "
                    + "LEFT JOIN m.msgDeleteInfo md "
                    + "WHERE m.senderCode = :userCode AND md.msgDeleteCode IN (1, 3) "
                    + "AND (m.msgContent LIKE :keyword OR u.name LIKE :keyword)";
        }

        TypedQuery<MsgListDTO> query = entityManager.createQuery(jpql, MsgListDTO.class);
        query.setParameter("userCode", userCode);
        query.setParameter("keyword", keyword);

        return query.getResultList();
    }


}

