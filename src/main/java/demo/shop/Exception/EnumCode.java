package demo.shop.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EnumCode {
  USER_FIRSTNAME(1002,"First Name at least 3 characters!", HttpStatus.BAD_REQUEST),
  USER_EXISTED(1001,"User existed",HttpStatus.FOUND),
  USER_NOT_EXISTED(1003,"User not existed",HttpStatus.NOT_FOUND),
  PRODUCT_NOT_EXISTED(1003,"Product not existed",HttpStatus.NOT_FOUND),
  UNAUTHENTICATED(1004,"Unauthenticated",HttpStatus.UNAUTHORIZED),
  UNAUTHORIZED(1005,"You do not have permission",HttpStatus.FORBIDDEN),
  USER_EMAIL_EXISTED(1006,"Email user existed",HttpStatus.FOUND),
  WRONG_PASSWORD(1007,"Wrong Password please input again!",HttpStatus.UNAUTHORIZED),
  EMAIL_ALREADY_EXISTS(1008, "Email already exists", HttpStatus.CONFLICT),
  INVALID_AUTHENTICATION(1009, "Invalid username or password", HttpStatus.UNAUTHORIZED),
  INVALID_TOKEN(1010, "Invalid token", HttpStatus.UNAUTHORIZED),
  LOGOUT(1011, "You have been logged out", HttpStatus.UNAUTHORIZED),
  OutOfToken(1011, "Token is out of date", HttpStatus.UNAUTHORIZED),
  CATEGORY_NOT_EXISTED(1012, "Category not existed", HttpStatus.NOT_FOUND),
  ;



  private int code;
  private String message;
  private HttpStatusCode statusCode;
}
