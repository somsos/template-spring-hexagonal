package daj.user.visible.port.out;

import org.springframework.data.domain.PageRequest;

import daj.common.types.AppPage;
import daj.user.visible.port.dto.UserDto;

public interface IUserReaderOutputPort {
  
  UserDto findByUsername(String username);

  UserDto findById(Integer userId);

  AppPage<UserDto> findByPage(PageRequest pageReq);

}
