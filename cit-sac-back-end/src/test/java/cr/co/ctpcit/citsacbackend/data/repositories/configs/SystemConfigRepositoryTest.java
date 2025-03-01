package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
class SystemConfigRepositoryTest {
  @Autowired
  private SystemConfigRepository systemConfigRepository;

  SystemConfigEntity systemConfigEntity;

  @BeforeEach
  void setUp() {
    systemConfigEntity = SystemConfigEntity.builder()
        .configName("")
        .build();
  }

}
