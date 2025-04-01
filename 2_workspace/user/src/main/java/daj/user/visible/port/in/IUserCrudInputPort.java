package daj.user.visible.port.in;

import daj.common.types.AppPage;
import daj.common.types.PageAndFilterRequestDto;
import daj.user.visible.port.dto.UserDto;

public interface IUserCrudInputPort {

  AppPage<UserDto> findByPage(PageAndFilterRequestDto reqDto);
  
}
