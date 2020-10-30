package foundly.restapi;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import foundly.restapi.service.FileStorageService;

@SpringBootApplication
@EntityScan(basePackages="foundly.core.model")
public class RestApi {

    @Resource
    FileStorageService storageService;

	public void run(String... arg) throws Exception {
		storageService.init();
	}
	
}
