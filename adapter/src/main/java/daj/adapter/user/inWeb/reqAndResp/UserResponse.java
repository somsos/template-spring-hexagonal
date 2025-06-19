package daj.adapter.user.inWeb.reqAndResp;

import java.util.Date;
import java.util.List;

import daj.user.visible.port.dto.UserRoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

  private final Integer id;

  private final String username;

  private final String email;

  private List<UserRoleDto> roles;

  private Date createdAt;

  private Date updatedAt;
  
}
