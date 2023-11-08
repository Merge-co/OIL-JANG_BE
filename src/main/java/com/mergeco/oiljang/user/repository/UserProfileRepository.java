package com.mergeco.oiljang.user.repository;

import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    @Query("SELECT up FROM UserProfile up WHERE up.refUserCode = :user")
    UserProfile findByUser(@Param("user") User user);

}
