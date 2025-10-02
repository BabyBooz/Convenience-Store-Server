package demo.shop.Mapper;

import demo.shop.DTO.Request.CategoryRequest;
import demo.shop.DTO.Response.CategoryResponse;
import demo.shop.Entity.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  @Mapping(target = "isdeleted", ignore = true)
  @Mapping(target = "products", ignore = true)
  Category toCategory(CategoryRequest request);

  CategoryResponse toCategoryResponse(Category category);

  List<CategoryResponse> toCategoryResponses(List<Category> categories);
}
