package com.team.youarelikemetoo.alarmFeed.service;


import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedCommentDTO;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeedComment;
import com.team.youarelikemetoo.alarmFeed.repository.AlarmFeedCommentJpaRepository;
import com.team.youarelikemetoo.alarmFeed.repository.AlarmFeedJPARepository;
import com.team.youarelikemetoo.alarmFeed.repository.mybatis.MyBatisAlarmFeedCommentRepository;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmFeedCommentService {


    private final AlarmFeedCommentJpaRepository alarmFeedCommentJpaRepository;
    private final UserJPARepository userJPARepository;
    private final AlarmFeedJPARepository alarmFeedJPARepository;
    private final MyBatisAlarmFeedCommentRepository myBatisAlarmFeedCommentRepository;


    public AlarmFeedCommentDTO saveAlarmFeedComment(AlarmFeedCommentDTO dto, Long userId) {
        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AlarmFeed alarmFeed = alarmFeedJPARepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("AlarmFeed Not Found"));



        AlarmFeedComment entity = dto.toEntity(user, alarmFeed);
        entity = alarmFeedCommentJpaRepository.save(entity);

        return AlarmFeedCommentDTO.fromEntity(entity);
    }

    public void deleteAlarmFeedComment(Long alarmFeedCommentId) {
        AlarmFeedComment alarmFeedComment = alarmFeedCommentJpaRepository.getById(alarmFeedCommentId);

        alarmFeedCommentJpaRepository.delete(alarmFeedComment);
        return;
    }

    public AlarmFeedCommentDTO readAlarmFeedComment(Long alarmFeedCommentId) {

        AlarmFeedComment alarmFeedComment = alarmFeedCommentJpaRepository.getById(alarmFeedCommentId);
        return AlarmFeedCommentDTO.fromEntity(alarmFeedComment);
    }

    public AlarmFeedCommentDTO updateAlarmFeedComment(Long alarmFeedCommentId, AlarmFeedCommentDTO alarmFeedCommentDTO) {
        AlarmFeedComment alarmFeedComment = alarmFeedCommentJpaRepository.getById(alarmFeedCommentId);

        alarmFeedComment.updateAlarmFeedComment(alarmFeedCommentDTO);

        return AlarmFeedCommentDTO.fromEntity(alarmFeedComment);
    }


    public List<AlarmFeedCommentDTO> readAlarmFeedCommentList(Long alarmFeedId) {
        return myBatisAlarmFeedCommentRepository.findAllAlarmFeedCommentsByAlarmFeedId(alarmFeedId);
    }
}
