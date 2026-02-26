package daj.adapter.user.outDB.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import daj.adapter.user.outDB.entity.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
  
}
