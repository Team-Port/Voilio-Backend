package com.techeer.port.voilio.domain.user.service;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

  private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

  private final RedisTemplate<String, String> redisTemplate;

  public RefreshTokenService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void saveRefreshToken(String username, String refreshToken) {
    redisTemplate
        .opsForValue()
        .set(REFRESH_TOKEN_PREFIX + username, refreshToken, 7, TimeUnit.DAYS);
  }

  public void deleteRefreshToken(String username) {
    redisTemplate.delete(REFRESH_TOKEN_PREFIX + username);
  }

  public String findRefreshToken(String username) {
    return redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + username);
  }
}
