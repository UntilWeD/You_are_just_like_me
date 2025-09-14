package com.team.youarelikemetoo.alarmFeed.service;

import com.team.youarelikemetoo.alarm.dto.AlarmDTO;
import com.team.youarelikemetoo.alarm.repository.AlarmJPARepository;
import com.team.youarelikemetoo.alarm.service.AlarmService;
import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedCommentDTO;
import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import com.team.youarelikemetoo.alarmFeed.entity.*;
import com.team.youarelikemetoo.alarmFeed.repository.AlarmFeedJPARepository;
import com.team.youarelikemetoo.alarmFeed.repository.AlarmFeedLikeJpaRepository;
import com.team.youarelikemetoo.alarmFeed.repository.AlarmFeedShareJpaRepository;
import com.team.youarelikemetoo.alarmFeed.repository.mybatis.MyBatisAlarmFeedCommentRepository;
import com.team.youarelikemetoo.alarmFeed.repository.mybatis.MyBatisAlarmFeedRepository;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmFeedService {

    private final AlarmJPARepository alarmJPARepository;
    private final UserJPARepository userJPARepository;
    private final AlarmFeedJPARepository alarmFeedJPARepository;
    private final AlarmFeedLikeJpaRepository alarmFeedLikeJpaRepository;
    private final AlarmFeedShareJpaRepository alarmFeedShareJpaRepository;
    private final MyBatisAlarmFeedRepository myBatisAlarmFeedRepository;
    private final MyBatisAlarmFeedCommentRepository myBatisAlarmFeedCommentRepository;

    private final AlarmService alarmService;

    private final AzureBlobService azureBlobService;

    @Transactional
    public AlarmFeedDTO saveAlarmFeed(AlarmFeedDTO dto, List<MultipartFile> imageFiles, Long userId) {
        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AlarmFeed alarmFeed = dto.toEntity(user);
        List<AlarmFeedDay> alarmFeedDays = dto.getAlarmFeedDays().stream()
                .map(day -> AlarmFeedDay.builder()
                        .alarmFeed(alarmFeed)
                        .dayOfWeek(day)
                        .build())
                .collect(Collectors.toList());
        alarmFeed.saveAlarmFeedDays(alarmFeedDays);



        alarmFeedJPARepository.save(alarmFeed);
        if(imageFiles != null && !imageFiles.isEmpty()){
            log.info(imageFiles.get(0).getOriginalFilename());
            List<AlarmFeedImage> imageEntities = imageFiles.stream()
                    .map(file -> {
                        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                        String blobPath = String.format("AlarmFeed/%s/%s", alarmFeed.getId(), fileName);
                        String imageUrl = azureBlobService.uploadAlarmFeedImage(file, blobPath);

                        AlarmFeedImage image = AlarmFeedImage.builder()
                                .uniqueFilename(fileName)
                                .blobName(blobPath)
                                .imageUrl(imageUrl)
                                .alarmFeed(alarmFeed) // 양방향 관계 설정
                                .build();

                        return image;
                    }).collect(Collectors.toList());

            alarmFeed.getImages().addAll(imageEntities);
        }

        AlarmFeedDTO savedAlarmFeedDTO = AlarmFeedDTO.fromEntity(alarmFeedJPARepository.save(alarmFeed)); // Cascade로 이미지도 함께 저장
        return savedAlarmFeedDTO;
    }

    @Transactional
    public void deleteAlarmFeed(Long alarmFeedId) {
        AlarmFeed alarmFeed = alarmFeedJPARepository.findByIdWithImages(alarmFeedId).get();


        log.info("AlarmFeed/" + alarmFeedId);
        azureBlobService.deleteFolder("AlarmFeed/" + alarmFeedId);

        alarmFeedJPARepository.delete(alarmFeed);
    }

    public AlarmFeedDTO getAlarmFeedDTO(Long alarmFeedId) {
        AlarmFeed alarmFeed = alarmFeedJPARepository.findByIdWithImages(alarmFeedId).get();

        return AlarmFeedDTO.fromEntity(alarmFeed);
    }

    @Transactional
    public AlarmFeedDTO updateAlarmFeed(AlarmFeedDTO dto, List<MultipartFile> imageFiles, Long userId) {


        return null;
    }

    @Transactional
    public void addAlarmFeedLike(Long alarmFeedId, Long userId) {
        Optional<AlarmFeedLike> alarmFeedLike = alarmFeedLikeJpaRepository.findAlarmFeedLikeByAlarmFeed_IdAndUser_Id(alarmFeedId, userId);

        AlarmFeed alarmFeed = alarmFeedJPARepository.findById(alarmFeedId)
                .orElseThrow(() -> new RuntimeException("AlarmFeed not found"));

        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if(alarmFeedLike.isEmpty()){
            AlarmFeedLike entity = AlarmFeedLike.builder()
                    .alarmFeed(alarmFeed)
                    .user(user)
                    .build();
            alarmFeedLikeJpaRepository.save(entity);
            alarmFeed.addLikeCount();
        } else{
            alarmFeedLikeJpaRepository.delete(alarmFeedLike.get());
            alarmFeed.minusLikeCount();
        }

    }

    public void shareAlarmFeed(Long alarmFeedId, Long userId) {
        // 1. 알람피드 쉐어 엔터티 생성 후 저장
        Optional<AlarmFeedShare> alarmFeedShare = alarmFeedShareJpaRepository
                .findAlarmFeedShareByAlarmFeed_IdAndUser_Id(alarmFeedId, userId);

        AlarmFeed alarmFeed = alarmFeedJPARepository.findById(alarmFeedId)
                .orElseThrow(() -> new RuntimeException("AlarmFeed not found"));

        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if(alarmFeedShare.isEmpty()){
            AlarmFeedShare entity = AlarmFeedShare.builder()
                    .alarmFeed(alarmFeed)
                    .user(user)
                    .build();
            alarmFeedShareJpaRepository.save(entity);
            alarmFeed.addShareCount();
        } else{
            alarmFeedShareJpaRepository.delete(alarmFeedShare.get());
            alarmFeed.minusShareCount();
        }

        // 2. 공유한 사용자에게 공유한 알람피드의 정보를 바탕으로 알람저장
        AlarmDTO temp = AlarmDTO.builder()
                .title(alarmFeed.getTitle())
                .description(alarmFeed.getDescription())
                .category("공부")
                .time(alarmFeed.getTime())
                .timeInterval(alarmFeed.getTimeInterval())
                .repeatCount(alarmFeed.getRepeatCount())
                .alarmDays(alarmFeed.extractDayOfWeeks())
                .build();
        alarmService.saveAlarm(temp, userId);

        return;
    }

    public List<AlarmFeedDTO> getAlarmFeedsByDayOfWeek(List<Integer> dayOfWeek) {
        List<AlarmFeedDTO> alarmFeeds = myBatisAlarmFeedRepository.findAlarmFeedsByDayOfWeek(dayOfWeek);

        for (AlarmFeedDTO dto : alarmFeeds){
            AlarmFeedCommentDTO commentDTO = myBatisAlarmFeedCommentRepository.findAlarmFeedCommentByAlarmFeedId(dto.getId());
            if(commentDTO != null)
                dto.setAlarmFeedCommentDTO(commentDTO);
        }

        return alarmFeeds.stream()
                .collect(Collectors.toList());
    }


    public List<AlarmFeedDTO> getAlarmFeedsByDayOfWeekAndUserId(List<Integer> dayOfWeek, Long userId) {
        List<AlarmFeedDTO> alarmFeeds = myBatisAlarmFeedRepository.findAlarmFeedsByDayOfWeekAndUserId(dayOfWeek, userId);

        return alarmFeeds.stream()
                .collect(Collectors.toList());
    }
}
