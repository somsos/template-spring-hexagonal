package daj.user.visible.port.in;

import daj.common.types.AppPage;
import daj.user.visible.port.dto.UserDto;
import org.springframework.data.domain.PageRequest;

public interface IUserCrudInputPort {

  AppPage<UserDto> findByPage(PageRequest pageReq);

  
  
}
