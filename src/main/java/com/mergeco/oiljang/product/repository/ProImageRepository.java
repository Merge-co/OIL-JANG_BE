package com.mergeco.oiljang.product.repository;

import com.mergeco.oiljang.product.entity.ProImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProImageRepository extends JpaRepository<ProImageInfo, Integer> {

}
