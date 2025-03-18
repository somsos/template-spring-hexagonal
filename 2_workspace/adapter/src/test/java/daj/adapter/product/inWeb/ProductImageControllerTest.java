package daj.adapter.product.inWeb;

import static daj.adapter.common.AuthConstants.ROLE_REGISTERED;
import static daj.adapter.common.AuthConstants.ROLE_PRODUCT;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import daj.adapter.common.authConfig.AuthConfig;
import daj.product.visible.config.IProductConstants;
import daj.product.visible.port.dto.ProductImageDto;
import daj.product.visible.port.in.IProductImageInputPort;

@ActiveProfiles("test")
@WebMvcTest({ ProductImageController.class, AuthConfig.class })
public class ProductImageControllerTest {

  @MockBean
  private IProductImageInputPort imageIP;

  @Autowired
  private MockMvc mvc;  

  //####### upload image ########

  @Test
  void test_uploadImage_mustBeProtected_FromAnonymousUsers() throws Exception {
    final var endpoint = IProductConstants.POINT_PRODUCTS_IMAGE.replace("{id}", "1");
    mvc.perform(post(endpoint).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_REGISTERED})
  void test_uploadImage_mustBeProtected_FromRegisteredUsers() throws Exception {
    final var endpoint = IProductConstants.POINT_PRODUCTS_IMAGE.replace("{id}", "1");
    mvc.perform(post(endpoint).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_PRODUCT})
  void test_uploadImage_success() throws Exception {
    final var image = new MockMultipartFile("image", "filename.png", MediaType.IMAGE_PNG.getType(), "soma_data".getBytes());
    final var endpoint = IProductConstants.POINT_PRODUCTS_IMAGE.replace("{id}", "1");
    final var request = MockMvcRequestBuilders.multipart(endpoint).file(image);

    final var outPut = new ProductImageDto(1, image.getName(), image.getContentType(), image.getBytes(), null);
    when(imageIP.saveImage(any())).thenReturn(outPut);

    mvc.perform(request)
      .andExpect(status().is(HttpStatus.CREATED.value()))
      .andExpect(jsonPath("$.id", is(outPut.getId())))
    ;

  }


  


  //####### get image ########
  @Test
  void test_getImage_mustFail_imageNotFound() throws Exception {
    final var endpoint = IProductConstants.POINT_PRODUCTS_IMAGE_ID.replace("{id}", "777");
    mvc.perform(get(endpoint).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(IProductConstants.ERROR_IMAGE_NOT_FOUND)))
    ;
  }


  
  
  
  //####### delete ########
  @Test
  void testDelete_mustBeProtected_FromAnonymousUsers() throws Exception {
    final var endpoint = IProductConstants.POINT_PRODUCTS_IMAGE_ID.replace("{id}", "1");
    mvc.perform(delete(endpoint).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_REGISTERED})
  void testDelete_mustBeProtected_FromRegisteredUsers() throws Exception {
    final var endpoint = IProductConstants.POINT_PRODUCTS_IMAGE_ID.replace("{id}", "1");
    mvc.perform(delete(endpoint).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }


  @Test
  @WithMockUser(username="mario1",roles={ROLE_PRODUCT})
  void testDelete_success() throws Exception {
    
    final var output = new ProductImageDto();
    output.setId(1);

    final var endpoint = IProductConstants.POINT_PRODUCTS_IMAGE_ID.replace("{id}", "1");
    final var request = delete(endpoint);

    when(imageIP.delete(any())).thenReturn(output);
    
    mvc.perform(request)
    .andExpect(status().is(HttpStatus.ACCEPTED.value()))
    .andExpect(jsonPath("$.id", is(output.getId())))
    ;

  }





}
