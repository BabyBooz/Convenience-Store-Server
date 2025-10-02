package demo.shop.Service;

import demo.shop.DTO.Request.CategoryRequest;
import demo.shop.DTO.Response.CategoryResponse;
import demo.shop.Entity.Category;
import demo.shop.Exception.AppException;
import demo.shop.Exception.EnumCode;
import demo.shop.Mapper.CategoryMapper;
import demo.shop.Repository.CategoryRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
  CategoryRepository categoryRepository;
  CategoryMapper categoryMapper;

  @Transactional
  public CategoryResponse createCategory(CategoryRequest request) {
    var category = categoryMapper.toCategory(request);
    category.setIsdeleted(0);
    categoryRepository.save(category);
    return categoryMapper.toCategoryResponse(category);
  }

  @Transactional
  public void updateDeleteCategory(int id) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(EnumCode.CATEGORY_NOT_EXISTED));
    category.setIsdeleted(1);
    categoryRepository.save(category);
  }

  public List<CategoryResponse> getAllCategory() {
    var categories = categoryRepository.findAll();
    return categoryMapper.toCategoryResponses(categories);
  }

}
