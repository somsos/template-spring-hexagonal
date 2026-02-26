package daj.adapter.product.outDB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import daj.adapter.product.outDB.entity.ProductEntity;
import daj.adapter.product.outDB.repository.ProductRepository;
import daj.adapter.product.utils.ProductUtilBeans;
import daj.common.error.ErrorResponse;
import daj.product.visible.config.IProductConstants;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.out.IProductWriterOutputPort;

@ActiveProfiles("test")
@DataJpaTest
@Import({ProductWriterDBAdapter.class, ProductUtilBeans.class, ProductReaderDbAdapter.class, ProductReaderDbAdapter.class})
public class ProductWriterDbAdapterTest {

  @Autowired
  IProductWriterOutputPort productDBAdapter;

  @Autowired
  ProductRepository repo;
  
  @Test
  void testSave() {
    var input = new ProductDto(null, "trompo1", 10.10f, 10, "description1", null, null, null);
    productDBAdapter.save(input);
    final ProductEntity found = repo.findByName(input.getName());
    assertNotNull(found.getCreatedAt());
  }

  @Test
  @Sql("test_createProduct.sql")
  void testDelete() {
    Integer toDel = 1;
    ProductDto deleted = productDBAdapter.delete(toDel);
    assertEquals(toDel, deleted.getId());

    final ProductEntity found = repo.findById(toDel).orElse(null);
    assertNull(found);
  }

  @Test
  void testDelete_notFound() {
    ErrorResponse ex = assertThrows(
      ErrorResponse.class,
           () -> productDBAdapter.delete(1),
           "Not found exception expected"
    );
    assertEquals(IProductConstants.NOT_FOUND, ex.getMessage());
  }



  @Test
  @Sql("test_createProduct.sql")
  void testUpdate() {
    var newInfo = new ProductDto(null, "someName1", 10.10f, 10, null, null, null, null);
    Integer id = 1;
    productDBAdapter.update(id, newInfo);

    final ProductEntity found = repo.findById(1).orElse(null);
    assertEquals(id, found.getId());
    assertEquals(newInfo.getAmount(), found.getAmount());
    //nulls must be keep the same value
    assertEquals("description1", found.getDescription());
    assertEquals(newInfo.getName(), found.getName());
    assertEquals(newInfo.getPrice(), found.getPrice());
  }

}
