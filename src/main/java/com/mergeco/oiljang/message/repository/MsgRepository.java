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


//    @Query(value = "SELECT u.user_code, u.name, u.id, m.msg_code, m.sender_code, m.receiver_code, m.msg_content, m.msg_status, m.msg_time, md.msg_delete_code " +
//            "FROM message_and_delete m " +
//            "LEFT JOIN user u ON (CASE WHEN :isReceived THEN m.sender_code ELSE m.receiver_code END) = u.user_code " +
//            "JOIN m.msg_delete_info md " +
//            "WHERE " +
//            "(CASE WHEN :isReceived THEN m.receiver_code ELSE m.sender_code END) = :userCode AND " +
//            "(md.msg_delete_code IN (1, CASE WHEN :isReceived THEN 2 ELSE 3 END)) " +
//            "AND (m.msg_content LIKE :keyword OR u.name LIKE :keyword) " +
//            "ORDER BY m.msg_time DESC " +
//            "LIMIT :limit OFFSET :offset", nativeQuery = true)
//    List<MsgListDTO> getMessages(
//            @Param("userCode") int userCode,
//            @Param("page") int page,
//            @Param("offset") int offset,
//            @Param("limit") int limit,
//            @Param("isReceived") boolean isReceived,
//            @Param("keyword") String keyword
//
//    );
}
