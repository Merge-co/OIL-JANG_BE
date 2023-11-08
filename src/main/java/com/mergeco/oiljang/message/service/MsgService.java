package com.mergeco.oiljang.message.service;

import com.mergeco.oiljang.message.dto.MsgDeleteDTO;
import com.mergeco.oiljang.message.dto.MsgInsertDTO;
import com.mergeco.oiljang.message.dto.MsgProUserInfoDTO;
import com.mergeco.oiljang.message.dto.MsgUserDTO;
import com.mergeco.oiljang.message.entity.Message;
import com.mergeco.oiljang.message.repository.MsgDeleteRepository;
import com.mergeco.oiljang.message.repository.MsgRepository;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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


    public List<MsgUserDTO> selectSenderReceiver(int msgCode, String token, UUID userCode) {
        String jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgUserDTO(m.msgCode, u.userCode ,m.senderCode, m.receiverCode, u.id, u.name, u.token) "
                + "FROM message_and_delete m "
                + "RIGHT JOIN User u ON (m.senderCode = :userCode OR m.receiverCode = :userCode) AND u.token = :token "
                + "WHERE m.msgCode = :msgCode";
        List<MsgUserDTO> userList = entityManager.createQuery(jpql, MsgUserDTO.class)
                .setParameter("msgCode", msgCode)
                .setParameter("token", token)
                .setParameter("userCode", userCode)
                .getResultList();

        return userList;

        //ON (m.senderCode = :userCode OR m.receiverCode = :userCode) AND

//        private int msgCode;
//        private UUID senderCode;
//        private UUID receiverCode;
//        private String id;
//        private String name;
//        private String token;
    }


//    public List<MsgProUserInfoDTO> selectMsgDetail(int msgCode) {
//        String jpql = "SELECT new com.mergeco.oiljang.message.dto.MsgProUserInfoDTO(m.msgCode, m.msgContent, m.msgStatus, m.msgTime, m.senderCode, m.receiverCode, u.id, u.name, u.token, p.productCode, p.productName, p.productDesc, md.msgDeleteCode, md.msgDeleteStatus) "
//                + "FROM message_and_delete m "
//                + "RIGHT JOIN User u "
//                + "RIGHT JOIN Product p "
//                + "LEFT JOIN m.msgDeleteInfo md "
//                + "WHERE m.msgCode = :msgCode";
//
//       List<MsgProUserInfoDTO> msgProUserList = entityManager.createQuery(jpql, MsgProUserInfoDTO.class)
//               .setParameter("msgCode", msgCode)
//               .getResultList();
//
//       return msgProUserList;
//    }
}
