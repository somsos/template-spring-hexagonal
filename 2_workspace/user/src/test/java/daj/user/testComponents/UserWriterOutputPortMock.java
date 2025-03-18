package daj.user.testComponents;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserRoleDto;
import daj.user.visible.port.out.IUserWriterOutputPort;

@Profile("test")
@Component
public class UserWriterOutputPortMock implements IUserWriterOutputPort {

  @Override
  public UserDto register(UserDto toRegister, List<UserRoleDto> roles) {
    throw new UnsupportedOperationException("Unimplemented method 'register'");
  }
  
}
