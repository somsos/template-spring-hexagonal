package daj.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import daj.user.internal.service.JwtService;
import daj.user.internal.service.LoginService;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.IHasher;
import daj.user.visible.port.out.IUserReaderOutputPort;
import daj.common.error.ErrorResponse;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

  @Mock
  private IUserReaderOutputPort userReader;

  @Mock
  private JwtService jwtService;

  private IHasher hasher = new HasherTest();

  private LoginService loginService;

  @BeforeEach
  void setUp() throws Exception {
    this.loginService = new LoginService(userReader, jwtService, hasher);
  }

  @Test
  void testLogin_successLogin() {
    final var userInDb = new UserDto();
    userInDb.setId(1);
    userInDb.setUsername("mario1");
    userInDb.setPassword(hasher.encode("mario1p"));

    final var userInput = new UserDto();
    userInput.setUsername("mario1");
    userInput.setPassword("mario1p");

    final var tokenToGenerate = "some-token";

    when(this.userReader.findByUsername( any() )).thenReturn(userInDb);
    when(this.jwtService.generateToken(any())).thenReturn(tokenToGenerate);
    
    final UserDto output = this.loginService.login(userInput);

    Mockito.verify(jwtService, times(1)).generateToken(userInDb.getId());
    assertEquals(userInDb.getId(), output.getId());
    assertEquals(output.getToken(), tokenToGenerate);
  }


  @Test
  void testLogin_failLogin_userNotFound() {
    final var userInput = new UserDto();
    userInput.setUsername("mario1");
    userInput.setPassword("mario1p");

    when(this.userReader.findByUsername( any() )).thenReturn(null);

    try {
      this.loginService.login(userInput);  
    } catch (Exception e) {
      assertEquals("bad credentials", e.getMessage());
      return ;
    }
    
    fail("login should throw exception");
  }

  @Test
  void testLogin_failLogin_userPasswordNotMath() {
    final var userInput = new UserDto();
    userInput.setUsername("mario1");
    userInput.setPassword("mario1XXX");

    final var userInDb = new UserDto();
    userInput.setId(1);
    userInput.setUsername("mario1");
    userInput.setPassword("mario1p");

    when(this.userReader.findByUsername( any() )).thenReturn(userInDb);

    try {
      this.loginService.login(userInput);  
    } catch (ErrorResponse e) {
      assertEquals("bad credentials", e.getMessage());
      return ;
    }
    
    fail("login should throw exception");
  }


}
