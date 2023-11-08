package com.mergeco.oiljang.message.repository;

import com.mergeco.oiljang.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface MsgRepository extends JpaRepository<Message, Integer> {

}
