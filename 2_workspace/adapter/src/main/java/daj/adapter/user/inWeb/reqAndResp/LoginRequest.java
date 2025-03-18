package daj.adapter.user.inWeb.reqAndResp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
  
  @NotBlank
  @Size(min = 3, max = 16)
  private String username;

  @NotBlank
  @Size(min = 3, max = 16)
  private String password;
  
}
