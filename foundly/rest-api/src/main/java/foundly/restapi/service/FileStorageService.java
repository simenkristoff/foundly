package foundly.restapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Interface FileStorageService. Interface for handling storage of files.
 */
public interface FileStorageService {

  /**
   * Inits the file storage.
   */
  public void init();

  /**
   * Saves a file.
   *
   * @param file the file to be saved
   * @return true, if successful
   */
  public boolean save(MultipartFile file);


  /**
   * The Class FileHandler. Acts as a wrapper for the static methods in {@link Files}. This class is
   * written for ease of testing when using {@link Mockito}.
   */
  public class FileHandler {

    /**
     * Creates the directory for storing uploaded files.
     *
     * @param dir the dir
     * @return the path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Path createDirectory(Path dir) throws IOException {
      return Files.createDirectory(dir);
    }

    /**
     * Copy a file to the uploads folder.
     *
     * @param in the in
     * @param target the target
     * @return the long
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public long copy(InputStream in, Path target) throws IOException {
      return Files.copy(in, target);
    }
  }

}