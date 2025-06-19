package daj.product.visible.port.out;

import daj.product.visible.port.dto.ProductImageDto;

public interface IProductImageOutputPort {

  ProductImageDto saveImage(ProductImageDto imageEntity);

  ProductImageDto findImageById(Integer id);

  ProductImageDto delete(Integer id);

  
}
