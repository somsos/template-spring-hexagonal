package daj.user.visible.port.in;

import daj.common.types.AppPage;
import daj.common.types.PageAndFilterRequestDto;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserPictureDto;

public interface IUserCrudInputPort {

  AppPage<UserDto> findByPage(PageAndFilterRequestDto reqDto);

  UserDto save(UserDto casted);

  UserPictureDto saveImage(UserPictureDto requestDto);

  UserPictureDto findImageByUserId(Integer id);

  UserDto getUserById(Integer id);

  UserDto update(Integer id, UserDto casted);
  
}
