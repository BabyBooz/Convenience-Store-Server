package demo.shop.Entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
  private List<T> content; // danh sách phần tử trong trang
  private int page; // trang hiện tại
  private int size; // kích thước trang
  private long totalElements; // tổng số phần tử
  private int totalPages; // tổng số trang
  private boolean last; // trang cuối cùng
}
