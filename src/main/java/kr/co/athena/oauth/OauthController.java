package kr.co.athena.oauth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.athena.oauth.common.config.JwtProvider;
import kr.co.athena.oauth.common.util.AES256Util;
import kr.co.athena.oauth.common.util.JsonUtil;
import kr.co.athena.oauth.model.AccessToken;
import kr.co.athena.oauth.model.Oauth;
import kr.co.athena.oauth.model.RefreshToken;
import kr.co.athena.oauth.service.OauthService;
import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/oauth")
@CrossOrigin(value = "*")
public class OauthController {
	
	@Autowired
	private OauthService oauthService;
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final long ACCESS_TOKEN_EXPIRE = 1000 * 60 * 15; // 15분
    private final long REFRESH_TOKEN_EXPIRE = 1000 * 60 * 60 * 24 * 7; // 7일
	
	
	private String encryptYn = "N";
	
	@PostMapping(path = "/refresh", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> refresh( 
			HttpServletRequest request,
            @RequestParam Map<String, String> parameters
			) throws Exception {
		
		AES256Util aes256Util = new AES256Util();
		
		String[] credentials = JwtProvider.extractBasicAuthCredentials(request);
		 if (credentials == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
	    }
		
		String clientId = credentials[0];
		String clientSecret = credentials[1]; 
		 
		Oauth vo = new Oauth();
		vo.setOrgCode(parameters.get("org_code"));
		vo.setUserId(parameters.get("user_id"));
		vo.setUserNo(parameters.get("user_no"));
		vo.setInsDn(parameters.get("ins_dn"));
		
		Oauth oauthVo = oauthService.getOauthCmpnInfo(vo);
		RefreshToken tokenVo = new RefreshToken();
		String jsonTxt = "";
		String rtnJson = "";
		if(clientId == null || !clientId.equals(oauthVo.getClientId())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT ID가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else if(clientSecret == null || !clientSecret.equals(oauthVo.getClientSecret())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT SECRET KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else {
			if(oauthVo != null && oauthVo.getClientId() != null && !oauthVo.getClientId().equals("")) {
				oauthService.editOauthRevoke(vo);
				/*String token = JwtProvider.generateTokenAthena(ACCESS_TOKEN_EXPIRE, oauthVo.getClientId(), oauthVo.getClientSecret(), parameters.get("org_code"));
				tokenVo.setAccessToken(token);
				tokenVo.setExpires(ACCESS_TOKEN_EXPIRE);
				*/
				String refreshToken = JwtProvider.generateRefreshTokenAthena(REFRESH_TOKEN_EXPIRE, oauthVo.getClientId(), oauthVo.getClientSecret(), parameters.get("org_code"));
				tokenVo.setRefreshToken(refreshToken);
				tokenVo.setRefreshExpires(REFRESH_TOKEN_EXPIRE);
				tokenVo.setMsg("성공!");
				jsonTxt = JsonUtil.getJsonString(tokenVo);
				
				vo.setAccessToken("");
				vo.setRefreshToken(refreshToken);
				vo.setRefreshYn("Y");
				vo.setRevokeYn("N");
				
				oauthService.addOauth(vo);
			}else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("msg", "조회된 데이타가 없습니다.");
				jsonTxt = JsonUtil.getJsonString(jsonObject);
				
			}
		}
		
		rtnJson = jsonTxt;
		if(encryptYn.equals("Y")) {
			rtnJson = aes256Util.aesEncode(jsonTxt);
		}
		
		return new ResponseEntity<String> (rtnJson, HttpStatus.OK);
	}
	
		
	@PostMapping(path = "/token", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> token( 
			HttpServletRequest request,
            @RequestParam Map<String, String> parameters
			) throws Exception {
		
		AES256Util aes256Util = new AES256Util();
		
		String[] credentials = JwtProvider.extractBasicAuthCredentials(request);
		 if (credentials == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
	    }
		
		String clientId = credentials[0];
		String clientSecret = credentials[1]; 
		 
		Oauth vo = new Oauth();
		vo.setOrgCode(parameters.get("org_code"));
		vo.setUserId(parameters.get("user_id"));
		vo.setUserNo(parameters.get("user_no"));
		vo.setInsDn(parameters.get("ins_dn"));
		
		String paramRefreshToken = parameters.get("refresh_token");
		
		Oauth oauthVo = oauthService.getOauthCmpnInfo(vo);
		AccessToken tokenVo = new AccessToken();
		String jsonTxt = "";
		String rtnJson = "";
		if(clientId == null || !clientId.equals(oauthVo.getClientId())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT ID가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else if(clientSecret == null || !clientSecret.equals(oauthVo.getClientSecret())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT SECRET KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else {
			Oauth tokenInfo = oauthService.getOauthTokenInfo(vo);
			if(tokenInfo != null && tokenInfo.getRefreshToken() != null && tokenInfo.getRefreshToken().equals(paramRefreshToken)) {
				oauthService.editOauthUseN(vo);
				
				String token = JwtProvider.generateTokenAthena(ACCESS_TOKEN_EXPIRE, oauthVo.getClientId(), oauthVo.getClientSecret(), parameters.get("org_code"));
				tokenVo.setAccessToken(token);
				tokenVo.setExpires(ACCESS_TOKEN_EXPIRE);
				
				tokenVo.setMsg("성공!");
				jsonTxt = JsonUtil.getJsonString(tokenVo);
				
				vo.setAccessToken(token);
				vo.setRefreshToken(paramRefreshToken);
				vo.setRefreshYn("N");
				vo.setRevokeYn("N");
				
				oauthService.addOauth(vo);
				
				
			}else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("msg", "잘못된 REFRESH TOKEN 입니다.");
				jsonTxt = JsonUtil.getJsonString(jsonObject);
				
			}
		}
		
		rtnJson = jsonTxt;
		if(encryptYn.equals("Y")) {
			rtnJson = aes256Util.aesEncode(jsonTxt);
		}
		
		return new ResponseEntity<String> (rtnJson, HttpStatus.OK);
	}
	
	@PostMapping(path = "/validate", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> validate( 
			@RequestHeader(value="Authorization") String token
			) throws Exception {
		
		AES256Util aes256Util = new AES256Util();
		
		String jsonTxt = "";
		String rtnJson = "";
		if(token != null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT ID가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "잘못된 REFRESH TOKEN 입니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
			
		}
		
		rtnJson = jsonTxt;
		if(encryptYn.equals("Y")) {
			rtnJson = aes256Util.aesEncode(jsonTxt);
		}
		
		return new ResponseEntity<String> (rtnJson, HttpStatus.OK);
	}
	
	@PostMapping(path = "/revoke", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> revoke( 
			HttpServletRequest request,
            @RequestParam Map<String, String> parameters
			) throws Exception {
		
		AES256Util aes256Util = new AES256Util();
		
		String[] credentials = JwtProvider.extractBasicAuthCredentials(request);
		 if (credentials == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
	    }
		
		String clientId = credentials[0];
		String clientSecret = credentials[1]; 
		 
		Oauth vo = new Oauth();
		vo.setOrgCode(parameters.get("org_code"));
		vo.setUserId(parameters.get("user_id"));
		vo.setUserNo(parameters.get("user_no"));
		vo.setInsDn(parameters.get("ins_dn"));
		
		String paramRefreshToken = parameters.get("refresh_token");
		
		Oauth oauthVo = oauthService.getOauthCmpnInfo(vo);
		AccessToken tokenVo = new AccessToken();
		String jsonTxt = "";
		String rtnJson = "";
		if(clientId == null || !clientId.equals(oauthVo.getClientId())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT ID가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else if(clientSecret == null || !clientSecret.equals(oauthVo.getClientSecret())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT SECRET KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "성공!");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
			
			oauthService.editOauthRevoke(vo);
		}
		
		rtnJson = jsonTxt;
		if(encryptYn.equals("Y")) {
			rtnJson = aes256Util.aesEncode(jsonTxt);
		}
		
		return new ResponseEntity<String> (rtnJson, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/getToken/{companyCd}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> getToken(@PathVariable(value="companyCd") String companyCd, 
			@RequestHeader(value="apikey") String apiKey, @RequestHeader(value="apisecret") String apisecret ) throws Exception {
		
		AES256Util aes256Util = new AES256Util();
		
		Oauth vo = new Oauth();
		vo.setCompanyCd(companyCd);
		
		Oauth oauthVo = oauthService.getOauthCmpnInfo(vo);
		AccessToken tokenVo = new AccessToken();
		String jsonTxt = "";
		String rtnJson = "";
		if(apiKey == null || !apiKey.equals(oauthVo.getClientId())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT ID가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else if(apisecret == null || !apisecret.equals(oauthVo.getClientSecret())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT SECRET KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else {
			if(oauthVo != null && oauthVo.getClientId() != null && !oauthVo.getClientId().equals("")) {
				oauthService.editOauthRevoke(vo);
				String token = JwtProvider.generateTokenAthena(ACCESS_TOKEN_EXPIRE, oauthVo.getClientId(), oauthVo.getClientSecret(), companyCd);
				tokenVo.setAccessToken(token);
				tokenVo.setExpires(ACCESS_TOKEN_EXPIRE);
				tokenVo.setMsg("성공!");
				jsonTxt = JsonUtil.getJsonString(tokenVo);
				
				vo.setAccessToken(token);
				
				vo.setRefreshYn("N");
				vo.setRevokeYn("N");
				
				oauthService.addOauth(vo);
			}else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("msg", "조회된 데이타가 없습니다.");
				jsonTxt = JsonUtil.getJsonString(jsonObject);
				
			}
		}
		
		rtnJson = jsonTxt;
		if(encryptYn.equals("Y")) {
			rtnJson = aes256Util.aesEncode(jsonTxt);
		}
		
		return new ResponseEntity<String> (rtnJson, HttpStatus.OK);
	}
	
		
	@RequestMapping(method = RequestMethod.GET, path = "/refreshToken/{companyCd}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> refreshToken(@PathVariable(value="companyCd") String companyCd, 
			@RequestHeader(value="apikey") String apiKey, @RequestHeader(value="apisecret") String apisecret, @RequestHeader(value="refreshtoken") String refreshToken ) throws Exception {
		
		AES256Util aes256Util = new AES256Util();
		
		Oauth vo = new Oauth();
		vo.setCompanyCd(companyCd);
		
		Oauth oauthVo = oauthService.getOauthCmpnInfo(vo);
		AccessToken tokenVo = new AccessToken();
		String jsonTxt = "";
		String rtnJson = "";
		if(apiKey == null || !apiKey.equals(oauthVo.getClientId())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT ID가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else if(apisecret == null || !apisecret.equals(oauthVo.getClientSecret())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT SECRET KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else {
			
			Oauth tokenInfo = oauthService.getOauthTokenInfo(vo);
			if(tokenInfo != null && tokenInfo.getRefreshToken() != null && tokenInfo.getRefreshToken().equals(refreshToken)) {
				oauthService.editOauthUseN(vo);
				
				String token = JwtProvider.generateTokenAthena(ACCESS_TOKEN_EXPIRE, oauthVo.getClientId(), oauthVo.getClientSecret(), companyCd);
				tokenVo.setAccessToken(token);
				tokenVo.setExpires(ACCESS_TOKEN_EXPIRE);
				tokenVo.setMsg("성공!");
				
				jsonTxt = JsonUtil.getJsonString(tokenVo);
				
				vo.setAccessToken(token);
				vo.setRefreshToken(refreshToken);
				vo.setRefreshYn("Y");
				vo.setRevokeYn("N");
				
				oauthService.addOauth(vo);
				
				
			}else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("msg", "잘못된 REFRESH TOKEN 입니다.");
				jsonTxt = JsonUtil.getJsonString(jsonObject);
				
			}
		}
		
		rtnJson = jsonTxt;
		if(encryptYn.equals("Y")) {
			rtnJson = aes256Util.aesEncode(jsonTxt);
		}
		
		return new ResponseEntity<String> (rtnJson, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/revokeToken/{companyCd}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> revokeToken(@PathVariable(value="companyCd") String companyCd, 
			@RequestHeader(value="apikey") String apiKey, @RequestHeader(value="apisecret") String apisecret) throws Exception {
		
		AES256Util aes256Util = new AES256Util();
		
		Oauth vo = new Oauth();
		vo.setCompanyCd(companyCd);
		
		Oauth oauthVo = oauthService.getOauthCmpnInfo(vo);
		AccessToken tokenVo = new AccessToken();
		String jsonTxt = "";
		String rtnJson = "";
		if(apiKey == null || !apiKey.equals(oauthVo.getClientId())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT ID가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else if(apisecret == null || !apisecret.equals(oauthVo.getClientSecret())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "CLIENT SECRET KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "성공!");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
			
			oauthService.editOauthRevoke(vo);
		}
		
		rtnJson = jsonTxt;
		if(encryptYn.equals("Y")) {
			rtnJson = aes256Util.aesEncode(jsonTxt);
		}
		
		return new ResponseEntity<String> (rtnJson, HttpStatus.OK);
	}
		
}
