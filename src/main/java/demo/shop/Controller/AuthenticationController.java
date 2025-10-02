package demo.shop.Controller;

import com.nimbusds.jose.JOSEException;
import demo.shop.DTO.Request.AuthenticationRequest;
import demo.shop.DTO.Request.IntrospectRequest;
import demo.shop.DTO.Request.LogoutRequest;
import demo.shop.DTO.Request.RefreshTokenRequest;
import demo.shop.DTO.Request.UserRequest;
import demo.shop.DTO.Response.ApiResponse;
import demo.shop.DTO.Response.AuthenticationResponse;
import demo.shop.DTO.Response.IntrospectResponse;
import demo.shop.DTO.Response.LogoutResponse;
import demo.shop.DTO.Response.UserResponse;
import demo.shop.Service.AuthenticationService;
import demo.shop.Service.UserService;
import java.text.ParseException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {
  AuthenticationService authenticationService;

  @PostMapping("/login")
  public ApiResponse<AuthenticationResponse> authenticationUser(@RequestBody AuthenticationRequest request) {
    return ApiResponse.<AuthenticationResponse>builder()
        .message("Authentication successfully")
        .result(authenticationService.authenticationUser(request))
        .build();
  }

  @PostMapping("/introspect")
  public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
      throws ParseException, JOSEException {
    return ApiResponse.<IntrospectResponse>builder()
        .message("Introspect successfully")
        .result(authenticationService.introspectResponse(request))
        .build();
  }

  @PostMapping("/logout")
  public ApiResponse<LogoutResponse> logout(@RequestBody LogoutRequest request)
      throws ParseException, JOSEException {
    authenticationService.logoutToken(request);
    return ApiResponse.<LogoutResponse>builder()
        .message("Logout successfully")
        .build();
  }

  @PostMapping("/refreshToken")
  public ApiResponse<AuthenticationResponse> logout(@RequestBody RefreshTokenRequest request)
      throws ParseException, JOSEException {
    return ApiResponse.<AuthenticationResponse>builder()
        .message("Refresh successfully")
        .result(authenticationService.refreshToken(request))
        .build();
  }
}
