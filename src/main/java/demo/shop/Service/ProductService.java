package demo.shop.Service;


import demo.shop.DTO.Request.ProductRequest;
import demo.shop.DTO.Response.ProductResponse;
import demo.shop.Entity.PageResponse;
import demo.shop.Entity.Product;
import demo.shop.Exception.AppException;
import demo.shop.Exception.EnumCode;
import demo.shop.Mapper.ProductMapper;
import demo.shop.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

  ProductRepository productRepository;
  ProductMapper productMapper;

  public PageResponse<ProductResponse> getAllProduct(int page, int size,  Integer categoryId) {
    Page<Product> products;
    if(categoryId == null){
      products = productRepository.findAll(PageRequest.of(page,size));
    }else{
      products = productRepository.findByCategoryId(categoryId, PageRequest.of(page,size));
    }

    Page<ProductResponse> productResponses = products.map(productMapper::toProductResponse);

    return new PageResponse<>(
        productResponses.getContent(),
        productResponses.getNumber(),
        productResponses.getSize(),
        productResponses.getTotalElements(),
        productResponses.getTotalPages(),
        productResponses.isLast()
    );
  }

  public ProductResponse getProductById(int id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new AppException(EnumCode.PRODUCT_NOT_EXISTED));
    return productMapper.toProductResponse(product);
  }

  @Transactional
  public ProductResponse createProduct(ProductRequest request) {
    Product product = productMapper.toProduct(request);
    Product savedProduct = productRepository.save(product);
    return productMapper.toProductResponse(savedProduct);
  }

  @Transactional
  public void deleteProduct(int id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new AppException(EnumCode.PRODUCT_NOT_EXISTED));
    productRepository.delete(product);
  }

  @Transactional
  public void updateProduct(int id, ProductRequest request) {
    Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new AppException(EnumCode.PRODUCT_NOT_EXISTED));
    productMapper.updateProductFromDto(request, existingProduct);
    productRepository.save(existingProduct);
  }
}

