package daj.adapter.user.inWeb.reqAndResp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAddResponse extends UserAddRequest{
  private Integer id;
  private String createdAt;
}
