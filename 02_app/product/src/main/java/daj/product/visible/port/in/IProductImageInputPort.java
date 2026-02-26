package daj.product.visible.port.in;

import daj.product.visible.port.dto.ProductImageDto;

public interface IProductImageInputPort {

  ProductImageDto saveImage(ProductImageDto imageEntity);

  ProductImageDto findImageById(Integer id);

  ProductImageDto delete(Integer id);

}
