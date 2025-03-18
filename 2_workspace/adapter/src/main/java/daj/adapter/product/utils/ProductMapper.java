package daj.adapter.product.utils;

import java.util.List;
import java.util.ArrayList;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import daj.adapter.product.inWeb.reqAndResp.ProductDetailsResponse;
import daj.adapter.product.inWeb.reqAndResp.ProductSaveRequest;
import daj.adapter.product.inWeb.reqAndResp.ProductUpdateRequest;
import daj.adapter.product.outDB.entity.ProductEntity;
import daj.adapter.user.outDB.entity.UserEntity;
import daj.common.depends.user.UserMDto;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.dto.ProductImageDto;


@Mapper
public interface ProductMapper {

  @Mapping(ignore = true, target = "images")
  ProductDto entityToModel(ProductEntity source);
  default UserMDto mapOwner(UserEntity s) {
    if(s == null) {
      return null;
    }
    
    List<String> roles = new ArrayList<>();
    if(s.getRoles() != null) {
      roles = s.getRoles().stream().map(re -> re.getAuthority()).toList();
    }
    final var mapped = new UserMDto(s.getId(), roles);
    return mapped;
  }


  @Mapping(ignore = true, target = "images")
  ProductEntity modelToEntity(ProductDto entitySource);
  default UserEntity mapOwner(UserMDto s) {
    final var m = new UserEntity();
    m.setId(s.getId());
    return m;
  }


  //user input
  ProductDto saveRequestToEntity(ProductSaveRequest source);
  default UserMDto mapOwner(Integer s) {
    final var m = new UserMDto(s, null);
    return m;
  }

  ProductDto updateRequestToEntity(ProductUpdateRequest source);

  //user output
  ProductDetailsResponse modelToDetails(ProductDto m);

  List<ProductDetailsResponse> listModelsToDetails(List<ProductDto> source);

  default List<Integer> mapImages(List<ProductImageDto> images) {
    if(images == null) {
      return new ArrayList<Integer>();
    }
    List<Integer> listIdImages = images.stream()
      .map(m -> m.getId()).toList();
    return listIdImages;
  }  

  List<ProductDto> listEntitiesToModels(List<ProductEntity> content);
  

}