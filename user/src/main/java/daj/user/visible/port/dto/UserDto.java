package daj.user.visible.port.dto;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import daj.common.depends.user.UserMDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private Integer id;

  private String username;

  private String email;

  private String password;

  private String token;

  private List<UserRoleDto> roles;

  private Date createdAt;

  private Date updatedAt;


  public UserMDto toMDto() {
      List<String> roles = new ArrayList<>();
      if(this.getRoles() != null) {
        roles = this.getRoles().stream().map(r -> r.getAuthority()).toList();
      }
      
      final var mapped = new UserMDto(this.getId(), roles);
      return mapped;
  }
  
}
