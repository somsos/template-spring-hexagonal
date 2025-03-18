package daj.adapter.product.outDB.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import daj.adapter.product.outDB.entity.ProductImageEntity;

@Repository
public interface ImageProductRepository extends CrudRepository<ProductImageEntity, Integer> {

  List<ProductImageEntity> findByProductId(Integer id);

}
