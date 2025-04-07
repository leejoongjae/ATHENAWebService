package kr.co.athena.oauth.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

	public static String AES_KEY;
	

	@Value("${aes.key}")
	public void setAES_KEY(String value) {
		AES_KEY = value;
	}
}
