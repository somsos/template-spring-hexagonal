package daj.adapter.user.outDB;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import daj.adapter.user.outDB.entity.UserEntity;
import daj.adapter.user.outDB.repository.UserRepository;
import daj.adapter.user.utils.IUserMapper;
import daj.common.types.AppPage;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.out.IUserReaderOutputPort;
import lombok.AllArgsConstructor;

@Primary
@Component
@AllArgsConstructor
public class UserReaderDbAdapter implements IUserReaderOutputPort {

  private final UserRepository repo;

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
  public AppPage<UserDto> findByPage(PageRequest pageReq) {
    final var found = repo.findAll(pageReq);
    final var contentMapped = mapper.entitiesToDtos(found.getContent());
    final int totalItems = repo.totalUsers();
    final var output = new AppPage<UserDto>(
      contentMapped, totalItems, pageReq.getPageNumber(), pageReq.getPageSize()
    );
    return output;
  }
  
}
