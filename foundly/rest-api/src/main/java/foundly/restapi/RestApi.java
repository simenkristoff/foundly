package foundly.restapi;

import foundly.restapi.service.FileStorageService;
import javax.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * The Class RestApi. The main-class for the rest-api services.
 */
@SpringBootApplication
@EntityScan(basePackages = "foundly.core.model")
public class RestApi {

  public static void main(String[] args) {
    SpringApplication.run(RestApi.class, args);
  }

  @Resource
  FileStorageService storageService;

  /**
   * Runner-method for the rest-api.
   *
   * @param arg the arg
   * @throws Exception the exception
   */
  public void run(String... arg) throws Exception {
    storageService.init();
  }

}
