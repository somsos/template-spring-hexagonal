package daj.adapter.product.inWeb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static daj.adapter.common.AuthConstants.ROLE_REGISTERED;
import static daj.adapter.common.AuthConstants.ROLE_PRODUCT;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

import daj.adapter.common.authConfig.AuthConfig;
import daj.adapter.product.inWeb.reqAndResp.ProductSaveRequest;
import daj.adapter.product.inWeb.reqAndResp.ProductUpdateRequest;
import daj.adapter.product.utils.ProductUtilBeans;
import daj.product.visible.config.IProductConstants;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.in.IProductWriteInputPort;
import daj.user.visible.port.out.IUserReaderOutputPort;

@WebMvcTest({ ProductWriterController.class, ProductUtilBeans.class, AuthConfig.class })
@ActiveProfiles("test")
public class ProductWriterControllerTest {

  @MockBean
  IProductWriteInputPort writerIP;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private MockMvc mvc;

  @MockBean
  IUserReaderOutputPort userDbReader;
  



  // ############# Save
  @Test
  void testSave_mustBeProtected_FromAnonymousUsers() throws Exception {
    mvc.perform(post(IProductConstants.POINT_PRODUCTS).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_REGISTERED})
  void testSave_mustBeProtected_FromRegisteredUsers() throws Exception {
    mvc.perform(post(IProductConstants.POINT_PRODUCTS).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_PRODUCT})
  void testSave_success() throws Exception {

    //Scenario null, "trompo1", 10.10f, 10, "description1", null, null
    final var input = ProductSaveRequest.builder().name("trompo1").price(10.1f).description("description1").amount(10).build();

    final var output = new ProductDto(1, null, null, null, null, null, null, null);
    final var request = post(IProductConstants.POINT_PRODUCTS).contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(input));
    when(writerIP.save(any())).thenReturn(output);

    //Test
    mvc.perform(request)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(output.getId())))
    ;
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_PRODUCT})
  void testSave_mustNotAccept_RequestsWithoutName() throws Exception {

    //Scenario
    final var input = new ProductDto(null, " ", 1.10f, 10, "description1", null, null, null);
    final var output = new ProductDto(1, null, null, null, null, null, null, null);
    final var request = post(IProductConstants.POINT_PRODUCTS).contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(input));
    when(writerIP.save(any())).thenReturn(output);

    //Test
    mvc.perform(request)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.causes", hasSize(3)))

      .andExpect(jsonPath("$.causes[0].path", is("price")))
      .andExpect(jsonPath("$.causes[0].message", is("must be between 10 and 100000")))
      
      .andExpect(jsonPath("$.causes[1].path", is("name")))
      .andExpect(jsonPath("$.causes[1].message", is("must not be blank")))
      
      .andExpect(jsonPath("$.causes[2].path", is("name")))
      .andExpect(jsonPath("$.causes[2].message", is("length must be between 4 and 64")))

    ;
  }


  // ############# Delete
  @Test
  void testDelete_mustBeProtected_FromAnonymousUsers() throws Exception {
    mvc.perform(delete(IProductConstants.POINT_PRODUCTS + "/1").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_REGISTERED})
  void testDelete_mustBeProtected_FromRegisteredUsers() throws Exception {
    mvc.perform(delete(IProductConstants.POINT_PRODUCTS + "/1").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_PRODUCT})
  void testDelete_success() throws Exception {

    //Scenario
    final var request = delete(IProductConstants.POINT_PRODUCTS_ID.replace("{id}", "1"))
      .contentType(MediaType.APPLICATION_JSON);

    final var output = new ProductDto(1, null, null, null, null, null, null, null);
    
    when(writerIP.delete(1)).thenReturn(output);

    //Test
    mvc.perform(request)
      .andExpect(status().is(HttpStatus.ACCEPTED.value()))
      .andExpect(jsonPath("$.id", is(output.getId())))
    ;
  }


  // ############# Update
  @Test
  void testUpdate_mustBeProtected_FromAnonymousUsers() throws Exception {
    final var point = IProductConstants.POINT_PRODUCTS_ID.replace("{id}", "1");
    mvc.perform(put(point).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_REGISTERED})
  void testUpdate_mustBeProtected_FromRegisteredUsers() throws Exception {
    final var point = IProductConstants.POINT_PRODUCTS_ID.replace("{id}", "1");
    mvc.perform(put(point).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_PRODUCT})
  void testUpdate_success() throws Exception {

    //Scenario ("trompo100", 100.10f, 100, "description100");
    final var input = ProductUpdateRequest.builder().name("trompo100")
      .price(100.10f).amount(100).description("description100").build();
    final var output = new ProductDto(1, "trompo11", 101.101f, 101, "description101", new Date(), null, null);
    final var point = IProductConstants.POINT_PRODUCTS_ID.replace("{id}", "1");
    final var request = put(point)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(input));
      ;
    when(writerIP.update(any(), any())).thenReturn(output);

    //Test
    mvc.perform(request)
      .andExpect(status().is(HttpStatus.ACCEPTED.value()))
      .andExpect(jsonPath("$.id", is(output.getId())))
      .andExpect(jsonPath("$.name", is(output.getName())))
    ;
  }

  @Test
  @WithMockUser(username="mario1",roles={ROLE_PRODUCT})
  void testUpdate_success_updateOnlyOneField() throws Exception {
    final var input = ProductUpdateRequest.builder().amount(101).build();
    final var output = new ProductDto(1, "trompo11", 101.100f, 101, "description101", new Date(), null, null);
    final var point = IProductConstants.POINT_PRODUCTS_ID.replace("{id}", "1");
    final var request = put(point)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(input));
      ;
    when(writerIP.update(any(), any())).thenReturn(output);

    //Test
    mvc.perform(request)
      .andExpect(status().is(HttpStatus.ACCEPTED.value()))
      .andExpect(jsonPath("$.id", is(output.getId())))
      .andExpect(jsonPath("$.amount", is(output.getAmount())))
    ;
  }


}
