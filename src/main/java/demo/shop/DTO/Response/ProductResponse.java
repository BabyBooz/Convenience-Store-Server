package demo.shop.DTO.Response;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductResponse {
  @Size(max = 100)
  @NotNull
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Lob
  @Column(name = "description")
  private String description;

  @NotNull
  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @NotNull
  @Column(name = "stock", nullable = false)
  private Integer stock;

  @Size(max = 255)
  @Column(name = "image_url")
  private String imageUrl;

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "category_id")
  private Integer categoryId;
}
