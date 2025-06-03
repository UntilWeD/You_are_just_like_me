package com.team.youarelikemetoo.alarm.repository;

import com.team.youarelikemetoo.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlarmJPARepository extends JpaRepository<Alarm, Long> {
    public Optional<Alarm> findById(Long alarmId);
}
