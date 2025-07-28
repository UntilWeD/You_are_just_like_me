package com.team.youarelikemetoo.user.repository;

import com.team.youarelikemetoo.user.entity.UserProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileImageJpaRepository extends JpaRepository<UserProfileImage, Long> {
}
