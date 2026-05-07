package daj.adapter.user.outDB;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import daj.adapter.user.outDB.repository.UserRepository;
import daj.adapter.user.utils.UserUtilBeans;

@DataJpaTest
@Import({UserWriterDbAdapter.class, UserUtilBeans.class})
@ActiveProfiles("test")
public class UserWriterDbAdapterTest_H2 {

  @Autowired
  UserWriterDbAdapter userOP;

  @Autowired
  private UserRepository repo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  @Sql("classpath:sql-tests/zzz_test_createUser.sql")
  void testDelete_mustJustMarkTheUserAsDeleted() {
    var mustExist = repo.findById(1).orElse(null);
    assertEquals(1, mustExist.getId());
    repo.deleteById(1);

    var mustExistMarkedAsDeleted = jdbcTemplate.queryForObject("select id from users;", Integer.class);
    assertEquals(Integer.valueOf(1), mustExistMarkedAsDeleted);

    var mustBeNull = repo.findById(1).orElse(null);
    assertEquals(null, mustBeNull);
  }

}
