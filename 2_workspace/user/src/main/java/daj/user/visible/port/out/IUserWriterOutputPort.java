package daj.user.visible.port.out;

import java.util.List;

import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserRoleDto;

public interface IUserWriterOutputPort {
  
  UserDto register(UserDto toRegister, List<UserRoleDto> roles);

}
