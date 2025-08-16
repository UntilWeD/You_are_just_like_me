package com.team.youarelikemetoo.user.service;

import com.team.youarelikemetoo.alarmFeed.service.AzureBlobService;
import com.team.youarelikemetoo.global.util.ApiResponse;
import com.team.youarelikemetoo.user.dto.UserDTO;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.entity.UserProfileImage;
import com.team.youarelikemetoo.user.repository.UserJPARepository;
import com.team.youarelikemetoo.user.repository.UserProfileImageJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserJPARepository userJPARepository;
    private final UserProfileImageJpaRepository userProfileImageJpaRepository;
    private final AzureBlobService azureBlobService;


    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserInfo(String oauthId){
        log.info(oauthId);
        UserEntity user = userJPARepository.findByOauthId(oauthId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));


        return ResponseEntity.ok(ApiResponse.success(UserDTO.fromEntity(user)));
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO, MultipartFile imageFile, String oauthId){
        // 1. 기본 유저 데이터 수정
        UserEntity user = userJPARepository.findByOauthId(oauthId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        user.changeUserInfo(userDTO);

        UserDTO dto = UserDTO.fromEntity(user);

        // 1-1. 추가하려는 이미지가 있고 기존 이미지가 있으면 삭제
        UserProfileImage existing = user.getUserProfileImage();
        if(!imageFile.isEmpty() && existing != null){
            String folderPath = "UserProfile/" + user.getId();
            azureBlobService.deleteFolder(folderPath);
            userProfileImageJpaRepository.delete(existing);
            user.setUserProfileImage(null);
        }

        // 2. 유저 프로필 사진 존재시 추가
        if(imageFile != null && !imageFile.isEmpty()){
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String blobPath = String.format("UserProfile/%s/%s", user.getId(), fileName);
            String imageUrl = azureBlobService.uploadAlarmFeedImage(imageFile, blobPath);

            UserProfileImage profileImage = UserProfileImage.builder()
                .uniqueFilename(fileName)
                .blobName(blobPath)
                .imageUrl(imageUrl)
                .user(user)
                .build();

            userProfileImageJpaRepository.save(profileImage);
            dto.setProfileImageUrl(imageUrl);
        }

        return dto;
    }

    @Transactional
    public void deleteUser(Long userId) {
        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        userJPARepository.delete(user);

        if(user.getUserProfileImage() != null){
            String folderPath = "UserProfile/" + user.getId();
            azureBlobService.deleteFolder(folderPath);
        }
        return;
    }
}
