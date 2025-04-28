package com.team.youarelikemetoo.user.service;

import com.team.youarelikemetoo.user.dto.UserDTO;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.repository.UserRepository;
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

    private final UserRepository userRepository;

    public ResponseEntity<?> getUserInfo(String oauthId){
        Optional<UserEntity> optionalUser = userRepository.findByOauthId(oauthId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 유저입니다.");
        }

        UserEntity user = optionalUser.get();
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }

    public ResponseEntity<?> saveUser(UserDTO userDTO, String oauthId){
        log.info("oauthId : {}", oauthId);

        Optional<UserEntity> user = userRepository.findByOauthId(oauthId);
        user.get().changeUserInfo(userDTO);

        return ResponseEntity.status(HttpStatus.OK).body("아 킥킥");
    }

}
