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
public class Oauth extends Page implements Serializable {

	private static final long serialVersionUID = -1548638781636479909L;
	
	private String companyCd;
	private String apiKey;
	private String apiSecret;
	private String token;
	private String useYn;
	private String regDttm;
	
}
