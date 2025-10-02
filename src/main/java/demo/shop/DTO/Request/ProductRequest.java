package demo.shop.DTO.Request;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
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
public class ProductRequest {
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

  @ColumnDefault("0")
  @Column(name = "isdeleted")
  private Integer isdeleted;

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at")
  private Instant createdAt;

  @NotNull(message = "Category ID cannot be null")
  @Column(name = "category_id", nullable = false)
  private Integer categoryId;

}
