package foundly.restapi.service;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class FileStorageServiceImpl. Implementation of the interface FileStorageService. This class
 * handles storing of files.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

  private final Path root = Paths.get("uploads");

  /**
   * Inits the storage directory in root. Throws an exception if the directory couldn't be created.
   */
  @Override
  public void init() {
    try {
      Files.createDirectory(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  /**
   * Saves a file to the storage directory. Throws an exception if the file couldn't be stored.
   *
   * @param file the file
   */
  @Override
  public void save(MultipartFile file) {
    try {
      try {
        Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
      } catch (FileAlreadyExistsException e) {
        System.out.println("File '" + file.getOriginalFilename() + "' already exists");
      }

    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

}
