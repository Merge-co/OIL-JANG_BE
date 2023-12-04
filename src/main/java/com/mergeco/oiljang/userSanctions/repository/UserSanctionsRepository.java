package com.mergeco.oiljang.userSanctions.repository;

import com.mergeco.oiljang.userSanctions.entity.UserSanctions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSanctionsRepository extends JpaRepository<UserSanctions, Integer> {
}
