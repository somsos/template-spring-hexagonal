package daj.adapter.product.outDB;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import daj.adapter.product.outDB.repository.ProductRepository;
import daj.adapter.product.utils.ProductUtilBeans;
import daj.common.error.ErrorResponse;
import daj.product.visible.config.IProductConstants;

@ActiveProfiles("test")
@DataJpaTest
@Import({ProductReaderDbAdapter.class, ProductUtilBeans.class})
public class ProductReaderDbAdapterTest {

  @Autowired
  ProductReaderDbAdapter productReader;

  @Autowired
  ProductRepository repo;

  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  @Test
  @Sql("test_createProduct.sql")
  void test_findByIdOrThrow() {
    var found = productReader.findByIdOrThrow(1);
    assertEquals("trompo1", found.getName());
    assertEquals("image1.png", found.getImages().get(0).getName());
    assertEquals(Integer.valueOf(2), found.getImages().get(1).getId());
  }

  @Test
  @Sql("test_createProducts.sql")
  void testFindByPage() {
    final var sizeWanted = 5;
    final var pageFound = productReader.findByPage(3, sizeWanted);

    final var firstProduct = pageFound.getContent().get(pageFound.getContent().size() - 5);
    final var lastProduct = pageFound.getContent().get(pageFound.getContent().size()-1);
    assertEquals(sizeWanted, pageFound.getContent().size());
    assertEquals(20, pageFound.getTotalElements());
    assertEquals(4, pageFound.getTotalPages());
    assertEquals("Pet Bed", firstProduct.getName());
    assertEquals(4, firstProduct.getImages().size());
    var firstProductFirstImage = firstProduct.getImages().get(0);
    assertEquals(Integer.valueOf(1), firstProductFirstImage.getId());

    //Note: the product id 20 is deleted so it should not return product id 20
    assertEquals(Integer.valueOf(21), lastProduct.getId());
    assertEquals(2, lastProduct.getImages().size());
    var firstProductLastImage = lastProduct.getImages().get(lastProduct.getImages().size()-1);
    assertEquals(Integer.valueOf(6), firstProductLastImage.getId());

    assertEquals("Board Game", lastProduct.getName());
  }


  @Test
  @Sql("test_createDeletedProduct.sql")
  void testFindByIdOrThrow_mustNotReturnProductsMarkedAsDeleted() {
    var ex = assertThrows(ErrorResponse.class,
      () -> productReader.findByIdOrThrow(1),
      "Not found exception expected"
    );
    assertEquals(IProductConstants.NOT_FOUND, ex.getMessage());
  }

  @Test
  @Sql("test_createProduct.sql")
  void testDelete_mustJustMarkTheUserAsDeleted() {
    var mustExist = repo.findById(1).orElse(null);
    assertEquals(Integer.valueOf(1), mustExist.getId());
    repo.deleteById(1);

    var mustExistMarkedAsDeleted = jdbcTemplate.queryForObject("select id from products;", Integer.class);
    assertEquals(Integer.valueOf(1), mustExistMarkedAsDeleted);

    var mustBeNull = repo.findById(1).orElse(null);
    assertEquals(null, mustBeNull);
  }

}
