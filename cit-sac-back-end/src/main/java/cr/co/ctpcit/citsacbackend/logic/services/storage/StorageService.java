package cr.co.ctpcit.citsacbackend.logic.services.storage;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
public interface StorageService {
  void init();

  CompletableFuture<DocumentDto> store(MultipartFile file, String filename, DocType docType);

  Stream<Path> loadAll();

  Resource loadAsResource(Long id);

  void deleteAll();

  boolean deleteDocument(String filename);
}
