package daj.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import daj.user.internal.service.RegisterService;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserRoleDto;
import daj.user.visible.port.in.IHasher;
import daj.user.visible.port.out.IUserWriterOutputPort;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

  @Mock
  IUserWriterOutputPort userDbWriter;

  final private IHasher hasher = new HasherTest();
  
  @Test
  void testRegister_success() {
    var input = new UserDto();
    input.setUsername("mario1");
    input.setEmail("mario@email.com");
    input.setPassword("mario1p");
    
    final var rolesOnRegister = new ArrayList<UserRoleDto>();
    // check roles in import.sql
    int publicRoleId = -53;
    rolesOnRegister.add(new UserRoleDto(publicRoleId, null));

    final var inDB = new UserDto();
    inDB.setId(1);
    inDB.setRoles(rolesOnRegister);
    when(userDbWriter.register(any(), any())).thenReturn(inDB);

    RegisterService service = new RegisterService(userDbWriter, hasher);
    final UserDto output = service.register(input);

    assertEquals(1, output.getId());
    assertEquals(publicRoleId, output.getRoles().get(0).getId());
  }

}
