package com.team.youarelikemetoo.user.repository;

import com.team.youarelikemetoo.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByOauthId(String oauthId);
    Optional<UserEntity> findByOauthIdAndOauthProvider(String id, String oauthProvider);

}
