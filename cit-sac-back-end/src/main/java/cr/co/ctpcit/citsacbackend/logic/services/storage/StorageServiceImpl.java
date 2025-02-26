package cr.co.ctpcit.citsacbackend.logic.services.storage;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.DocumentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageException;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageFileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {
  @Value("${storage.location}")
  private String location;

  private DocumentRepository documentRepository;

  @Autowired
  public StorageServiceImpl(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

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
        Files.copy(file.getInputStream(), Paths.get(location).resolve(filename));
      }
    } catch (IOException e) {
      throw new StorageException(
          "Failed to store file " + file.getOriginalFilename() + " with name " + filename, e);
    }

    return CompletableFuture.completedFuture(new DocumentDto(null, filename, docType, null));
  }

  @Override
  public Stream<Path> loadAll() {
    return Stream.empty();
  }

  @Override
  public Resource loadAsResource(Long id) {
    DocumentEntity documentEntity = documentRepository.findById(id).orElse(null);

    if (documentEntity == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
    }

    try {
      Path file = Paths.get(documentEntity.getDocumentUrl());
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(
            "Could not read file with id: " + documentEntity.getId());

      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException(
          "Could not read file with id: " + documentEntity.getId(), e);
    }
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
