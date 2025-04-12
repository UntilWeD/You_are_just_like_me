package com.team.youarelikemetoo.User.Service;

import com.team.youarelikemetoo.User.DTO.UserDTO;
import com.team.youarelikemetoo.User.Entity.UserEntity;
import com.team.youarelikemetoo.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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

}
