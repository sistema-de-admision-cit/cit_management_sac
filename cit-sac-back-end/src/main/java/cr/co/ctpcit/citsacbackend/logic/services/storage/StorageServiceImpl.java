package cr.co.ctpcit.citsacbackend.logic.services.storage;

import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

  @Override
  public void store(MultipartFile file, String filename) {
    try {
      if (file.isEmpty()) {
        throw new StorageException(
            "Failed to store empty file " + file.getOriginalFilename() + " with name " + filename);
      }
      Files.copy(file.getInputStream(), Paths.get(location).resolve(filename));
    } catch (IOException e) {
      throw new StorageException(
          "Failed to store file " + file.getOriginalFilename() + " with name " + filename, e);
    }
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

  }

  @Override
  public boolean deleteDocument(String filename) {
    return false;
  }
}
