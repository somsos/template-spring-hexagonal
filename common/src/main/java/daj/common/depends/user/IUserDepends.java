package daj.common.depends.user;

public interface IUserDepends {

  UserMDto findByIdOrThrow(Integer id);
  
}
