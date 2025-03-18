package daj.adapter.product.utils;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUtilBeans {

  @Bean
  public ProductMapper productMapper() {
    return Mappers.getMapper(ProductMapper.class);
  }

  @Bean
  public ProductImageMapper productImageMapper() {
    return Mappers.getMapper(ProductImageMapper.class);
  }

}
