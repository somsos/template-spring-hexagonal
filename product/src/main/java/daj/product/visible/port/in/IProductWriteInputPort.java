package daj.product.visible.port.in;

import daj.product.visible.port.dto.ProductDto;

public interface IProductWriteInputPort {

  ProductDto save(ProductDto input);

  ProductDto delete(Integer id);

  ProductDto update(Integer id, ProductDto newInfo);

}
