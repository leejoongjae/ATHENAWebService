package kr.co.athena.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.athena.oauth.common.config.JwtProvider;
import kr.co.athena.oauth.common.util.AES256Util;
import kr.co.athena.oauth.common.util.JsonUtil;
import kr.co.athena.oauth.model.AccessToken;
import kr.co.athena.oauth.model.Oauth;
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
		if(apiKey == null || !apiKey.equals(oauthVo.getApiKey())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "API KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else if(apisecret == null || !apisecret.equals(oauthVo.getApiSecret())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "API SECRET KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else {
			if(oauthVo != null && oauthVo.getApiKey() != null && !oauthVo.getApiKey().equals("")) {
				oauthService.editOauthRevoke(vo);
				String token = JwtProvider.generateTokenAthena(ACCESS_TOKEN_EXPIRE, oauthVo.getApiKey(), oauthVo.getApiSecret(), companyCd);
				tokenVo.setAccessToken(token);
				tokenVo.setExpires(ACCESS_TOKEN_EXPIRE);
				String refreshToken = JwtProvider.generateRefreshTokenAthena(REFRESH_TOKEN_EXPIRE, oauthVo.getApiKey(), oauthVo.getApiSecret(), companyCd);
				tokenVo.setRefreshToken(refreshToken);
				tokenVo.setRefreshExpires(REFRESH_TOKEN_EXPIRE);
				tokenVo.setMsg("성공!");
				jsonTxt = JsonUtil.getJsonString(tokenVo);
				
				vo.setAccessToken(token);
				vo.setRefreshToken(refreshToken);
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
		if(apiKey == null || !apiKey.equals(oauthVo.getApiKey())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "API KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else if(apisecret == null || !apisecret.equals(oauthVo.getApiSecret())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "API SECRET KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else {
			
			Oauth tokenInfo = oauthService.getOauthTokenInfo(vo);
			if(tokenInfo != null && tokenInfo.getRefreshToken() != null && tokenInfo.getRefreshToken().equals(refreshToken)) {
				oauthService.editOauthUseN(vo);
				
				String token = JwtProvider.generateTokenAthena(ACCESS_TOKEN_EXPIRE, oauthVo.getApiKey(), oauthVo.getApiSecret(), companyCd);
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
		if(apiKey == null || !apiKey.equals(oauthVo.getApiKey())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "API KEY가 일치하지 않습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
		}else if(apisecret == null || !apisecret.equals(oauthVo.getApiSecret())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "API SECRET KEY가 일치하지 않습니다.");
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
