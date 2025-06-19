package daj.product.visible.port.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {

  private Integer id;

  private String name;

  private String type;

  private byte[] image;

  private ProductDto product;
  
}
