package demo.shop.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.shop.DTO.Response.ApiResponse;
import demo.shop.Exception.EnumCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper = new ObjectMapper();


  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code(EnumCode.UNAUTHENTICATED.getStatusCode().value())
        .message("Token Expired or Invalid2")
        .build();

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    objectMapper.writeValue(response.getOutputStream(), apiResponse);
  }
}
