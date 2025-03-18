package daj.user.visible.port.in;

import daj.user.visible.port.dto.UserDto;

public interface ILoginInputPort {

  UserDto login(UserDto input);
  
}
