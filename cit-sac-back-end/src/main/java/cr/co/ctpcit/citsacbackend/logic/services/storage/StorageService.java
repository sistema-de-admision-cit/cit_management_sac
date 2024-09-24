package cr.co.ctpcit.citsacbackend.logic.services.storage;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.DocumentDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
public interface StorageService {

  void init();

  void store(MultipartFile file);

  void store(MultipartFile file, String filename);

  Stream<Path> loadAll();

  Path load(String filename);

  Resource loadAsResource(String filename);

  void deleteAll();

  boolean deleteDocument(String filename);
}
