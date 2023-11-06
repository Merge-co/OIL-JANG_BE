package com.mergeco.oiljang.message.repository;

import com.mergeco.oiljang.message.entity.MsgDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MsgDeleteRepository extends JpaRepository<MsgDelete, Integer> {
}
