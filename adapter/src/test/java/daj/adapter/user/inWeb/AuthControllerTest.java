package daj.adapter.user.inWeb;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import daj.adapter.common.authConfig.AuthConfig;
import daj.adapter.user.inWeb.reqAndResp.LoginRequest;
import daj.adapter.user.inWeb.reqAndResp.RegisterRequest;
import daj.adapter.user.utils.UserUtilBeans;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.ILoginInputPort;
import daj.user.visible.port.in.IRegisterInputPort;
import daj.user.visible.port.out.IUserReaderOutputPort;

import static daj.adapter.common.AuthConstants.ROLE_REGISTERED;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//WARNING: there is a class with the same name
//import jakarta.security.auth.message.config.AuthConfig;

//@Import({AuthConfig.class})
@WebMvcTest({AuthConfig.class, AuthController.class, UserUtilBeans.class})
@ActiveProfiles("test")
public class AuthControllerTest {

  @MockBean
  IUserReaderOutputPort userDbReader;

  @MockBean
  ILoginInputPort loginInputPort;

  @MockBean
  IRegisterInputPort registerInputPort;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private MockMvc mvc;

  
  @Test
  void know_path_protection_from_anonymous() throws Exception {
    mvc.perform(get(AuthController.CHECK_REGISTERED_USER).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username="random",roles={ROLE_REGISTERED})
  void pathSecurityByRole_allowRegisteredUsersToTheyRoutes() throws Exception {
    mvc.perform(get(AuthController.CHECK_REGISTERED_USER).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username="random",roles={"admin_users"})
  void pathSecurityByRole_allowAdminUserToTheirRoutes() throws Exception {
    mvc.perform(get(AuthController.CHECK_USERS_ROLE).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test()
  @WithMockUser(username="random",roles={"admin_products"})
  void pathSecurityByRole_allowAdminProductsToTheirRoutes() throws Exception {
    mvc.perform(get(AuthController.CHECK_PRODUCT_ROLE).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  void random_path_protection_from_anonymous() throws Exception {
    mvc.perform(get(getRandomPath()).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden());
  }

  private String getRandomPath() {
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(targetStringLength);
    for (int i = 0; i < targetStringLength; i++) {
        int randomLimitedInt = leftLimit + (int) 
          (random.nextFloat() * (rightLimit - leftLimit + 1));
        buffer.append((char) randomLimitedInt);
    }
    return "/" + buffer.toString();
  }

  @Test
  void testLogin_success() throws Exception {
    
    //scenario
    final var input = new LoginRequest("mario1", "mario1p");
    final var userInDb = new UserDto();
    userInDb.setId(1);
    userInDb.setUsername("mario1");
    userInDb.setPassword("mario1p");

    final var output = new UserDto();
    userInDb.setId(1);
    userInDb.setToken("some-token");
    
    final var request = post(AuthController.LOGIN_PATH).contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(input));

    when(userDbReader.findById(any())).thenReturn(userInDb);
    when(loginInputPort.login(any())).thenReturn(output);
    
    //test
    mvc.perform(request)
      .andExpect(status().is2xxSuccessful())
      .andExpect(jsonPath("$.id", is(output.getId())))
      .andExpect(jsonPath("$.token", is(output.getToken())))
    ;
    
  }

  @Test
  void testRegister_success() throws Exception {
    final var input = new RegisterRequest("mario3", "mario3@email.com", "mario3p");


    final var output = new UserDto();
    output.setId(1);

    when(registerInputPort.register(any())).thenReturn(output);

    final var request = post(AuthController.REGISTER_PATH)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(input));

    mvc.perform(request)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(output.getId())))
    ;

  }

  @Test
  void testRegister_errorPasswordRequired() throws Exception {
    final var input = new RegisterRequest("mario3", "mario3@email.com", " ");
    final var output = new UserDto();
    output.setId(1);

    when(registerInputPort.register(any())).thenReturn(output);

    final var request = post(AuthController.REGISTER_PATH)
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(input));

    mvc.perform(request)
      .andExpect(status().isBadRequest())
    ;

  }

}
