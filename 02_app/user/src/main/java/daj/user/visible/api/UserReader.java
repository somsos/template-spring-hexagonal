package daj.user.visible.api;

import org.springframework.stereotype.Service;

import daj.common.depends.user.IUserDepends;
import daj.common.depends.user.UserMDto;
import daj.common.error.ErrorResponse;
import daj.user.visible.IUserConstants;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.out.IUserReaderOutputPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserReader implements IUserDepends {

  final IUserReaderOutputPort userReadings;

  @Override
  public UserMDto findByIdOrThrow(Integer id) {
    final UserDto found = userReadings.findById(id);
    if(found == null) {
      throw new ErrorResponse(IUserConstants.ERROR_USER_NOT_FOUND, 400, null);
    }
    final var mapped = found.toMDto();
    return mapped;
  }
  
}
