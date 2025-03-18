package daj.user.testComponents;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import daj.common.types.AppPage;
import daj.user.visible.port.dto.UserDto;
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
  public AppPage<UserDto> findByPage(PageRequest pageReq) {
    throw new UnsupportedOperationException("Unimplemented method 'findByPage'");
  }

  
  
}
