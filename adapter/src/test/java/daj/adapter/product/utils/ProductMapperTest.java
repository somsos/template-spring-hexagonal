package daj.adapter.product.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import daj.adapter.product.outDB.entity.ProductEntity;
import daj.product.visible.port.dto.ProductDto;

public class ProductMapperTest {

  @Test
  public void castModelToEntity() {
    final var mapper = Mappers.getMapper(ProductMapper.class);

    final var modelSource = new ProductDto();
    final ProductEntity entityTarget = mapper.modelToEntity(modelSource);

    assertEquals(modelSource.getId(), entityTarget.getId());

  }

  @Test
  public void castEntityToModel() {
    final var mapper = Mappers.getMapper(ProductMapper.class);
    final var entitySource = new ProductEntity(1, null, null, null, null, null, null, null, null);
    final ProductDto modelTarget = mapper.entityToModel(entitySource);

    assertEquals(entitySource.getId(), modelTarget.getId());

  }
  
}
