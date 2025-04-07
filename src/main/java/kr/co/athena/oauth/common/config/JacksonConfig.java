package kr.co.athena.oauth.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {
	
	// {@link HaksaSyncUnivController} syncHaksaLoginLog, syncLmsScore 에서 단일 데이터도 list형식으로 받을 수 있도록 아래 전역 설정 추가
	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		return new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	}

}
