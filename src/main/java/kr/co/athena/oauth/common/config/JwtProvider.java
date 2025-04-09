package kr.co.athena.oauth.common.config;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import kr.co.athena.oauth.common.Constants;
import kr.co.athena.oauth.common.util.AES256Util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

	private final static SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
	
	private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	private static String JWT_API_KEY;
	private static String JWT_API_SECRET;
	private static int JWT_EXPIRE;
	

    
	
	@Value("${jwt.api.key}")
	public void setJWT_API_KEY(String value) {
		JWT_API_KEY = value;
	}
	@Value("${jwt.api.secret}")
	public void setJWT_API_SECRET(String value) {
		JWT_API_SECRET = value;
	}
	@Value("${jwt.api.expire}")
	public void setJWT_EXPIRE(int value) {
		JWT_EXPIRE = value;
	}
	
	
	
	public static String generateToken() throws Exception {

		Date expiration = new Date(System.currentTimeMillis() + (JWT_EXPIRE * 1000));

		String token = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam(JwsHeader.ALGORITHM, ALGORITHM.getJcaName())
				.setIssuer(JWT_API_KEY)
				.setExpiration(expiration)
				.signWith(ALGORITHM, JWT_API_SECRET.getBytes())
				.compact();

		return token;
	}
    
	public static String generateToken(int jwtExpire_sec) throws Exception {

		Date expiration = new Date(System.currentTimeMillis() + (jwtExpire_sec * 1000));

		String token = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam(JwsHeader.ALGORITHM, ALGORITHM.getJcaName())
				.setIssuer(JWT_API_KEY)
				.setExpiration(expiration)
				.signWith(ALGORITHM, JWT_API_SECRET.getBytes())
				.compact();

		return token;
	}
	
	public static String generateTokenUid(int jwtExpire_sec, String uid) throws Exception {

		Date expiration = new Date(System.currentTimeMillis() + (jwtExpire_sec * 1000));

		HashMap<String, Object> claimsMap = new HashMap<String, Object>();
		
		claimsMap.put("uId", uid);
		claimsMap.put("iss", JWT_API_KEY);
		
		String token = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam(JwsHeader.ALGORITHM, ALGORITHM.getJcaName())
				.setClaims(claimsMap)
				.setExpiration(expiration)
				.signWith(ALGORITHM, JWT_API_SECRET.getBytes())
				.compact();

		return token;
	}
	
	public static String generateTokenByKey(int jwtExpire_sec, String apiKey, String apiSecret) throws Exception {

		Date expiration = new Date(System.currentTimeMillis() + (jwtExpire_sec * 1000));

		String token = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam(JwsHeader.ALGORITHM, ALGORITHM.getJcaName())
				.setIssuer(apiKey)
				.setExpiration(expiration)
				.signWith(ALGORITHM, apiSecret.getBytes())
				.compact();

		return token;
	}
	
	public static String generateTokenAthena(long jwtExpire_sec, String apiKey, String apiSecret, String companyCd) throws Exception {

		Date expiration = new Date(System.currentTimeMillis() + (jwtExpire_sec * 1000));

		String token = Jwts.builder()
				.setSubject(companyCd)
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam(JwsHeader.ALGORITHM, ALGORITHM.getJcaName())
				.setIssuer(apiKey)
				.setExpiration(expiration)
				.signWith(ALGORITHM, apiSecret.getBytes())
				.compact();

		return token;
	}
	
    // ✅ 리프레시 토큰 생성
    public static String generateRefreshTokenAthena(long jwtExpire_sec, String apiKey, String apiSecret, String companyCd) throws Exception {
    	Date expiration = new Date(System.currentTimeMillis() + (jwtExpire_sec * 1000));
    	
    	String token = Jwts.builder()
				.setSubject(companyCd)
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam(JwsHeader.ALGORITHM, ALGORITHM.getJcaName())
				.setIssuer(apiKey)
				.setExpiration(expiration)
				.signWith(ALGORITHM, apiSecret.getBytes())
				.compact();

		return token;
    }

    // ✅ 토큰에서 사용자 이름 추출
    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ✅ 토큰 유효성 검증
    public static boolean validateToken(String token) {
    	Jws<Claims> claimsJws = null;
        try {
        	claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        	
        	Claims claims = claimsJws.getBody();
            System.out.println("Token is valid!");
            System.out.println("Username: " + claims.getSubject());
            System.out.println("Issued at: " + claims.getIssuedAt());
            System.out.println("Expiration: " + claims.getExpiration());
        	
            return true;
        } catch (JwtException e) {
        	System.out.println("Invalid Token: " + e.getMessage());
            return false;
        }
    }
    
    // Authorization client_id, client_secret 값 추출 
    public static String[] extractBasicAuthCredentials(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            // Authorization: Basic base64encoded(client_id:client_secret)
            String base64Credentials = authHeader.substring("Basic ".length());
            byte[] decodedBytes = Base64Utils.decodeFromString(base64Credentials);
            String decoded = new String(decodedBytes);
            String[] parts = decoded.split(":", 2); // [client_id, client_secret]
            return parts;
        }
        return null;
    }

	//해당 블록 실행하면 테스트 토큰 얻을수 있음
	public static void main(String[] args) {
		try {
			//System.out.println(generateToken(999999));
			//AES256Util aes256Util = new AES256Util();
			//System.out.println(aes256Util.aesDecode("cspxSNQdxXelHtp9oGP5jcpEdF7Tkpr6WxEqIfr9xlrlKy2g1iB0M++Wx5S/yvyJmBK4uCeQKy96H+dpZ6JqfbLHUXCDRuS3BogxYHtCYQMx/rB/xUrt1LONTAQBk2yPWLoQnk5luaJw9V4RJY/o2+yERQjMsKCVdhoVGvNtTm0JTzia5LudUlCXRl5J1o+iH79UNfnI6a/vB/BhWJ3dwFJZw4icwg/Cv7rzw7neHS/4yShWvwHIt8ur3lT1OTQSAGtwu3pn/MqilSiqR1U7edS29pEAw2y26rSNxpxWrsqpOIKNKqhGQ1GGkg6oWw3Cj0u3cGXjagj09+zud3qnbZdLQXpbIzVJ2x4ZPFvxR0DUaZxy55EYCylUjVEIT9F7"));
			
			/*String aa = "/haksa/univ/sync/getToken/CO00052";
			System.out.println(aa.substring(aa.lastIndexOf("/")+1));
			*/
			//AES 암호화 해제 처리 
			/*String iv = "";
			Key keySpec1 = null;
			String str = "cspxSNQdxXelHtp9oGP5jcpEdF7Tkpr6WxEqIfr9xlrlKy2g1iB0M++Wx5S/yvyJmBK4uCeQKy96H+dpZ6JqfbLHUXCDRuS3BogxYHtCYQMx/rB/xUrt1LONTAQBk2yPWLoQnk5luaJw9V4RJY/o2+yERQjMsKCVdhoVGvNtTm0JTzia5LudUlCXRl5J1o+iH79UNfnI6a/vB/BhWJ3dwFJZw4icwg/Cv7rzw7neHS/4yShWvwHIt8ur3lT1OTQSWSKxc1BKPvlj+hc7WxNcmzpQf1Rg1jh+I/6HNEq9KaYXr1s5JsL2RnU7r9Ugy5iHrwC8Jl4+OJp9HN/dekuLshSf06kwQlisV8F6tQPQ3T3EgZcRENpGiTxLMk6/Xlq+";
			try {
		        iv = "08f442d2b45b9ced21f28a1c".substring(0, 16);
		        
		        byte[] keyBytes = new byte[16];
		        byte[] b;
				b = "08f442d2b45b9ced21f28a1c".getBytes("UTF-8");
		        int len = b.length;
		        if (len > keyBytes.length) {
		            len = keyBytes.length;
		        }
		        System.arraycopy(b, 0, keyBytes, 0, len);
		        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		        
		        keySpec1 = keySpec;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        c.init(Cipher.DECRYPT_MODE, keySpec1, new IvParameterSpec(iv.getBytes("UTF-8")));
	 
	        byte[] byteStr = Base64.decodeBase64(str.getBytes());
	 
	        System.out.println( new String(c.doFinal(byteStr),"UTF-8"));
			*/
			
			//jjwt 라이브러리를 사용하여 Secret Key 생성 및 JWT 발급/검증을 구현할 수 있습니다. chatGPT 활용 방법
			//HMAC SHA256을 사용할 경우, Secret Key가 필요합니다.
			//아래 코드를 실행하면 Base64 인코딩된 Secret Key가 생성됩니다.
			/*SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

	        // Base64 인코딩하여 출력 (JWT 발급 시 사용 가능)
	        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
	        System.out.println("Generated Secret Key: " + encodedKey);*/
			
			System.out.println(JwtServerManager.generatorApiKey());
			System.out.println(JwtServerManager.generatorApiSecret());
			
			Date expiration = new Date(System.currentTimeMillis() + (999999 * 1000));

			String token = Jwts.builder()
					.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
					.setHeaderParam(JwsHeader.ALGORITHM, ALGORITHM.getJcaName())
					.setIssuer("da111cf0d159da0483545923")
					.setExpiration(expiration)
					.signWith(ALGORITHM, "40ac5331651970baed879c5cc78d50d8".getBytes())
					.compact();
			
			
			System.out.println(token);
			
			JwtServerManager jwtServerManager = new JwtServerManager();
			
			System.out.println(jwtServerManager.isValidToken("Bearer "+token, "xXPqcLdVzPsMu6LaHnapkaOibwvKPsBg", "2eS9hiqTXP1X9pMqwc6kMw=="));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
