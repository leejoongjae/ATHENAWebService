package kr.co.athena.oauth.common.config;

import org.springframework.http.HttpMethod;

import kr.co.athena.oauth.common.Constants;


public interface SyncApiUrl {
	public HttpMethod getMethod();

	public String getUrl();

	public String getLmsLoginLogUrl();
	
}
