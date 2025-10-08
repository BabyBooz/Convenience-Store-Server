package demo.shop.Controller;

import demo.shop.DTO.Request.ProductRequest;
import demo.shop.DTO.Response.ApiResponse;
import demo.shop.DTO.Response.ProductResponse;
import demo.shop.Entity.PageResponse;
import demo.shop.Mapper.ProductMapper;
import demo.shop.Service.ProductService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductController {

  private static final Logger log = LoggerFactory.getLogger(ProductController.class);
  ProductService productService;
  ProductMapper productMapper;

  @GetMapping("/{id}")
  public ApiResponse<ProductResponse> getProductById(@PathVariable int id){
    ProductResponse response = productService.getProductById(id);
    return ApiResponse.<ProductResponse>builder()
        .code(1000)
        .message("Product fetched successfully")
        .result(response)
        .build();
  }

  @GetMapping("/getProducts")
  public ApiResponse<PageResponse<ProductResponse>> getAllProducts(@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "6") int size, @RequestParam(required = false) Integer categoryId){
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("Authenticated user: {}", authentication.getName());
    authentication.getAuthorities().forEach(authority -> log.info("Authority: {}", authority.getAuthority()));

    PageResponse<ProductResponse> responses = productService.getAllProduct(page, size, categoryId);
    return ApiResponse.<PageResponse<ProductResponse>>builder()
        .code(1000)
        .message("Products fetched successfully")
        .result(responses)
        .build();
  }

  @PostMapping("/create")
  public ApiResponse<ProductResponse> createProduct(@RequestBody  ProductRequest request){
    ProductResponse response = productService.createProduct(request);
    return ApiResponse.<ProductResponse>builder()
        .code(1000)
        .message("Product created successfully")
        .result(response)
        .build();
  }

  @DeleteMapping("/delete/{id}")
  public ApiResponse<Void> deleteProduct(@PathVariable int id){
    productService.deleteProduct(id);
    return ApiResponse.<Void>builder()
        .code(1000)
        .message("Product deleted successfully")
        .build();
  }

  @PostMapping("/update/{id}")
  public ApiResponse<Void> updateProduct(@PathVariable int id, ProductRequest request){
    productService.updateProduct(id, request);
    return ApiResponse.<Void>builder()
        .code(1000)
        .message("Product updated successfully")
        .build();
  }
}
