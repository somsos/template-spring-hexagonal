package daj.adapter.product.inWeb;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import daj.adapter.common.authConfig.AuthConfig;
import daj.adapter.product.utils.ProductUtilBeans;
import daj.common.types.AppPage;
import daj.product.visible.config.IProductConstants;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.dto.ProductImageDto;
import daj.product.visible.port.in.IProductReadInputPort;
import daj.product.visible.port.out.IProductReaderOutputPort;

@WebMvcTest({
  ProductReaderController.class, ProductUtilBeans.class,
  AuthConfig.class,
})
@ActiveProfiles("test")
public class ProductReaderControllerTest {

  @MockBean
  IProductReaderOutputPort repo;

  @MockBean
  private IProductReadInputPort productReaderInputPort;

  @Autowired
  private MockMvc mvc;

  @Test
  void testFindById_success() throws Exception {
    final var output = new ProductDto(1, "Product 1", 10.0f, 100, "Description 1", new Date(), null, null);
    final var image1P1 = new ProductImageDto(1, null, null, null, null);
    final var image2P1 = new ProductImageDto(2, null, null, null, null);
    List<ProductImageDto> product1Images = Arrays.asList(image1P1, image2P1);
    output.setImages(product1Images);
    
    when(productReaderInputPort.findDetailsById(1)).thenReturn(output);

    final var point = IProductConstants.POINT_PRODUCTS_ID.replace("{id}", "1");
    final var req = get(point);
    
    final var resp = mvc.perform(req);

    resp.andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(output.getId())))
      .andExpect(jsonPath("$.name").value("Product 1"))
      .andExpect(jsonPath("$.images", hasSize(2)))
      .andExpect(jsonPath("$.images[0]", is(1)))      
    ;
      
  }


  @Test
  void testFindByIdOrThrow_fail_notFound() throws Exception {
    final var req = get(IProductConstants.POINT_PRODUCTS + "/1");
    when(repo.findDetailsById(1)).thenReturn(null);
    
    final var resp = mvc.perform(req);

    resp.andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(IProductConstants.NOT_FOUND)));
  }





  @Test
  void testFindByPage() throws Exception {
    // Mock the product response
    final var product1 = new ProductDto(1, "Product 1", 10.0f, 100, "Description 1", new Date(), null, null);
    final var image1P1 = new ProductImageDto(1, null, null, null, null);
    final var image2P1 = new ProductImageDto(2, null, null, null, null);
    List<ProductImageDto> product1Images = Arrays.asList(image1P1, image2P1);
    product1.setImages(product1Images);

    final var product2 = new ProductDto(2, "Product 2", 20.0f, 200, "Description 2", new Date(), null, null);
    List<ProductDto> products = Arrays.asList(product1, product2);

    // Mock the behavior of the service
    final var pageFound = new AppPage<ProductDto>(products, 10, 0, 10);
    when(productReaderInputPort.findByPage(0, 10)).thenReturn(pageFound);

    // Perform the GET request
    mvc.perform(get(IProductConstants.POINT_PRODUCTS_BY_PAGE)
        .param("page", "0")
        .param("size", "10")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(2))
        .andExpect(jsonPath("$.content[0].id", is(1)))
        .andExpect(jsonPath("$.content[0].name").value("Product 1"))
        .andExpect(jsonPath("$.content[0].images", hasSize(2)))
        .andExpect(jsonPath("$.content[0].images[1]", is(2)))
        .andExpect(jsonPath("$.content[1].name").value("Product 2"))
    ;
  }

  

}
