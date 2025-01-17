package cr.co.ctpcit.citsacbackend.logic.services.storage;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {
  @Value("${storage.location}")
  private String location;

  @Override
  public void init() {
    try {
      Files.createDirectories(Paths.get(location));
    } catch (FileAlreadyExistsException e) {
      // Do nothing
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }

  @Async
  @Override
  public CompletableFuture<DocumentDto> store(MultipartFile file, String filename,
      DocType docType) {
    try {
      if (file != null) {
        if (Files.exists(Paths.get(location).resolve(filename))) {
          File existingFile = new File(location + filename);
          filename = existingFile.getName().substring(0, existingFile.getName()
              .lastIndexOf('.')) + "_" + System.currentTimeMillis() + filename.substring(
              filename.lastIndexOf('.'));
        }
        Files.copy(file.getInputStream(), Paths.get(location).resolve(filename));
      }
    } catch (IOException e) {
      throw new StorageException(
          "Failed to store file " + file.getOriginalFilename() + " with name " + filename, e);
    }
    return CompletableFuture.completedFuture(new DocumentDto(null, filename, docType));
  }

  @Override
  public Stream<Path> loadAll() {
    return Stream.empty();
  }

  @Override
  public Path load(String filename) {
    return null;
  }

  @Override
  public Resource loadAsResource(String filename) {
    return null;
  }

  @Override
  public void deleteAll() {
    try {
      Files.walk(Paths.get(location)).filter(Files::isRegularFile).map(Path::toFile)
          .forEach(File::delete);
    } catch (IOException e) {
      throw new StorageException("Failed to delete all files", e);
    }
  }

  @Override
  public boolean deleteDocument(String filename) {
    try {
      return Files.deleteIfExists(Paths.get(location).resolve(filename));
    } catch (IOException e) {
      throw new StorageException("Failed to delete file " + filename, e);
    }
  }
}
