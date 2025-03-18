package daj.adapter.user.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ActiveProfiles("test")
public class AuthConfigTest {
  
  @Test
  void testPasswordEncoder() {
    final var encoder = new BCryptPasswordEncoder();
    final var toEncode = "mario3p";
    final String hash = encoder.encode(toEncode);
    log.info(toEncode + " = " + hash);
  }
}
