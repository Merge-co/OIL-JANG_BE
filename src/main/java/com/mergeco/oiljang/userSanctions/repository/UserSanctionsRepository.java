package com.mergeco.oiljang.userSanctions.repository;

import com.mergeco.oiljang.userSanctions.entity.UserSanctions;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface UserSanctionsRepository extends JpaRepository<UserSanctions, Integer> {





    @Query(value ="SELECT s.sanctions_date FROM user_sanctions s WHERE s.ref_User_Code = :userCode " , nativeQuery = true)
    Date findByUserDate(@Param("userCode") int userCode);

}
