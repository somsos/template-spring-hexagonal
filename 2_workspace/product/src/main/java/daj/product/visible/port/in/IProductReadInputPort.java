package daj.product.visible.port.in;


import daj.common.types.AppPage;
import daj.product.visible.port.dto.ProductDto;

public interface IProductReadInputPort {
  
  ProductDto findDetailsById(Integer id);

  AppPage<ProductDto> findByPage(int page, int size);

}
