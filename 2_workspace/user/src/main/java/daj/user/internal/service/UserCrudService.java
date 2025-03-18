package daj.user.internal.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import daj.common.types.AppPage;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.IUserCrudInputPort;
import daj.user.visible.port.out.IUserReaderOutputPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCrudService implements IUserCrudInputPort {

  private final IUserReaderOutputPort userReader;

  @Override
  public AppPage<UserDto> findByPage(PageRequest pageReq) {
    return userReader.findByPage(pageReq);
  }
  
}
