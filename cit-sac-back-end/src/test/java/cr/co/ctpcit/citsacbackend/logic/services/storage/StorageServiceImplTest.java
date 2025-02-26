package cr.co.ctpcit.citsacbackend.logic.services.storage;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageServiceImplTest {

  @InjectMocks
  private StorageServiceImpl storageService;

  @Mock
  private MultipartFile file;

  @Value("${storage.location}")
  private String location = "D:\\temp\\spring\\uploads";

  private final String filename = "test-file.txt";
  private final DocType docType = DocType.AC;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(storageService, "location", location);
  }

  @Test
  void shouldStoreFileSuccessfully() throws Exception {
    // Arrange
    Path filePath = Paths.get(location).resolve(filename);
    when(file.getInputStream()).thenReturn(mock(InputStream.class));

    // Act
    CompletableFuture<DocumentDto> result = storageService.store(file, filename, docType);


    // Assert
    assertNotNull(result);
    assertEquals(filename, result.get().documentUrlPostfix());
    assertEquals(docType, result.get().documentType());
  }

  @Test
  void shouldThrowExceptionWhenIOExceptionOccurs() throws Exception {
    // Arrange
    when(file.getInputStream()).thenThrow(new IOException("Simulated IOException"));
    when(file.getOriginalFilename()).thenReturn(filename);

    // Act & Assert
    StorageException thrown = assertThrows(StorageException.class, () ->
        storageService.store(file, filename, docType).join()
    );

    assertTrue(thrown.getMessage().contains("Failed to store file"));
  }

}
