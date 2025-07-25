package com.team.youarelikemetoo.alarmFeed.service;

import com.team.youarelikemetoo.alarm.dto.AlarmDTO;
import com.team.youarelikemetoo.alarm.repository.AlarmJPARepository;
import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeedImage;
import com.team.youarelikemetoo.alarmFeed.repository.AlarmFeedJPARepository;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmFeedService {

    private final AlarmJPARepository alarmJPARepository;
    private final UserJPARepository userJPARepository;
    private final AlarmFeedJPARepository alarmFeedJPARepository;
    private final AzureBlobUploader azureBlobUploader;

    @Transactional
    public AlarmFeedDTO saveAlarmFeed(AlarmFeedDTO dto, List<MultipartFile> imageFiles, Long userId) {
        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AlarmFeed alarmFeed = dto.toEntity(user);

        if(imageFiles != null && !imageFiles.isEmpty()){
            List<AlarmFeedImage> imageEntities = imageFiles.stream()
                    .map(file -> {
                        String imageUrl = azureBlobUploader.upload(file);
                        AlarmFeedImage image = AlarmFeedImage.builder()
                                .originalFilename(file.getOriginalFilename())
                                .imagePath(imageUrl)
                                .alarmFeed(alarmFeed) // 양방향 관계 설정
                                .build();
                        return image;
                    }).collect(Collectors.toList());

            alarmFeed.getImages().addAll(imageEntities);
        }

        AlarmFeedDTO savedAlarmFeedDTO = AlarmFeedDTO.fromEntity(alarmFeedJPARepository.save(alarmFeed)); // Cascade로 이미지도 함께 저장
        return savedAlarmFeedDTO;
    }

}
