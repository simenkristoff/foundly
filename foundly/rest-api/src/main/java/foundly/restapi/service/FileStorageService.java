package foundly.restapi.service;

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
   */
  public void save(MultipartFile file);

}
