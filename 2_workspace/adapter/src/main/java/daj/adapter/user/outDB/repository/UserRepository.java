package daj.adapter.user.outDB.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import daj.adapter.user.outDB.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

  UserEntity findByUsername(String username);

  //Page<UserEntity> findAllByOrderByIdAsc(Pageable pageable);

  Page<UserEntity> findAll(Pageable pageable);

  @Query("SELECT COUNT(e) FROM UserEntity e WHERE e.deletedAt IS NULL")
  Integer totalUsers();

  List<UserEntity> findByIdIn(List<Integer> ids, Pageable pageable);
  
  @Query(
    nativeQuery = true,
    value = "SELECT u.id FROM users u " + 
    "LEFT JOIN users_roles ur ON u.id = ur.user_id " +
    "LEFT JOIN roles r ON ur.role_id = r.id " +
    "WHERE CAST(u.id AS TEXT) LIKE CONCAT('%', :overall, '%') "+
    "OR CAST(u.created_at AS TEXT) LIKE CONCAT('%', :overall, '%') "+
    "OR CAST(u.updated_at AS TEXT) LIKE CONCAT('%', :overall, '%') "+
    "OR u.username LIKE CONCAT('%', :overall, '%') "+
    "OR u.email LIKE CONCAT('%', :overall, '%') "+
    "OR r.authority LIKE CONCAT('%', :overall, '%') "+
    "GROUP BY u.id, u.username"
  )
  List<Integer> overallQuery(@Param("overall") String overall);

}
