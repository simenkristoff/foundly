package foundly.restapi.service;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class FileStorageServiceImpl. Implementation of the interface {@link FileStorageService}.
 * This class handles storing of files. Uses a {@link FileHandler} as wrapper for handling files and
 * directories.
 * 
 * @see FileHandler
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

  private final Path root = Paths.get("uploads");

  private FileHandler fileHandler = new FileHandler();

  /**
   * Inits the storage directory in root. Throws an exception if the directory couldn't be created.
   */
  @Override
  public void init() {
    try {
      fileHandler.createDirectory(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  /**
   * Saves a file to the storage directory. Throws an exception if the file couldn't be stored.
   *
   * @param file the file
   * @return true, if successful
   */
  @Override
  public boolean save(MultipartFile file) {
    try {
      try {
        fileHandler.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        return true;
      } catch (FileAlreadyExistsException e) {
        System.out.println("File '" + file.getOriginalFilename() + "' already exists");
      }

    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
    return false;
  }
}