package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
class SystemConfigRepositoryTest {

  @Autowired
  private SystemConfigRepository systemConfigRepository;

  private SystemConfigEntity systemConfigEntity;

  @BeforeEach
  void setUp() {
    systemConfigEntity = SystemConfigEntity.builder().id(4).configName(Configurations.EMAIL_CONTACT)
        .configValue("servicioalcliente@cit.co.cr").build();
  }

  @Test
  void saveExistingSystemConfig() {
    try {
      SystemConfigEntity savedSystemConfig = systemConfigRepository.save(
          new SystemConfigEntity(null, Configurations.EMAIL_CONTACT, "ejemplo@cit.co.cr"));

      assertNotNull(savedSystemConfig);
      assertNotNull(savedSystemConfig.getId());
    } catch (Exception e) {
      assertEquals("org.springframework.dao.DataIntegrityViolationException",
          e.getClass().getName());
    }
  }

  @Test
  void updateSystemConfig() {
    SystemConfigEntity savedSystemConfig =
        systemConfigRepository.findByConfigName(systemConfigEntity.getConfigName()).orElse(null);

    savedSystemConfig.setConfigValue(systemConfigEntity.getConfigValue());
    SystemConfigEntity updatedSystemConfig = systemConfigRepository.save(savedSystemConfig);

    assertNotNull(updatedSystemConfig);
    assertNotNull(updatedSystemConfig.getId());
    assertEquals(systemConfigEntity.getConfigName(), updatedSystemConfig.getConfigName());
  }

  @Test
  void findSystemConfig() {
    SystemConfigEntity foundSystemConfig = systemConfigRepository.findById(4).orElse(null);

    assertNotNull(foundSystemConfig);
    assertEquals(systemConfigEntity.getId(), foundSystemConfig.getId());
    assertEquals(systemConfigEntity.getConfigName(), foundSystemConfig.getConfigName());
  }

  @Test
  void findSystemProcessWeights() {
    List<SystemConfigEntity> processWeights = systemConfigRepository.getProcessWeights();

    assertNotNull(processWeights);
    assertFalse(processWeights.isEmpty());
    assertEquals(2, processWeights.size());
  }

  @Test
  void findSystemContactInfo() {
    List<SystemConfigEntity> contactInfo = systemConfigRepository.getContactInfo();

    assertNotNull(contactInfo);
    assertFalse(contactInfo.isEmpty());
    assertEquals(8, contactInfo.size());
  }
}
