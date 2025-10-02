package demo.shop.Mapper;

import demo.shop.DTO.Response.UserResponse;
import demo.shop.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponse toUserResponse(User user);

  User toUser(User request);
}
