package foundly.ui.dataaccess;

import java.io.File;

/**
 * The Interface ImageDataAccess. Handles requests to rest-api for uploading images.
 */
public interface ImageDataAccess {

  /**
   * Upload.
   *
   * @param file the file
   */
  void upload(File file);

}
