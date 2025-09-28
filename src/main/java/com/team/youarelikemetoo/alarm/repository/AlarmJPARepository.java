package com.team.youarelikemetoo.alarm.repository;

import com.team.youarelikemetoo.alarm.entity.Alarm;
import com.team.youarelikemetoo.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmJPARepository extends JpaRepository<Alarm, Long> {

    public Optional<Alarm> findById(Long alarmId);
    public List<Alarm> findAllByUser(UserEntity user);
    public Alarm findByAlarmFeedId(Long alarmFeedId);

}
