package com.mergeco.oiljang.inquiry.repository;

import com.mergeco.oiljang.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InqRepository extends JpaRepository<Inquiry, Integer> {
    Long countByRefUserCode(int userCode);
}
