package daj.adapter.user.inWeb.reqAndResp;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPictureUploadResponse {
  
  private Integer idPicture;

  private Integer idUser;

  private String urlPicture;

  private Date createdAt;


}
