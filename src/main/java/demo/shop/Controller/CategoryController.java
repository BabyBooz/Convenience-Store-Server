package demo.shop.Controller;

import demo.shop.DTO.Request.CategoryRequest;
import demo.shop.DTO.Response.ApiResponse;
import demo.shop.DTO.Response.CategoryResponse;
import demo.shop.Service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryController {
  CategoryService categoryService;

  @PostMapping("/createCategory")
  public ApiResponse<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
    return ApiResponse.<CategoryResponse>builder()
        .code(1000)
        .message("Category created successfully")
        .result(categoryService.createCategory(request))
        .build();
  }

  @PutMapping("/deleteCategory/{id}")
  public ApiResponse<Void> deleteCategory(@PathVariable int id) {
    categoryService.updateDeleteCategory(id);
    return ApiResponse.<Void>builder()
        .code(1000)
        .message("Category deleted successfully")
        .build();
  }

  @GetMapping("/getAllCategory")
  public ApiResponse<List<CategoryResponse>> getAllCategory() {
    List<CategoryResponse> responses = categoryService.getAllCategory();
    return ApiResponse.<List<CategoryResponse>>builder()
        .code(1000)
        .message("Get all categories successfully")
        .result(responses)
        .build();
  }
}
