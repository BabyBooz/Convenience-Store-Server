package demo.shop.Service;

import demo.shop.DTO.Request.UserRequest;
import demo.shop.DTO.Response.ApiResponse;
import demo.shop.DTO.Response.UserResponse;
import demo.shop.Entity.User;
import demo.shop.Enums.Role;
import demo.shop.Exception.AppException;
import demo.shop.Exception.EnumCode;
import demo.shop.Mapper.UserMapper;
import demo.shop.Repository.UserRepository;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.HashSet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

  UserRepository userRepository;
  UserMapper userMapper;

  @Transactional
  public UserResponse registerUser(UserRequest request) {
    boolean user = userRepository.existsByUsername(request.getUsername());
    if (user) {
      throw new AppException(EnumCode.USER_EXISTED);
    }

    // Kiểm tra email (thêm method này vào UserRepository)
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new AppException(EnumCode.EMAIL_ALREADY_EXISTS);
    }
    PasswordEncoder encoder = new BCryptPasswordEncoder(10);

    HashSet<String> roles = new HashSet<>();
    roles.add(Role.USER.name());

    User newUser = User.builder()
        .username(request.getUsername())
        .password(encoder.encode(request.getPassword()))
        .email(request.getEmail())
        .fullName(request.getFullName())
        .isdeleted(0)
        .createdAt(Instant.now())
        .roles(roles)
        .build();

    try {
      User savedUser = userRepository.save(newUser);
      return userMapper.toUserResponse(savedUser);
    } catch (DataIntegrityViolationException ex) {
      throw new AppException(EnumCode.USER_EXISTED);
    }
  }

  public UserResponse getUserByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new AppException(EnumCode.USER_NOT_EXISTED));
    return userMapper.toUserResponse(user);
  }

  public void updateDeleteUser(Integer id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new AppException(EnumCode.USER_NOT_EXISTED));
    user.setIsdeleted(1);
    userRepository.save(user);
  }
}
