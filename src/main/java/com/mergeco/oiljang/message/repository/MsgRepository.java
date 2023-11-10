package com.mergeco.oiljang.message.repository;

import com.mergeco.oiljang.message.dto.MsgListDTO;
import com.mergeco.oiljang.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface MsgRepository extends JpaRepository<Message, Integer> {
//
//        @Query("SELECT m FROM message_and_delete m " +
//                "WHERE (m.senderCode = :userCode AND m.msgStatus IN ('N', 'Y') AND COALESCE(:isReceived, true) = :isReceived) " +
//                "OR (m.receiverCode = :userCode AND m.msgStatus IN ('N', 'Y') AND COALESCE(:isReceived, false) = :isReceived)")
//        List<MsgListDTO> findMessages(@Param("userCode") int userCode, @Param("isReceived") int offset, @Param("isReceived") int limit, @Param("isReceived") Boolean isReceived);
}
