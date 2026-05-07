package daj.product.mockBeans;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.out.IProductWriterOutputPort;

@Profile("test")
@Component
public class ProductOutputPortMock implements IProductWriterOutputPort {

  @Override
  public ProductDto save(ProductDto input) {
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public ProductDto delete(Integer toDel) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public ProductDto update(Integer id, ProductDto newInfo) {
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  
  
}
