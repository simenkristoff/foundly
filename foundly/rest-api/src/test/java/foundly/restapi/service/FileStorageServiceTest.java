package foundly.restapi.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import foundly.restapi.service.FileStorageService.FileHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * Tests for the Class FileStorageService.
 */
@ExtendWith(MockitoExtension.class)
public class FileStorageServiceTest {

  @Mock
  FileHandler fileHandler;

  @InjectMocks
  private FileStorageServiceImpl fileStorage;

  @Mock
  private MultipartFile imageTest;

  /**
   * Tests if the correct directory is created.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Testing creation of uploads-directory")
  public void createDirectoryTest() throws IOException {
    Path dir = Paths.get("uploads");
    when(fileHandler.createDirectory(any(Path.class))).thenReturn(dir);

    fileStorage.init();
    verify(fileHandler, times(1)).createDirectory(eq(dir));
  }

  /**
   * Test if Exceptions are caught during initialization of directory.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Testing fail during creation of directory")
  public void runtimeErrorOnCreateDirectory() throws IOException {
    when(fileHandler.createDirectory(any(Path.class))).thenThrow(IOException.class);

    assertThrows(RuntimeException.class, () -> {
      fileStorage.init();
    });
  }

  /**
   * Test if a upload is stored to the correct path.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Testing if files are stored properly")
  public void saveTest() throws IOException {
    File file = new File(getClass().getResource("defaultTest.png").getPath());
    imageTest = new MockMultipartFile("defaultTest.png", "defaultTest.png", "image/png",
        new FileInputStream(file));

    when(fileHandler.copy(any(InputStream.class), any(Path.class))).thenReturn(imageTest.getSize());

    assertTrue(fileStorage.save(imageTest));
    verify(fileHandler, times(1)).copy(any(InputStream.class),
        eq(Paths.get("uploads", file.getName())));
  }

  /**
   * File already exist exception test.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Testing exception if file already exists")
  public void fileAlreadyExistExceptionTest() throws IOException {
    File file = new File(getClass().getResource("defaultTest.png").getPath());
    imageTest = new MockMultipartFile("defaultTest.png", "defaultTest.png", "image/png",
        new FileInputStream(file));

    when(fileHandler.copy(any(InputStream.class), any(Path.class)))
        .thenThrow(FileAlreadyExistsException.class);

    assertFalse(fileStorage.save(imageTest));
  }

  /**
   * Runtime error on save test.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Testing runtime error during saving of file")
  public void runtimeErrorOnSaveTest() throws IOException {
    File file = new File(getClass().getResource("defaultTest.png").getPath());
    imageTest = new MockMultipartFile("defaultTest.png", "defaultTest.png", "image/png",
        new FileInputStream(file));

    when(fileHandler.copy(any(InputStream.class), any(Path.class))).thenThrow(IOException.class);

    assertThrows(RuntimeException.class, () -> {
      fileStorage.save(imageTest);
    });
  }
}