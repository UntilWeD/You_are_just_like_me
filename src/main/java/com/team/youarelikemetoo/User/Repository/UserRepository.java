package com.team.youarelikemetoo.User.Repository;

import com.team.youarelikemetoo.User.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
    Optional<UserEntity> findByOauth_idAndProvider(String id, String provider);

}
