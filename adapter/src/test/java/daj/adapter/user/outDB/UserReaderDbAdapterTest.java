package daj.adapter.user.outDB;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import daj.adapter.user.outDB.repository.UserRepository;
import daj.adapter.user.utils.UserUtilBeans;
import daj.user.visible.port.dto.UserDto;
import jakarta.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({UserReaderDbAdapter.class, UserUtilBeans.class})
@ActiveProfiles("test")
public class UserReaderDbAdapterTest {
  
  @Autowired
  private DataSource dataSource;
  
  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  @Autowired
  private EntityManager entityManager;
  
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserReaderDbAdapter userReaderDao;

  @Test
  void injectedComponentsAreNotNull(){
    assertNotNull(dataSource);
    assertNotNull(jdbcTemplate);
    assertNotNull(entityManager);
    assertNotNull(userRepository);
    assertNotNull(userReaderDao);
  }

  @Test
  @Sql("classpath:sql-tests/zzz_test_createUserWithRoles.sql")
  void check_start_import_script() {
    final UserDto found = userReaderDao.findById(-100);

    assertNotNull(found);
    assertEquals(2, found.getRoles().size());


    final UserDto found2 = userReaderDao.findById(-99);
    assertEquals(1, found2.getRoles().size());
  }


}
