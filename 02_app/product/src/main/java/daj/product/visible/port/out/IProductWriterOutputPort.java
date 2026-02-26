package daj.product.visible.port.out;

import daj.product.visible.port.dto.ProductDto;

public interface IProductWriterOutputPort {
  
  public ProductDto save(ProductDto input);

  public ProductDto delete(Integer toDel);

  public ProductDto update(Integer id, ProductDto newInfo);

}
