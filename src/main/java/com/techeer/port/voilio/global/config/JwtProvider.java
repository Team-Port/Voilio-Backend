package com.techeer.port.voilio.global.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.xml.bind.DatatypeConverter;
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
  private final Key key;

  public JwtProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
    this.key = Keys.hmacShaKeyFor(secretByteKey);
  }

  public JwtToken generateToken(Authentication authentication) {
    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    // Access Token 생성
    String accessToken =
        Jwts.builder()
            .setSubject(authorities)
            .claim("auth", authorities)
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

    // Refresh Token 생성
    String refreshToken =
        Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 36))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

    return JwtToken.builder()
        .grantType("Bearer")
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public Authentication getAuthentication(String accessToken) {
    // 토큰 복호화
    Claims claims = parseClaime(accessToken);
    if (claims.get("auth") == null) {
      throw new RuntimeException("권한 정보가 없는 토큰입니다.");
    }
    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get("auth").toString().split(","))
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
      log.info("Invalid JWT Token", e);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT Token", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT claims string is empty", e);
    }
    return false;
  }

  private Claims parseClaime(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
