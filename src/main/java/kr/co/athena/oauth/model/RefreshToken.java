package kr.co.athena.oauth.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * RefreshToken VO
 * @author ljj
 *
 */
@Alias("refreshToken")
@Getter
@Setter
@ToString
public class RefreshToken implements Serializable {

	private static final long serialVersionUID = -1548638781636479909L;
	
	private String msg;
	private String refreshToken;
	private long refreshExpires;
	
}
