package daj.adapter.product.outDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import daj.adapter.product.outDB.entity.ProductEntity;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

  ProductEntity findByName(String name);

  @Query("select p.id from ProductEntity p where p.id = ?1")
  Integer findIfExistsById(Integer id);

  Page<ProductEntity> findAllByOrderByIdAsc(Pageable pageable);
  
}
