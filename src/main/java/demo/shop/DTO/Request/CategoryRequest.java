package demo.shop.DTO.Request;

import demo.shop.Entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {

  @NotNull(message = "Category name is required")
  @NotBlank(message = "Category name cannot be blank") // dam bao name khong rong
  @Size(max = 100, message = "Category name must not exceed 100 characters")
  String name;

  @Size(max = 255, message = "Description must not exceed 255 characters")
  String description;

  @ColumnDefault("0")
  @Column(name = "isdeleted")
  Integer isdeleted;
}
