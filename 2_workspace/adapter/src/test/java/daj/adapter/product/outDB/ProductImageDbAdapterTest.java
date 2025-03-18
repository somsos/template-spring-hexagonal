package daj.adapter.product.outDB;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import daj.adapter.product.outDB.repository.ImageProductRepository;
import daj.adapter.product.utils.ProductUtilBeans;

@ActiveProfiles("test")
@DataJpaTest
@Import({ProductImageDbAdapter.class, ProductUtilBeans.class})
public class ProductImageDbAdapterTest {

  @MockBean
  ProductReaderDbAdapter productRA;

  @Autowired
  ImageProductRepository repo;

  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  @Test
  @Sql("test_createProduct.sql")
  void testDelete_mustJustMarkTheProductImageAsDeleted() {
    var mustExist = repo.findById(1).orElse(null);
    assertEquals("image1.png", mustExist.getName());
    
    repo.deleteById(1);

    var mustExistMarkedAsDeleted = jdbcTemplate.queryForObject("select id from product_images where id = 1;", Integer.class);
    assertEquals(Integer.valueOf(1), mustExistMarkedAsDeleted);

    var mustBeNull = repo.findById(1).orElse(null);
    assertEquals(null, mustBeNull);
  }

  /* 
  @Test
  void testFindImageById() {

  }

  @Test
  void testSaveImage() {

  }
  */
}
