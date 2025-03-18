package daj.adapter.user.inWeb;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import daj.adapter.common.authConfig.AuthConfig;
import daj.adapter.user.utils.UserUtilBeans;
import daj.common.types.AppPage;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserRoleDto;
import daj.user.visible.port.in.IUserCrudInputPort;

@ActiveProfiles("test")
@WebMvcTest({AuthConfig.class, UserController.class, UserUtilBeans.class})
public class UserControllerTest {

  @MockBean
  private IUserCrudInputPort userCrudIP;

  @Autowired
  private MockMvc mvc;

  @Test
  @WithMockUser(username="random",roles={"admin_users"})
  void testFindByPage() throws Exception {
    final String point = UserController.POINT_BY_PAGE + "?page=0&size=10";
    final var content = new ArrayList<UserDto>();
    final var roles = new ArrayList<UserRoleDto>();
    roles.add(new UserRoleDto(1, "some_one"));
    roles.add(new UserRoleDto(2, "some_two"));
    roles.add(new UserRoleDto(3, "some_three"));

    content.add(new UserDto(1, "mario1", "mario1@email.com", "somePass1", "someToken1", roles, new Date(), new Date()));
    content.add(new UserDto(2, "mario2", "mario2@email.com", "somePass2", "someToken2", roles, new Date(), new Date()));
    content.add(new UserDto(3, "mario3", "mario3@email.com", "somePass3", "someToken3", roles, new Date(), new Date()));
    content.add(new UserDto(4, "mario4", "mario4@email.com", "somePass4", "someToken4", roles, new Date(), new Date()));
    final var output = new AppPage<UserDto>(content, 20, 0, 10);
    final var req = get(point)
      .contentType(MediaType.APPLICATION_JSON)
      .param("page", "0")
      .param("size", "10")
    ;

    when(userCrudIP.findByPage(any())).thenReturn(output);

    final var resp = mvc.perform(req);

    resp
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content.length()").value(content.size()))
      .andExpect(jsonPath("$.content[0].id", is(content.get(0).getId())))
      .andExpect(jsonPath("$.content[0].username").value(content.get(0).getUsername()))
      .andExpect(jsonPath("$.content[0].email").value(content.get(0).getEmail()))
      .andExpect(jsonPath("$.content[0].roles", hasSize(content.get(0).getRoles().size())))
    ;
  }

}
