package com.team.youarelikemetoo.alarmFeed.repository;

import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmFeedJPARepository extends JpaRepository<AlarmFeed, Long> {

    @Query("SELECT af FROM AlarmFeed af LEFT JOIN FETCH af.images WHERE af.id = :id")
    Optional<AlarmFeed> findByIdWithImages(@Param("id") Long id);

    @Query("SELECT DISTINCT af FROM AlarmFeed af " +
            "JOIN af.alarmFeedDays afd " +
            "WHERE afd.dayOfWeek IN :dayOfWeek")
    List<AlarmFeed> findByDayOfWeekIn(@Param("dayOfWeek") List<Integer> dayOfWeek);

}
