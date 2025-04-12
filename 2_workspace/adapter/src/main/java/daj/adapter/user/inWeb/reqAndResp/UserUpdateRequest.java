package daj.adapter.user.inWeb.reqAndResp;

import java.util.List;

import daj.user.visible.port.dto.UserRoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
  
  private String username;
  
  private String email;
  
  private String password;
  
  private String oldPassword;

  private List<UserRoleDto> roles;

}
