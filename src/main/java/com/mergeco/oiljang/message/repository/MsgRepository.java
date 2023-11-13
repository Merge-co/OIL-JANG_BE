package com.mergeco.oiljang.message.repository;

import com.mergeco.oiljang.message.dto.MsgListDTO;
import com.mergeco.oiljang.message.entity.Message;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


public interface MsgRepository extends JpaRepository<Message, Integer> {


//    @Query("SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime) "
//            + "FROM message_and_delete m "
//            + "LEFT JOIN User u ON m.senderCode = :userCode "
//            + "WHERE m.senderCode = :userCode AND m.msgStatus IN ('삭제', '미삭제') ")
//    List<MsgListDTO> findSentMessages(@Param("userCode") int userCode, @Param("offset") int offset,@Param("limit") int limit, @Param("isReceived") Boolean isReceived);
//
//    @Query("SELECT new com.mergeco.oiljang.message.dto.MsgListDTO(u.userCode, m.senderCode, m.receiverCode, m.msgContent, m.msgStatus, m.msgTime) "
//            + "FROM message_and_delete m "
//            + "LEFT JOIN User u ON m.receiverCode = :userCode "
//            + "WHERE m.senderCode = :userCode AND m.msgStatus IN ('삭제', '미삭제') ")
//    List<MsgListDTO> findReceivedMessages(@Param("userCode") int userCode, @Param("offset") int offset,@Param("limit") int limit, @Param("isReceived") Boolean isReceived);
}
