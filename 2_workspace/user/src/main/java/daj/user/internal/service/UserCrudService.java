package daj.user.internal.service;

import org.springframework.stereotype.Service;

import daj.common.types.AppPage;
import daj.common.types.PageAndFilterRequestDto;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.IUserCrudInputPort;
import daj.user.visible.port.out.IUserReaderOutputPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCrudService implements IUserCrudInputPort {

  private final IUserReaderOutputPort userReader;

  @Override
  public AppPage<UserDto> findByPage(PageAndFilterRequestDto reqDto) {
    return userReader.findByPage(reqDto);
  }
  
}
