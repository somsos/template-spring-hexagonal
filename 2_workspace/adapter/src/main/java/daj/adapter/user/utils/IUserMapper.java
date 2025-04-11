package daj.adapter.user.utils;

import java.util.List;
import java.util.ArrayList;

import org.mapstruct.Mapper;

import daj.adapter.user.inWeb.reqAndResp.LoginRequest;
import daj.adapter.user.inWeb.reqAndResp.RegisterRequest;
import daj.adapter.user.inWeb.reqAndResp.RegisterResponse;
import daj.adapter.user.inWeb.reqAndResp.UserAddRequest;
import daj.adapter.user.inWeb.reqAndResp.UserAddResponse;
import daj.adapter.user.inWeb.reqAndResp.UserPictureUploadResponse;
import daj.adapter.user.inWeb.reqAndResp.UserResponse;
import daj.adapter.user.outDB.entity.UserEntity;
import daj.adapter.user.outDB.entity.UserPictureEntity;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserPictureDto;
import daj.user.visible.port.dto.UserRoleDto;
import jakarta.validation.Valid;

@Mapper
public interface IUserMapper {

  //@Mapping(source="id", target="idUser")
  UserDto entityToDto(UserEntity source);
  default List<UserDto> entitiesToDtos(List<UserEntity> source) {
    final var target = new ArrayList<UserDto>();
    source.forEach(entity -> {
      final var dto = this.entityToDto(entity);
      target.add(dto);
    });
    return target;
  }

  UserEntity dtoToEntity(UserDto source);

  UserDto loginRequestToDto(LoginRequest input);

  UserDto registerRequestToDto(@Valid RegisterRequest input);

  RegisterResponse dtoToRegisterResponse(UserDto registered);

  UserResponse dtoToResponse(UserDto input);

  default List<UserResponse> listDtoToResponse(List<UserDto> content) {
    final List<UserResponse> casted = new ArrayList<UserResponse>();
    for (UserDto dto : content) {
      final UserResponse resp = this.dtoToResponse(dto);
      final List<UserRoleDto> newRoles = resp.getRoles().stream()
        .map(r -> new UserRoleDto(r.getId(), r.getAuthority().replace("ROLE_", ""))).toList();
      resp.setRoles(newRoles);  
      casted.add(resp);
    }
    
    return casted;
  }

  UserDto requestSaveToDto(UserAddRequest entity);
  
  
  UserAddResponse dtoToAddedResponse(UserDto entity);
  

  UserPictureUploadResponse userPictureDtoToUserPictureUploadResponse(UserPictureDto saved);


  UserPictureEntity userPictureDtoToUserPictureEntity(UserPictureDto requestDto);
  default UserEntity map(Integer value) {
    final UserEntity user = new UserEntity();
    user.setId(value);
    return user;
  }

  UserPictureDto userPictureEntityToPictureDto(UserPictureEntity saved);
  default Integer map(UserEntity value) {
    return value.getId();
  }

}
