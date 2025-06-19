package daj.adapter.user.outDB.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import daj.adapter.user.outDB.entity.UserPictureEntity;


@Repository
public interface UserPictureRepository extends JpaRepository<UserPictureEntity, Integer>{

  UserPictureEntity findByIdUser(Integer idUser);

}
