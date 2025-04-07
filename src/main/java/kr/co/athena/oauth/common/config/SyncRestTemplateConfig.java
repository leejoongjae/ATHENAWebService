package kr.co.athena.oauth.common.config;

import java.nio.charset.Charset;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SyncRestTemplateConfig{
	/**
	 * RestTemplate 설정 
	 */
	@Bean(name = "syncRestTemplate")
	public RestTemplate getSimpleRestTemplate() {
		
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		
		factory.setReadTimeout(300000);
		factory.setConnectTimeout(300000);
		
		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(100)
				.setMaxConnPerRoute(5)
				.build();
		
		factory.setHttpClient(httpClient);
		factory.setReadTimeout(300000);
		
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		
		return restTemplate;
	}
	

}
