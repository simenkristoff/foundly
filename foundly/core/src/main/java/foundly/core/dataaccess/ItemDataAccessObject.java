package foundly.core.dataaccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import foundly.core.model.Item;

public class ItemDataAccessObject implements ItemDataAccess {

	private final String baseUrlString;

	private RestTemplate restTemplate;

	public ItemDataAccessObject(final String baseUrlString) {
		this.baseUrlString = baseUrlString + "/api/items";
		this.restTemplate = new RestTemplate();
	}

	protected RestTemplate getRestTemplate() {
		return restTemplate;
	}

	private String getRequestUrl(final String path) {
		return baseUrlString + path;
	}

	public List<Item> getAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Item[]> response = getRestTemplate().getForEntity(getRequestUrl(""), Item[].class);

		System.out.println(response.getStatusCode());

		if (response.getStatusCode() == HttpStatus.OK) {
            try {
                return Arrays.asList(response.getBody());
            } catch (NullPointerException e) {
                System.out.println("Error while fetching all items from rest-Server");
                return null;
            }
			
		}
		
		return null;
	}

	public Item get(long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Item> response = getRestTemplate().getForEntity(getRequestUrl("/" + id), Item.class);

		System.out.println(response.getStatusCode());

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}
		return null;
	}

	public void update(long id, Item item) {
		HttpHeaders headers = new HttpHeaders();
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("id", id);
		headers.setContentType(MediaType.APPLICATION_JSON);
		getRestTemplate().put(getRequestUrl("/" + id), item, params);
	}

	public void delete(long id) {
		HttpHeaders headers = new HttpHeaders();
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("id", id);
		headers.setContentType(MediaType.APPLICATION_JSON);
		getRestTemplate().delete(getRequestUrl("/" + id), params);
	}

	public boolean insert(Item item) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<Item> entity = new HttpEntity<>(item, headers);
		ResponseEntity<String> response = this.getRestTemplate().postForEntity(this.getRequestUrl(""), entity,
				String.class);

		System.out.println(response.getStatusCode());

		if (response.getStatusCode() == HttpStatus.OK) {
			return true;
		}
		return false;
	}

}
