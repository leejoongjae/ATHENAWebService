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
	
	
	private String encryptYn = "N";
	
	@RequestMapping(method = RequestMethod.POST, path = "/getToken/{companyCd}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> getToken(@PathVariable(value="companyCd") String companyCd, 
			@RequestHeader(value="apikey") String apiKey, @RequestHeader(value="apisecret") String apisecret ) throws Exception {
		
		AES256Util aes256Util = new AES256Util();
		
		Oauth vo = new Oauth();
		vo.setCompanyCd(companyCd);
		
		Oauth oauthVo = oauthService.getOauthCmpnInfo(vo);
		String jsonTxt = "";
		String rtnJson = "";
		if(oauthVo != null && oauthVo.getApiKey() != null && !oauthVo.getApiKey().equals("")) {
			String token = JwtProvider.generateTokenAthena(900, oauthVo.getApiKey(), oauthVo.getApiSecret(), companyCd);
			oauthVo.setToken(token);
			jsonTxt = JsonUtil.getJsonString(oauthVo);
		}else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", "조회된 데이타가 없습니다.");
			jsonTxt = JsonUtil.getJsonString(jsonObject);
			
		}
		
		rtnJson = jsonTxt;
		if(encryptYn.equals("Y")) {
			rtnJson = aes256Util.aesEncode(jsonTxt);
		}
		
		return new ResponseEntity<String> (rtnJson, HttpStatus.OK);
	}
		
}
