package daj.user.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import daj.user.visible.port.in.IHasher;

@Profile("test")
@Component
public class HasherTest implements IHasher {

  @Override
  public String encode(String rawPassword) {
    return "XX-" + rawPassword + "-XX";
    
  }

  @Override
  public boolean matches(String rawPassword, String encodedPassword) {
    final var rawEncoded = "XX-" + rawPassword + "-XX";
    return rawEncoded.equals(encodedPassword);
    
  }

}
