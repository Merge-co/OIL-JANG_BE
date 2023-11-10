package com.mergeco.oiljang.user.repository;

import com.mergeco.oiljang.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*import javax.persistence.EntityManager;*/
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT up FROM User up WHERE up.id = :id")
    Optional<User> findById(String id);


    @Query("SELECT up FROM User up WHERE up.nickname = :nickname")
    Optional<User> findByNickname(String nickname);

   /* default void clearStore() {
        EntityManager store = null;
        store.clear();
    }*/

}
