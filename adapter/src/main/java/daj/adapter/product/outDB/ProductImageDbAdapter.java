package daj.adapter.product.outDB;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import daj.adapter.product.outDB.entity.ProductEntity;
import daj.adapter.product.outDB.entity.ProductImageEntity;
import daj.adapter.product.outDB.repository.ImageProductRepository;
import daj.adapter.product.utils.ProductImageMapper;
import daj.adapter.product.utils.ProductMapper;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.dto.ProductImageDto;
import daj.product.visible.port.out.IProductImageOutputPort;


@Component
@RequiredArgsConstructor
public class ProductImageDbAdapter implements IProductImageOutputPort {

  private final ImageProductRepository imageRepo;
  
  private final ProductImageMapper imageMapper;

  private final ProductReaderDbAdapter reader;

  private final ProductMapper mapper;

  @Override
  public ProductImageDto saveImage(ProductImageDto rProductImage) {
    //Check if exists
    final ProductEntity productInDB = reader.findByIdOrThrow(rProductImage.getProduct().getId());

    // save
    final ProductDto pm = mapper.entityToModel(productInDB);
    rProductImage.setProduct(pm);

    final ProductImageEntity toSave = imageMapper.modelToEntity(rProductImage);
    final ProductImageEntity saved = imageRepo.save(toSave);
    final ProductImageDto output = imageMapper.entityToModel(saved);
    return output;
  }

  @Override
  public ProductImageDto findImageById(Integer id) {
    final ProductImageEntity found = imageRepo.findById(id).orElse(null);
    final ProductImageDto output = imageMapper.entityToModel(found);
    return output;
  }

  @Override
  public ProductImageDto delete(Integer id) {
    final ProductImageEntity found = imageRepo.findById(id).orElse(null);
    if(found == null) {
      return null;
    }
    imageRepo.deleteById(found.getId());
    return imageMapper.entityToModel(found);
  }

}
