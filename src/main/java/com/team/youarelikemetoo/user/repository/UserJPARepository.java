package com.team.youarelikemetoo.user.repository;

import com.team.youarelikemetoo.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.userProfileImage WHERE u.oauthId = :oauthId")
    Optional<UserEntity> findByOauthId(@Param("oauthId") String oauthId);
    Optional<UserEntity> findByOauthIdAndOauthProvider(String id, String oauthProvider);

}
