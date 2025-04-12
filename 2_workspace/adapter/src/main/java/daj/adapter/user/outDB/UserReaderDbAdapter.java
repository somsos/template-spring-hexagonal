package daj.adapter.user.outDB;


import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import daj.adapter.user.outDB.entity.UserEntity;
import daj.adapter.user.outDB.entity.UserPictureEntity;
import daj.adapter.user.outDB.repository.UserPictureRepository;
import daj.adapter.user.outDB.repository.UserRepository;
import daj.adapter.user.utils.IUserMapper;
import daj.common.error.ErrorResponse;
import daj.common.types.AppPage;
import daj.common.types.PageAndFilterRequestDto;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserPictureDto;
import daj.user.visible.port.out.IUserReaderOutputPort;
import lombok.AllArgsConstructor;

@Primary
@Component
@AllArgsConstructor
public class UserReaderDbAdapter implements IUserReaderOutputPort {

  private final UserRepository repo;

  private final UserPictureRepository repoPictures;

  private final IUserMapper mapper;

  @Override
  public UserDto findByUsername(String username) {
    final UserEntity found = repo.findByUsername(username);
  
    final UserDto foundMapped = mapper.entityToDto(found);
    return foundMapped;
  }

  @Override
  public UserDto findById(Integer userId) {
    final UserEntity found = repo.findById(userId).orElse(null);
    
    final UserDto foundMapped = mapper.entityToDto(found);
    return foundMapped;
  }

  @Override
  public AppPage<UserDto> findByPage(PageAndFilterRequestDto reqDto) {
    Page<UserEntity> pageFound = null;
    int totalItems = -1;
    if(reqDto.overAllQuery.isEmpty()) {
      pageFound = repo.findAll(reqDto.page);
      totalItems = repo.totalUsers();
    } else {
      List<Integer> idsFound = this.repo.overallQuery(reqDto.overAllQuery);
      List<UserEntity> usersFound = this.repo.findByIdIn(idsFound, reqDto.page);

      totalItems = idsFound.size();

      pageFound = new PageImpl<UserEntity>(usersFound);
    }
    final var contentMapped = mapper.entitiesToDtos(pageFound.getContent());
    final var output = new AppPage<UserDto>(
      contentMapped, totalItems, reqDto.page.getPageNumber(), reqDto.page.getPageSize()
    );
    return output;
  }
  
  @Override
  public UserDto save(UserDto reqDto) {
    final UserEntity entity = mapper.dtoToEntity(reqDto);
    final UserEntity saved = repo.save(entity);
    final UserDto savedMapped = mapper.entityToDto(saved);
    return savedMapped;
  }

  @Override
  public UserPictureDto saveImage(UserPictureDto requestDto) {
    final UserEntity entity = repo.findById(requestDto.getIdUser()).orElse(null);
    if (entity == null) {
      throw new ErrorResponse("user id not found", 400, "");
    }

    final UserPictureEntity toSave = mapper.userPictureDtoToUserPictureEntity(requestDto);
    final UserPictureEntity saved = this.repoPictures.save(toSave);
    
    final UserPictureDto savedMapped = mapper.userPictureEntityToPictureDto(saved);
    return savedMapped;
  }

  @Override
  public UserPictureDto findImageByUserId(Integer idUser) {
    final UserPictureEntity pictureFound = this.repoPictures.findByIdUser(idUser);
    if(pictureFound == null) {
      return null;
    }
    final UserPictureDto response = mapper.userPictureEntityToPictureDto(pictureFound);
    return response;
  }

  @Override
  public UserDto getUserById(Integer idUser) {
    final UserEntity found = repo.findById(idUser).orElse(null);
    if(found == null) {
      throw new ErrorResponse("User not found", 404, "");
    }
  
    final UserDto foundMapped = mapper.entityToDto(found);
    return foundMapped; 
  }

  @Override
  public UserDto update(Integer idUser, UserDto newInfo) {
    final UserEntity oldInfo = repo.findById(idUser).orElse(null);
    if(oldInfo == null) {
      throw new ErrorResponse("User not found", 404, "");
    }
    this.mapper.updateUserFromDto(newInfo, oldInfo);
    oldInfo.setId(idUser);
    final UserEntity updated = repo.save(oldInfo);
    final UserDto response = mapper.entityToDto(updated);
    return response; 
  }

  
}
