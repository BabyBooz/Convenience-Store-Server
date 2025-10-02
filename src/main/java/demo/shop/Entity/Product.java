package demo.shop.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false)
  private Integer id;

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

  @CreationTimestamp
  @Column(name = "created_at")
  private Instant createdAt;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}