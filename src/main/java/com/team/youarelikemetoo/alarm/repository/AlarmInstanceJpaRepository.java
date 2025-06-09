package com.team.youarelikemetoo.alarm.repository;

import com.team.youarelikemetoo.alarm.entity.AlarmInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmInstanceJpaRepository extends JpaRepository<AlarmInstance, Long> {

}
