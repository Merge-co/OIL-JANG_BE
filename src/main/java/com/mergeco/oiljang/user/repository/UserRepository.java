package com.mergeco.oiljang.user.repository;

import com.mergeco.oiljang.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/*import javax.persistence.EntityManager;*/
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT up.id FROM User up WHERE up.id = :id")
    Optional<User> findById(String id);


    @Query("SELECT up.nickname FROM User up WHERE up.nickname = :nickname")
    String checkUserNicknameExist(String nickname);

    @Query("SELECT up.id FROM User up WHERE up.id = :id")
    String checkUserIdExist(String id);

    @Query("SELECT up FROM User up WHERE up.id = :userId")
    User findByUserId(String userId);

    @Query("SELECT up FROM User up WHERE up.nickname = :newNickname")
    User findByNickname(String newNickname);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.profileImageUrl = :profileImageUrl WHERE u.userCode = :userCode")
    int editProfileUrl(@Param("profileImageUrl") String profileImageUrl, @Param("userCode") int userCode);

    @Query("SELECT up FROM User up WHERE up.email = :email")
    Optional<User> findByEmail(String email);
}
