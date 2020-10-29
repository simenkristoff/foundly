package foundly.restapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages="foundly.core.model")
public class RestApi {

	public void run(String... arg) throws Exception {
		
	}
	
}
