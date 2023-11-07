package com.mergeco.oiljang.user.repository;

import com.mergeco.oiljang.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*import javax.persistence.EntityManager;*/
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(String id);

    Optional<User> findByNickname(String nickname);

   /* default void clearStore() {
        EntityManager store = null;
        store.clear();
    }*/

}
