package daj.user.visible.port.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPictureDto {

  private Integer idPicture;

  private Integer idUser;

  private String urlPicture;

  private String type;

  private Date createdAt;

  private byte[] file;
  
}
