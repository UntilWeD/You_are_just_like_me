package com.team.youarelikemetoo.user.service;

import com.team.youarelikemetoo.global.util.ApiResponse;
import com.team.youarelikemetoo.user.dto.UserDTO;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserJPARepository userJPARepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserInfo(String oauthId){
        UserEntity user = userJPARepository.findByOauthId(oauthId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));


        return ResponseEntity.ok(ApiResponse.success(UserDTO.fromEntity(user)));
    }

    public ResponseEntity<?> saveUser(UserDTO userDTO, String oauthId){
        log.info("oauthId : {}", oauthId);

        Optional<UserEntity> user = userJPARepository.findByOauthId(oauthId);
        user.get().changeUserInfo(userDTO);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
