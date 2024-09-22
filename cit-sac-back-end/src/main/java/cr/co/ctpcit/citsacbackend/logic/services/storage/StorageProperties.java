package cr.co.ctpcit.citsacbackend.logic.services.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("storage")
public class StorageProperties {

  /**
   * Folder location for storing files as a directory named files located in resources directory of
   * the project
   */
  private String location = "src/main/resources/static/files";

}
