package daj.adapter.product.utils;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import daj.adapter.product.outDB.entity.ProductImageEntity;
import daj.product.visible.port.dto.ProductImageDto;


@Mapper
public interface ProductImageMapper {

  @Mapping(ignore = true, target = "product.owner")
  ProductImageEntity modelToEntity(ProductImageDto source);

  @Mapping(ignore = true, target = "product.owner")
  ProductImageDto entityToModel(ProductImageEntity source);
  
  //make a recoursion error
  //List<ProductImageModel> listEntitiesToModels(List<ProductImageEntity> images);

  default List<ProductImageDto> mapImages(List<ProductImageEntity> images) {
    if(images == null) {
      return new ArrayList<ProductImageDto>();
    }
    List<ProductImageDto> listIdImages = images.stream()
      .map(m -> {
        final var c = new ProductImageDto();
        c.setId(m.getId());
        return c;
      }).toList();
    return listIdImages;
  }  
  
}
