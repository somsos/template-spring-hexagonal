package daj.user.testComponents;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import daj.common.types.AppPage;
import daj.common.types.PageAndFilterRequestDto;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserPictureDto;
import daj.user.visible.port.out.IUserReaderOutputPort;


/*This class is just to temporally satisfy Components when this module, is
 * running tests (without adapter module),
*/

@Profile("test")
@Component
public class StuffToNotFail implements IUserReaderOutputPort {

  @Override
  public UserDto findByUsername(String username) {
    throw new UnsupportedOperationException("Unimplemented method 'getAuthInfoByUsername'");
  }

  @Override
  public UserDto findById(Integer userId) {
    throw new UnsupportedOperationException("Unimplemented method 'findAuthById'");
  }

  @Override
  public AppPage<UserDto> findByPage(PageAndFilterRequestDto reqDto) {
    throw new UnsupportedOperationException("Unimplemented method 'findByPage'");
  }

  @Override
  public UserDto save(UserDto reqDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public UserPictureDto saveImage(UserPictureDto requestDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'saveImage'");
  }

  @Override
  public UserPictureDto findImageByUserId(Integer idUser) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findImageByUserId'");
  }

  @Override
  public UserDto getUserById(Integer idUser) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
  }

  @Override
  public UserDto update(Integer idUser, UserDto newInfo) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public UserDto deleteById(Integer idUser) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
  }

  
  
}
