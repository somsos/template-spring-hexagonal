package daj.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import daj.user.internal.service.JwtService;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.IJwtService;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

  private IJwtService jwtService;

  @BeforeEach
  void setUp() throws Exception {
    this.jwtService = new JwtService();
  }


  @Test
  void testGenerateToken() {
    final var token = jwtService.generateToken(1);
    final String[] parts = token.split("\\.");
    assertEquals(3, parts.length);
  }

  @Test
  void testValidateToken() {
    final var user = new UserDto();
    user.setId(1);
    user.setUsername("username");
    user.setPassword("password");
    final var token = jwtService.generateToken(user.getId());
    jwtService.validateToken(token, user);
  }
}
