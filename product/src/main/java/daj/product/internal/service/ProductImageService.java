package daj.product.internal.service;

import org.springframework.stereotype.Service;

import daj.product.visible.port.dto.ProductImageDto;
import daj.product.visible.port.in.IProductImageInputPort;
import daj.product.visible.port.out.IProductImageOutputPort;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageInputPort {

  private final IProductImageOutputPort imageOP;

  @Override
  public ProductImageDto saveImage(ProductImageDto imageEntity) {
    final ProductImageDto saved = imageOP.saveImage(imageEntity);
    return saved;
  }
  

  @Override
  public ProductImageDto findImageById(Integer id) {
    final ProductImageDto found = imageOP.findImageById(id);
    return found;
  }

  public ProductImageDto delete(Integer id) {
    final ProductImageDto deleted = imageOP.delete(id);
    return deleted;
  }


}
