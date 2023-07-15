package com.techeer.port.voilio.global.config.security;

import com.techeer.port.voilio.domain.user.service.RefreshTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_TYPE = "bearer";
  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 토큰 유효시간 30분
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 7; // Refresh token 유효시간 7일
  private final Key key;

  private RefreshTokenService refreshTokenService;

  public JwtProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.refreshTokenService = refreshTokenService;
  }

  // 인증 객체를 받아와서 토큰 생성
  public TokenDto generateTokenDto(Authentication authentication) {
    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME); // 현재 시간으로부터 유효 시간을 더해 만료 시간 상정
    Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

    String accessToken =
        Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .setExpiration(tokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

    String refreshToken = Jwts.builder()
            .setSubject(authentication.getName())
            .setExpiration(refreshTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

    refreshTokenService.saveRefreshToken(authentication.getName(), refreshToken);

    return TokenDto.builder()
        .grantType(BEARER_TYPE)
        .accessToken(accessToken)
        .tokenExpiresIn(tokenExpiresIn.getTime())
        .build();
  }

  public TokenDto refreshToken(String refreshToken) {
    if (validateToken(refreshToken)) {
      // refreshToken이 유효하다면 새로운 accessToken을 생성
      Authentication authentication = getAuthentication(refreshToken);
      return generateTokenDto(authentication);
    } else {
      // refreshToken이 유효하지 않다면 refreshToken을 제거
      String username = getUsername(refreshToken);
      refreshTokenService.deleteRefreshToken(username);
      throw new RuntimeException("Refresh token is not valid.");
    }
  }

  public String getUsername(String refreshToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken).getBody().getSubject();
    } catch (ExpiredJwtException e) {
      return e.getClaims().getSubject();
    }
  }

  // JWT 토큰에서 인증 정보 조회
  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);

    if (claims.get(AUTHORITIES_KEY) == null) {
      throw new RuntimeException("권한 정보가 없는 토큰입니다.");
    }

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    UserDetails principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
