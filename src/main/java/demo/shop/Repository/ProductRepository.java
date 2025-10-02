package demo.shop.Repository;

import demo.shop.Entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
   Product findByName(String name);
   Optional<Product> getProductById(int productId);

   Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);

}