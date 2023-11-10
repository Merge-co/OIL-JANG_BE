package com.mergeco.oiljang.message.repository;

import com.mergeco.oiljang.message.dto.MsgListDTO;
import com.mergeco.oiljang.message.entity.Message;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface MsgRepository extends JpaRepository<Message, Integer> {

//    @Query("SELECT m FROM message_and_delete m " +
//            "WHERE (m.senderCode = :userCode AND m.msgStatus IN ('N', 'Y') AND COALESCE(:isReceived, true) = :isReceived) " +
//            "OR (m.receiverCode = :userCode AND m.msgStatus IN ('N', 'Y') AND COALESCE(:isReceived, false) = :isReceived)")
//    List<MsgListDTO> findMessages(@org.springframework.data.repository.query.Param("userCode") int userCode, @org.springframework.data.repository.query.Param("isReceived") int offset, @org.springframework.data.repository.query.Param("isReceived") int limit, @Param("isReceived") Boolean isReceived);

}
