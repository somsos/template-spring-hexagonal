package daj.adapter.user.outDB.repository;

import org.springframework.data.repository.CrudRepository;
import daj.adapter.user.outDB.entity.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
  
}
