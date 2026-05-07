package daj.product.visible.port.out;


import daj.common.types.AppPage;
import daj.product.visible.port.dto.ProductDto;

public interface IProductReaderOutputPort {

  ProductDto findDetailsById(Integer input);

  AppPage<ProductDto> findByPage(int page, int size);

  

}
