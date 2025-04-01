package daj.user.visible.port.out;

import daj.common.types.AppPage;
import daj.common.types.PageAndFilterRequestDto;
import daj.user.visible.port.dto.UserDto;

public interface IUserReaderOutputPort {
  
  UserDto findByUsername(String username);

  UserDto findById(Integer userId);

  AppPage<UserDto> findByPage(PageAndFilterRequestDto reqDto);

}
