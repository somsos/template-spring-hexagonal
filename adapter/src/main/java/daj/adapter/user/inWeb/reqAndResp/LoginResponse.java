package daj.adapter.user.inWeb.reqAndResp;

import java.util.List;

import daj.user.visible.port.dto.UserRoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
  
  private Integer id;

  private String username;

  private List<UserRoleDto> roles;

  final private String token;
  
}
