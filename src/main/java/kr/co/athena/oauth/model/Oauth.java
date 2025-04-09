package kr.co.athena.oauth.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Oauth VO
 * @author ljj
 *
 */
@Alias("oauth")
@Getter
@Setter
@ToString
public class Oauth implements Serializable {

	private static final long serialVersionUID = -1548638781636479909L;
	
	private String msg;
	private String companyCd;
	private String orgCode;
	private String clientId;
	private String clientSecret;
	private String userId;
	private String userNo;
	private String insDn;
	private String apiKey;
	private String apiSecret;
	private String token;
	private String accessToken;
	private String refreshToken;
	private String useYn;
	private String refreshYn;
	private String revokeYn;
	private String regDttm;
	private String modDttm;
	
	
}
