package com.mergeco.oiljang.message.service;

import com.mergeco.oiljang.message.dto.*;
import com.mergeco.oiljang.message.entity.Message;
import com.mergeco.oiljang.message.repository.MsgDeleteRepository;
import com.mergeco.oiljang.message.repository.MsgRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
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
        System.out.println("service: " + modelMapper.map(msgInfo, Message.class));
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
            dto.setReceiverCode((UUID) result[1]);
            dto.setProductCode((Integer) result[2]);
            dto.setUserCode((UUID) result[3]);
            dto.setName((String) result[4]);
            dto.setId((String) result[5]);

//            System.out.println("result[0] : " + result[0]);
//            System.out.println("result[1] : " + result[1]);
//            System.out.println("result[2] : " + result[2]);
//            System.out.println("result[3] : " + result[3]);
//            System.out.println("result[4] : " + result[4]);
//            System.out.println("result[5] : " + result[5]);

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


//    public List<MsgListDTO> selectMsgList(UUID userCode, int offset, int limit, boolean isReceived) {


//    }
}
