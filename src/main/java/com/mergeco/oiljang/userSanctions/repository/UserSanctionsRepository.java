package com.mergeco.oiljang.userSanctions.repository;

import com.mergeco.oiljang.userSanctions.entity.UserSanctions;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSanctionsRepository extends JpaRepository<UserSanctions, Integer> {

    @Query(value =
            "SELECT s.ref_user_code, s.sanctions_date, s.manager_date, s.sanctions_code " +
                    "FROM user_sanctions s " +
                    "WHERE s.ref_user_code = :refUserCode ", nativeQuery = true)
    UserSanctions findByRefUserCode(@Param("refUserCode") int refUserCode);
}
