package com.techeer.port.voilio.global.config.security.jwtCheck;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/*
  WebSecurityConfig 에서의 사용을 위해
 */

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    // 필요한 권한이 없이 접근하려 할때 403
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
  }
}
