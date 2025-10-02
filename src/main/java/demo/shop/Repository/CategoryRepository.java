package demo.shop.Repository;

import demo.shop.Entity.Category;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  @Query("UPDATE Category c SET c.isdeleted = 1 WHERE c.id = :id")
  @Modifying //bao cho spring jpa biet day la query thay doi du lieu
  void updateDeleteById(@Param("id") Integer id);

  @Query("SELECT c.name FROM Category c ")
  List<Category>  getAllCategories();

}