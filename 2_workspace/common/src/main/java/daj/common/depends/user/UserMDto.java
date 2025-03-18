package daj.common.depends.user;

import java.util.List;

public class UserMDto { 

  final private Integer id;

  final private List<String> roles;

  public UserMDto(Integer id, List<String> roles) {
    this.id = id;
    this.roles = roles;
  }

  public Integer getId() {
    return id;
  }

  public List<String> getRoles() {
    return roles;
  }  
  
}
