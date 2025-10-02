package demo.shop.Exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException {
  private EnumCode errorCode;

  public AppException(EnumCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

}
