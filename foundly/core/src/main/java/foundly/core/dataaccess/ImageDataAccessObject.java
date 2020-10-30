package foundly.core.dataaccess;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageDataAccessObject implements ImageDataAccess {

	private final String baseUrlString;

	@Autowired
	private ObjectMapper objectMapper;

	public ImageDataAccessObject(final String baseUrlString) {
		this.baseUrlString = baseUrlString + "/api/upload";
		this.objectMapper = new ObjectMapper();
	}

	protected ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	@Override
	public void upload(File file) {
		Resource resource = new FileSystemResource(file);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", resource);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(this.baseUrlString, requestEntity, String.class);
		System.out.println("Response code: " + response.getStatusCode());
	}

}
