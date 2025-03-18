package daj.adapter.user.outDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import daj.adapter.user.outDB.entity.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

  UserEntity findByUsername(String username);

  //Page<UserEntity> findAllByOrderByIdAsc(Pageable pageable);

  Page<UserEntity> findAll(Pageable pageable);

  @Query("SELECT COUNT(e) FROM UserEntity e WHERE e.deletedAt IS NULL")
  Integer totalUsers();
  
}
