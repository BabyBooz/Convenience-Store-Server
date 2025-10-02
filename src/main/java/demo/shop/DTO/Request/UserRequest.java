package demo.shop.DTO.Request;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
  @NotNull
  @Column(name = "username", nullable = false, length = 50)
  @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
  private String username;

  @Size(max = 255)
  @NotNull
  @Column(name = "password", nullable = false)
  private String password;

  @Email(message = "Email invalid")
  @Size(max = 100)
  @Column(name = "email", length = 100)
  private String email;

  @Size(max = 100)
  @Column(name = "full_name", length = 100)
  private String fullName;
}
