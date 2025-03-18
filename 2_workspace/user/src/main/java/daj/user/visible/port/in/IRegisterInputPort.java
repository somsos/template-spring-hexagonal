package daj.user.visible.port.in;

import daj.user.visible.port.dto.UserDto;

public interface IRegisterInputPort {
  
  UserDto register(UserDto input);

}
