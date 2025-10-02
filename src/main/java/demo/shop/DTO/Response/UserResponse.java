package demo.shop.DTO.Response;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {
  @NotNull
  @Column(name = "username", nullable = false, length = 50)
  @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
  private String username;

  @Size(max = 255)
  @NotNull
  @Column(name = "password", nullable = false)
  private String password;

  @Size(max = 100)
  @Column(name = "email", length = 100)
  private String email;

  @Size(max = 100)
  @Column(name = "full_name", length = 100)
  private String fullName;

  @ElementCollection
  @CollectionTable(name ="user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "role")
  private Set<String> roles;
}
