package daj.user.internal.service;

import org.springframework.stereotype.Service;

import daj.common.error.ErrorResponse;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.IHasher;
import daj.user.visible.port.in.IJwtService;
import daj.user.visible.port.in.ILoginInputPort;
import daj.user.visible.port.out.IUserReaderOutputPort;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService implements ILoginInputPort {
  
  private final IUserReaderOutputPort userReader;

  private final IJwtService jwtService;

  private final IHasher hasher;

  @Override
  public UserDto login(UserDto input) {
    final var userAuthInfoFound = userReader.findByUsername(input.getUsername());

    if(userAuthInfoFound == null) {
      throw new ErrorResponse("bad credentials", 400, "une");
    }

    if(!hasher.matches(input.getPassword(), userAuthInfoFound.getPassword())) {
      throw new ErrorResponse("bad credentials", 400, "ce");
    }

    final String token = jwtService.generateToken(userAuthInfoFound.getId());

    final var output = new UserDto();
    output.setId(userAuthInfoFound.getId());
    output.setRoles(userAuthInfoFound.getRoles());
    output.setUsername(userAuthInfoFound.getUsername());
    output.setToken(token);
    return output;
  }
  
}
