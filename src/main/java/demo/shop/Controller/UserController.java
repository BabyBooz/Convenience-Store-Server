package demo.shop.Controller;

import demo.shop.DTO.Request.UserRequest;
import demo.shop.DTO.Response.ApiResponse;
import demo.shop.DTO.Response.UserResponse;
import demo.shop.Service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
  UserService userService;

  @PostMapping("/register")
  public ApiResponse<UserResponse> registerUser(@Valid @RequestBody UserRequest request) {
    return ApiResponse.<UserResponse>builder()
        .code(1000)
        .message("User registered successfully")
        .result(userService.registerUser(request))
        .build();
  }

  @GetMapping("/{name}")
  public ApiResponse<UserResponse> getUserById(@PathVariable String name) {
    return ApiResponse.<UserResponse>builder()
        .code(1000)
        .message("User fetched successfully")
        .result(userService.getUserByUsername(name))
        .build();
  }

  @PostMapping("/{id}")
  public ApiResponse<Void> deleteUser(@PathVariable Integer id) {
    userService.updateDeleteUser(id);
    return ApiResponse.<Void>builder()
        .code(1000)
        .message("User deleted successfully")
        .build();
  }
}
