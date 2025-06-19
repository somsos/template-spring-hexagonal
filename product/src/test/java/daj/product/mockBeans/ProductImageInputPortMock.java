package daj.product.mockBeans;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import daj.product.visible.port.dto.ProductImageDto;
import daj.product.visible.port.out.IProductImageOutputPort;

@Profile("test")
@Component
public class ProductImageInputPortMock implements IProductImageOutputPort {

  @Override
  public ProductImageDto saveImage(ProductImageDto imageEntity) {
    throw new UnsupportedOperationException("Unimplemented method 'saveImage'");
  }

  @Override
  public ProductImageDto findImageById(Integer id) {
    throw new UnsupportedOperationException("Unimplemented method 'findImageByName'");
  }

  @Override
  public ProductImageDto delete(Integer id) {
    throw new UnsupportedOperationException("Unimplemented method 'findImageByName'");
  }
  
}
