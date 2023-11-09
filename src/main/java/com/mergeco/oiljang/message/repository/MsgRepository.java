package com.mergeco.oiljang.message.repository;

import com.mergeco.oiljang.message.dto.MsgListDTO;
import com.mergeco.oiljang.message.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface MsgRepository extends JpaRepository<Message, Integer> {

//    @Query("SELECT NEW com.mergeco.oiljang.message.dto.MsgListDTO(" +
//            ":userCode, " +
//            "CASE WHEN m.receiverCode = :userCode THEN m.senderCode ELSE m.receiverCode END," +
//            "m.msgContent, " +
//            "CASE WHEN m.receiverCode = :userCode THEN m.msgTime ELSE m.msgTime END," +
//            ":isReceived) " +
//            "FROM message_and_delete m " +
//            "WHERE (m.receiverCode = :userCode AND :isReceived = TRUE) OR (m.senderCode = :userCode AND :isReceived = FALSE)")
//    List<MsgListDTO> getMessages(@Param("userCode") String userCode, @Param("isReceived") boolean isReceived, Pageable pageable);
//}

}
