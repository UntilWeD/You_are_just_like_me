package com.team.youarelikemetoo.alarmFeed.service;

import com.team.youarelikemetoo.alarm.repository.AlarmJPARepository;
import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeedImage;
import com.team.youarelikemetoo.alarmFeed.repository.AlarmFeedJPARepository;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmFeedService {

    private final AlarmJPARepository alarmJPARepository;
    private final UserJPARepository userJPARepository;
    private final AlarmFeedJPARepository alarmFeedJPARepository;
    private final AzureBlobService azureBlobService;

    @Transactional
    public AlarmFeedDTO saveAlarmFeed(AlarmFeedDTO dto, List<MultipartFile> imageFiles, Long userId) {
        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AlarmFeed alarmFeed =  alarmFeedJPARepository.save(dto.toEntity(user));

        if(imageFiles != null && !imageFiles.isEmpty()){
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

    public AlarmFeedDTO updateAlarmFeed(AlarmFeedDTO dto, List<MultipartFile> imageFiles, Long userId) {

        return null;
    }
}
