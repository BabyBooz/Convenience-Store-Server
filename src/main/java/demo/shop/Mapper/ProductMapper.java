package demo.shop.Mapper;

import demo.shop.DTO.Request.ProductRequest;
import demo.shop.DTO.Response.ProductResponse;
import demo.shop.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  Product toProduct(ProductRequest request);
  // phai co mapping vi ten khac nhau
  // category.id la trong entity product
  // categoryId la trong DTO ProductResponse
  // neu trung ten thi khong can mapping
  // neu khong co mapping se bi loi
  @Mapping(source = "category.id", target = "categoryId")
  ProductResponse toProductResponse(Product product);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  void updateProductFromDto(ProductRequest request,@MappingTarget Product product);
}
