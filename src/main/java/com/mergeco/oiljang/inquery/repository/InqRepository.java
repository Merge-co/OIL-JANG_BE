package com.mergeco.oiljang.inquery.repository;

import com.mergeco.oiljang.inquery.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface InqRepository extends JpaRepository<Inquiry, Integer> {
}
