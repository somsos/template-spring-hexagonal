package daj.adapter.user.outDB;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import daj.adapter.user.outDB.entity.RoleEntity;
import daj.adapter.user.outDB.repository.RoleRepository;
import daj.adapter.user.outDB.repository.UserRepository;
import daj.adapter.user.utils.IUserMapper;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserRoleDto;
import daj.user.visible.port.out.IUserWriterOutputPort;
import lombok.AllArgsConstructor;

@Primary
@Component
@AllArgsConstructor
public class UserWriterDbAdapter implements IUserWriterOutputPort {

  private final UserRepository repo;

  private final RoleRepository roleRepo;

  private final IUserMapper mapper;

  @Override
  public UserDto register(UserDto input, List<UserRoleDto> roles) {
    final var toSave = mapper.dtoToEntity(input);
    final var validRoles = getValidRoles(roles);
    toSave.setRoles(validRoles);
    final var saved = repo.save(toSave);
    
    final var output = mapper.entityToDto(saved);
    return output;
    
  }

  private List<RoleEntity> getValidRoles(List<UserRoleDto> rolesToAddToUser) {
    final List<Integer> ids = rolesToAddToUser.stream().map(r -> r.getId()).toList();
    final List<RoleEntity> validRoles = (List<RoleEntity>) this.roleRepo.findAllById(ids);
    return validRoles;
  }

  
}
