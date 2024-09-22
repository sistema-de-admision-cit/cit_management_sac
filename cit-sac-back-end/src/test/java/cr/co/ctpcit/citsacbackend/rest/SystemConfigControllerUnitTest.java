package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.data.entities.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.SystemConfigMapper;
import cr.co.ctpcit.citsacbackend.logic.services.SystemConfigServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SystemConfigControllerUnitTest {

  @Mock
  private SystemConfigRepository systemConfigRepository;

  @InjectMocks
  private SystemConfigServiceImplementation systemConfigService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addSystemConfigTest() {
    SystemConfigDto systemConfigDto = new SystemConfigDto(null, "examenDai", "40");
    SystemConfigEntity systemConfigEntity = SystemConfigMapper.toEntity(systemConfigDto);
    systemConfigEntity.setId(1);

    when(systemConfigRepository.save(any(SystemConfigEntity.class))).thenReturn(systemConfigEntity);


    SystemConfigDto result = systemConfigService.addSystemConfig(systemConfigDto);

    assertNotNull(result);
    assertEquals("examenDai", result.configName());
    assertEquals("40", result.configValue());
    assertEquals(1, result.id());

    verify(systemConfigRepository, times(1)).save(any(SystemConfigEntity.class));
  }
}
