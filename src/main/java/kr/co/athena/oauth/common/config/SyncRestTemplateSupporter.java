package kr.co.athena.oauth.common.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SyncRestTemplateSupporter {
	
	@Autowired
	@Qualifier("syncRestTemplate")
	private RestTemplate syncRestTemplate;

	public <T> ResponseEntity<T> exchange(String authToken, SyncApiUrl apiUrl, URI uri, Object requestBody,
			ParameterizedTypeReference<T> responseType) throws Exception {

		SyncRestTemplateConfig syncRestTemplateConfig = new SyncRestTemplateConfig();
		
		RequestEntity<Object> request = RequestEntity.method(apiUrl.getMethod(), uri)
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
				.body(requestBody);
		
		return syncRestTemplateConfig.getSimpleRestTemplate().exchange(request, responseType);
	}

	public SyncRestTemplateSupporter() {
		super();
	}

}
