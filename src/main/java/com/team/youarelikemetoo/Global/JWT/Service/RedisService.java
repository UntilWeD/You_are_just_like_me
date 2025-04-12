package com.team.youarelikemetoo.Global.JWT.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String PREFIX = "refresh_token:";
    private final String BLACKLIST_PREFIX = "blacklist:";

    // 리프레시 저장
    public void saveRefreshToken(String oauthId, String refrehToken, long expirationMs){
        String key = PREFIX + oauthId;

        redisTemplate.opsForValue().set(key, refrehToken, expirationMs, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(String oauthId) {
        String key = PREFIX + oauthId;
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(String oauthId){
        String key = PREFIX + oauthId;
        redisTemplate.delete(key);
    }

    public void blacklistToken(String token, long expirationMillis){
        redisTemplate.opsForValue().set(
                BLACKLIST_PREFIX + token,
                "logout",
                expirationMillis,
                TimeUnit.MILLISECONDS
        );
    }

    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }

    public boolean hasRefreshToken(String oauthId){
        return redisTemplate.hasKey(PREFIX + oauthId);
    }


}
