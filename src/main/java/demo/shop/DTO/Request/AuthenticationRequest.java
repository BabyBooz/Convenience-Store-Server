package demo.shop.DTO.Request;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
  @Size(max = 50)
  @NotNull
  @Column(name = "username", nullable = false, length = 50)
  private String username;

  @Size(max = 255)
  @NotNull
  @Column(name = "password", nullable = false)
  private String password;
}
