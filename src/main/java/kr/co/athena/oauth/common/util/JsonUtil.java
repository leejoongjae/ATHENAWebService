package kr.co.athena.oauth.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


public class JsonUtil {

	private static final Log log = LogFactory.getLog(JsonUtil.class);

	/**
	 * Json String을 Object 형태로 변환 하여 돌려 준다.
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> getJsonObject(String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
        try {
            //convert JSON string to Map
        	result = mapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}

	/**
	 * 일반 인스턴스를 Json 형태로 변경해서 반환한다.
	 * @param obj json으로 변경하고자 하는 인스턴스
	 * @return
	 */
	public static String getJsonString(Object obj) {
		return getJsonString(obj, null);
	}

	/**
	 * 일반 인스턴스를 Json 형태로 변경해서 반환한다.
	 * JsonConfig를 이용해서 출력하고자 하는 맴버를 필터링 할 수 있다.
	 * <pre>
	 * JsonConfig jsonConfig = new JsonConfig();
	 *   jsonConfig.setRootClass( BeanA.class );
	 *   jsonConfig.setJavaPropertyFilter( new PropertyFilter(){
	 *     public boolean apply( Object source, String name, Object value ) {
	 *       if( "bool".equals( name ) || "integer".equals( name ) ){
	 *         return true;
	 *       }
	 *       return false;
	 *     }
	 *   });
	 * </pre>
	 * @param obj json으로 변경하고자 하는 인스턴스
	 * @return
	 */
	public static String getJsonString(Object obj, JsonConfig jsonConfig) {
		String json = "";
		if (obj == null)
			return null;

		if (obj instanceof List<?>) {
			json = (jsonConfig != null) ? JSONArray.fromObject(obj, jsonConfig).toString() : JSONArray.fromObject(obj).toString();
		} else {
			json = (jsonConfig != null) ? JSONObject.fromObject(obj, jsonConfig).toString() : JSONObject.fromObject(obj).toString();
		}

		if(log.isDebugEnabled())
			log.debug("JSON String : " + obj + "->" + json);
		return json;
	}

	/**
	 * Response에서 writer를 구해서 문자열을 출력한다.
	 * @param response 응답 인스턴스
	 * @param string 브라우져로 보내고자 하는 문자열
	 */
	public static void responseWrite(HttpServletResponse response, String string) {

		 response.setHeader("Pragma", "no-cache");
		 response.setHeader("Expires", "0");
		 response.setHeader("Cache-Control", "no-cache");
		 response.setContentType("application/json;charset=utf-8");

		try {
			PrintWriter writer = response.getWriter();
			writer.print(string);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
