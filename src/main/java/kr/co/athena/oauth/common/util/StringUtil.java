package kr.co.athena.oauth.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.rmi.server.UID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * String Utilities
 */
public class StringUtil {


	/**
	 * String.substring(int start, int end) 대체
	 * NullPointException 방지
	 */
	public static String substring(String src, int start, int end){
		if(src == null || "".equals(src) || start > src.length() || start > end || start < 0) return "";
		if(end > src.length()) end = src.length();

		return src.substring(start, end);
	}

	/**
	 *	파라미터 스트링이 null 이 아니고, "" 이 아니면 true, 아니면 false
	 *
	 *	@param param		검사 문자열
	 *
	 *	@return 검사결과
	 */
	public static boolean isNotNull(String param){
		if(param != null && "".equals(param) == false) return true;
		else return false;
	}


	/**
	 * 캐릭터셋 엔코딩
	 * @param value
	 * @param encoding
	 * @return
	 */
	public static String encodingCharset(String value, String encoding) {
		try {
			if(value != null) {
				String[] params = encoding.split("\\|");
				if (params.length < 2) {
					return value;
				}
				String charSet1 = params[0];
				String charSet2 = params[1];

				if(!charSet1.equalsIgnoreCase(charSet2)) {
					value = new String(value.getBytes(charSet1), charSet2);
				}
			}
		} catch(UnsupportedEncodingException e) {
			return value;
		}
		return value;
	}


	/**
	 *	스트링 치환 함수
	 *
	 *	주어진 문자열(buffer)에서 특정문자열('src')를 찾아 특정문자열('dst')로 치환
	 *
	 */
	public static String ReplaceAll(String buffer, String src, String dst){
		if(buffer == null) return null;
		if(buffer.indexOf(src) < 0) return buffer;

		int bufLen = buffer.length();
		int srcLen = src.length();
		StringBuffer result = new StringBuffer();

		int i = 0;
		int j = 0;
		for(; i < bufLen; ){
			j = buffer.indexOf(src, j);
			if(j >= 0) {
				result.append(buffer.substring(i, j));
				result.append(dst);

				j += srcLen;
				i = j;
			}else break;
		}
		result.append(buffer.substring(i));
		return result.toString();
	}

	/**
	 *	파라미터 스트링이 null or "" 이면 true, 아니면 false
	 *
	 *	@param param		검사 문자열
	 *
	 *	@return 검사결과
	 */
	public static boolean isNull(String param){
		if(param == null || "".equals(param) ) return true;
		else return false;
	}

	/**
	 * 문자열을 Form의 Input Text에 삽입할때 문자 변환
	 * @param str
	 * @return
	 */
    public static String	fn_html_text_convert (String str) {
		if (str == null || str.equals("")) {
			return	"";
		}
		else {
			char	schr1			=	'\'';
			char	schr2			=	'\"';
			char	schr3			=	'<';
			char	schr4			=	'>';
			StringBuffer	sb		=	new StringBuffer(str);
			int		idx_str			=	0;
			int		edx_str			=	0;

			while (idx_str >= 0) {
				idx_str				=	str.indexOf(schr1, edx_str);
				if (idx_str < 0) {
					break;
				}
				str					=	sb.replace(idx_str, idx_str+1, "&#39;").toString();
				edx_str				=	idx_str + 5;
			}

			sb						=	new StringBuffer(str);
			idx_str					=	0;
			edx_str					=	0;
			while (idx_str >= 0) {
				idx_str				=	str.indexOf(schr2, edx_str);
				if (idx_str < 0) {
					break;
				}
				str					=	sb.replace(idx_str, idx_str+1, "&quot;").toString();
				edx_str				=	idx_str + 6;
			}

			sb						=	new StringBuffer(str);
			idx_str					=	0;
			edx_str					=	0;
			while (idx_str >= 0) {
				idx_str				=	str.indexOf(schr3, edx_str);
				if (idx_str < 0) {
					break;
				}
				str					=	sb.replace(idx_str, idx_str+1, "&lt;").toString();
				edx_str				=	idx_str + 4;
			}
			sb						=	new StringBuffer(str);
			idx_str					=	0;
			edx_str					=	0;
			while (idx_str >= 0) {
				idx_str				=	str.indexOf(schr4, edx_str);
				if (idx_str < 0) {
					break;
				}
				str					=	sb.replace(idx_str, idx_str+1, "&gt;").toString();
				edx_str				=	idx_str + 4;
			}

			return	str;
		}
	}
    /**
	 * 문자열을 Form의 Input Text에 삽입할때 문자 변환
	 * @param str
	 * @return
	 */
    public static String	fn_input_text (String str) {
		if (str == null || str.equals("")) {
			return	"";
		}
		else {
			char	schr1			=	'\'';
			char	schr2			=	'\"';
			StringBuffer	sb		=	new StringBuffer(str);
			int		idx_str			=	0;
			int		edx_str			=	0;

			while (idx_str >= 0) {
				idx_str				=	str.indexOf(schr1, edx_str);
				if (idx_str < 0) {
					break;
				}
				str					=	sb.replace(idx_str, idx_str+1, "&#39;").toString();
				edx_str				=	idx_str + 5;
			}

			sb						=	new StringBuffer(str);
			idx_str					=	0;
			edx_str					=	0;
			while (idx_str >= 0) {
				idx_str				=	str.indexOf(schr2, edx_str);
				if (idx_str < 0) {
					break;
				}
				str					=	sb.replace(idx_str, idx_str+1, "&quot;").toString();
				edx_str				=	idx_str + 6;
			}


			return	str;
		}
	}

    /**
     * null인 경우 ""를 return
     * @param value
     * @return
     */
	public static String nvl(String value) {
		return nvl(value, "");
	}

	/**
	 * value가 null인 경우 defalult값을 return
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String nvl(String value, String defaultValue) {
		if (value == null || value.equals("") || value.equals("null"))
			return defaultValue;
		else
			return value;
	}

	/**
	 * value가 null인 경우 defalult값을 return
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int nvl(String value, int defaultValue) {
		if (value == null || value.equals("") || value.equals("null"))
			return defaultValue;
		else
			return Integer.parseInt(value);
	}

    /**
     * null인 경우 ""를 return
     * @param value
     * @return
     */
	public static String nvl(Object value) {
		return nvl(value, "");
	}

	/**
	 * value가 null인 경우 defalult값을 return
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String nvl(Object value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		else {
			if (value.toString().equals(""))
				return defaultValue;
			else
				return value.toString();
		}

	}

	/**
	 * value가 null인 경우 defalult값을 return
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int nvl(Object value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		else {
			if (value.toString().equals(""))
				return defaultValue;
			else
				return Integer.parseInt(value.toString());
		}
	}

	/**
	 * Number 타입인지를 체크 한다.
	 * @param paramName
	 * @return
	 */
	public static boolean isNumber(String paramName) {
		paramName = nvl(paramName);
		try {
			Long.parseLong(paramName);
		} catch (Exception e) {
			return false;
		}

		return true;
	}


	/**
	 * Double 타입인지를 체크 한다.
	 * @param paramName
	 * @return
	 */
	public static boolean isDouble(String paramName) {
		paramName = nvl(paramName);
		try {
			Double.parseDouble(paramName);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 문자열을 HTML 포맷에 맞게 변형해준다.
	 * @param src
	 * @return
	 */
    public static String getHtmlContents(String src)
    {
        src = ReplaceAll(src, "\n", "<br>");
        src = ReplaceAll(src, "&quot;", "\"");
        return src;
    }


    /**
     * 문자열을 구분자로 나누어 배열로 리턴
     * @param value
     * @param regex
     * @return
     */
    public static String[] getStringArray(String value, String regex) {
    	String result[] = new String[0];

    	if (value != null && !value.equals("")) {
			result = value.split(regex);
		}

    	return result;
    }


    /**
     * 데이타를 구분자로 나누어 배열로 리턴
     * @param str
     * @param sepe_str
     * @return
     */
	public static String[] split(String str, String sepe_str) {
		int		index				=	0;
		String[] result				=	new String[search(str,sepe_str)+1];
		String	strCheck			=	new String(str);
		while (strCheck.length() != 0) {
			int		begin			=	strCheck.indexOf(sepe_str);
			if (begin == -1) {
				result[index]		=	strCheck;
				break;
			}
			else {
				int	end				=	begin + sepe_str.length();
				if(true) {
					result[index++]	=	strCheck.substring(0, begin);
				}
				strCheck			=	strCheck.substring(end);
				if(strCheck.length()==0 && true) {
					result[index]	=	strCheck;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 문자열에서 특정 문자열의 위치를 돌려준다.
	 * @param strTarget
	 * @param strSearch
	 * @return
	 */
	public static int search(String strTarget, String strSearch) {
		int		result				=	0;
		String	strCheck			=	new String(strTarget);
		for(int i = 0; i < strTarget.length();) {
			int		loc				=	strCheck.indexOf(strSearch);
			if(loc == -1) {
				break;
			}
			else {
				result++;
				i					=	loc + strSearch.length();
				strCheck			=	strCheck.substring(i);
			}
		}
		return result;
	}

	/**
	 * 인자값으로 받은 Integre 만큼  문자를 자른후 나머지 문자는 .. 으로 표시한다
	 * @param str
	 * @param maxLength
	 * @return
	 */
    public static String setMaxLength(String str, int maxLength) {
        if (str == null) {
            return    "";
        }
        if ( str.length() <= maxLength ) return str;
        if ( maxLength < 3 ) return str.substring(0, 2);

        StringBuffer returnString = new StringBuffer();
        str = str.trim();

        returnString.append(str.substring(0, maxLength-1)).append("..");

        return    returnString.toString();
    }

    /**
     * 문자열을 특정 문자열 만큼 잘라주고 뒤에 "..."을 붙여준다.
     * @param str
     * @param cut
     * @return
     */
	public static String cropByte(String str, int cut) {
        if (str == null) {
            return    "";
        }
        if ( str.length() <= cut ) return str;
        if ( cut < 3 ) return str.substring(0, 2);

		StringCharacterIterator iter = new StringCharacterIterator(str);
        int check = 0;
		int type = Character.getType(iter.last());
		if(type == Character.OTHER_SYMBOL)
		  cut --;
		else check++;

		if(check < 1){
		    // 재검사
			iter.setText(str.substring(0,cut));
			type = Character.getType(iter.last());
			if(type == Character.OTHER_SYMBOL)
			  cut += 2;
		}

        // 문자를 다시 잘라 리턴
	    return str.substring(0,cut)+"...";
	}

	/**
	 * 문자열 실제크기를 리턴한다.
	 * @param s
	 * @return
	*/
	public static int realLength(String s) {
	        return s.getBytes().length;
	}

	/**
	 * 파일 확장자를 리턴한다.
	 * @param szTemp
	 * @return
	*/
	public static String getExt(String szTemp)
	{
		if(szTemp == null) return "";

		String fname = "";
		if (szTemp.indexOf(".") != -1) {
			fname = szTemp.substring(szTemp.lastIndexOf("."));
			return fname;
		} else {
			return "";
		}
	}

	/**
	 * 파일명에서 확장자를 제외(.포함)하고 이름을 리턴한다.
	 * @param fileName
	 * @return
	 */
	public static String removeExt(String fileName) {
		return fileName.replaceAll(StringUtil.getExt(fileName), "");
	}

	/**
	 * 파일 확장자에서 점을 제외하고 반환한다.
	 * @param fileName
	 * @return
	 */
	public static String getExtNoneDot(String fileName) {
		if(fileName == null) return "";

		String fname = "";
		if (fileName.indexOf(".") != -1) {
			fname = fileName.substring(fileName.lastIndexOf(".")+1);
			return fname;
		} else {
			return "";
		}
	}

	/**
	 * 천단위 콤마 찍힌 숫자를 리턴한다.
	 * @param str
	 * @return
	 */
	public static String getMoneyType(int str)
	{
		NumberFormat nf = NumberFormat.getNumberInstance();
		String r_str = nf.format(str);
		return r_str;
	}

	/**
	 * 천단위 콤마 찍힌 숫자를 리턴한다.
	 * @param str
	 * @return
	 */
	public static String getMoneyType(String str)
	{
		NumberFormat nf = NumberFormat.getNumberInstance();
		String r_str = nf.format(nvl(str, 0));
		return r_str;
	}

	/**
	 * @param requestURI
	 * @return
	 */
	public static String getUrlFileName(String path) {
		return path.substring(path.lastIndexOf("/")+1, path.lastIndexOf("."));
	}


	/**
	 * URL Encoding
	 * @param url
	 * @return
	 */
	public static String getUrlEncode(String url) {
		if (url != null) {
			try {
				url = url.replaceAll(" ", "*20");
				url = URLEncoder.encode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			url = url.replace('%','*');
		}
		return url;
	}


	/**
	 * URL Decoding
	 * @param url
	 * @return
	 */
	public static String getUrlDecode(String url) {
		if (url != null) {
			url = url.replace('*','%');
			try {
				url = URLDecoder.decode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return url;
	}


	/**
	 * HTML 태그를 제거해준다.
	 * @param s
	 * @return
	 */
    public static String removeTag(String s) {
        return s.replaceAll("(?:<!.*?(?:--.*?--s*)*.*?>)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)","");
    }

	/**
	* 기능 : 문자열의 왼쪽에 대체문자를 해당 index의 갯수만큼 채워 리턴한다.
	* param    String str      변경할 문자열
	* param    int index       반복횟수
	* param    String addStr   반복될문자열
	* @return  String  오른쪽에 반복되어 채워진 문자열
	*/
	public static String fillLeft(String str, int index, String addStr) {
		int gap = 0;
		if ((str!=null) && (addStr!=null) && (str.length()<=index)) {
			gap = index - str.length();

			for(int i=0 ; i<gap ; i++) {
				str = addStr + str;
			}
			return str;
		}
		else {
			return "";
		}
	}


	/**
	 * 문자열에서 지정한 위치의 문자를 반환한다.
	 * @param value
	 * @param idx
	 * @return
	 */
	public static String getStringIndexValue(String value, int idx) {
		String result = "";

		if (value != null && value.length() >= idx) {
			result = value.substring(idx-1, idx);
		}

		return result;
	}

	/**
	 * 특정 문자열을 만들어 반환한다.
	 * @param lenth
	 * @return String
	 */
	public static String generateKeyString(int length) {
		String retVal = "";
		char[] initRandomChar = {'1','2','3','4','5','6','7','8','9','0','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

		SecureRandom rand = new SecureRandom();
		int i=0;
		while(i < length) {
			retVal += initRandomChar[(int)(rand.nextFloat() * initRandomChar.length)];
			i++;
		}

		return retVal;
	}

	/**
	 *
	 * @param
	 * @param char[] pattern
	 */
	public static boolean isPatternMatch(String value, String pattern) {
		Pattern pa = Pattern.compile(pattern);
		Matcher ma = pa.matcher(value);
		boolean ret = ma.matches();
		return 	ret;
	}


	/**
	 * 문자열중 HOST URL을 운영서버용 URL로 변경(INSERT, UPDATE용)
	 * @param string
	 * @return
	 */
	public static String replaceUrlToPersist(String source) {

		// "|' http(s)://uniedu나 local을 포함하고 /를 제외한 모든 문자열/
		// (['|"])(http[s]*://[[^/]+uniedu[^/]+|[^/]+local[^/]+]+[^/]+)(/)
		//Pattern pattern = Pattern.compile("(['|\"])(http[s]*://[[^/]+uniedu[^/]+|[^/]+local[^/]+]+[^/]+)(/)");

		// $1 은 시작되는 따옴표, $3은 종료자로 사용된 / 문자열
		//String result = pattern.matcher(StringUtil.nvl(source)).replaceAll("$1" + Constants.PRODUCT_HOST_URL + "$3");
		//return result;

//		if(Constants.PRODUCT_HOST_URL.equals(Constants.HOST_URL)) return source;
//		return StringUtil.nvl(source).replaceAll(Constants.HOST_URL, Constants.PRODUCT_HOST_URL);
		return source;
	}

	/**
	 * 문자열중 운영서버용 URL을 HOST URL로 변경(SELECT 용)
	 * @param string
	 * @return
	 */
	public static String replaceUrlToBrowser(String string) {
		string = StringUtil.nvl(string);
		// 바꿀 필요 없으면 그냥 리턴
//		if(Constants.PRODUCT_HOST_URL.equals(Constants.HOST_URL)) {
//			//-- 홈페이지인 경우
//			String adminUrl = Constants.PRODUCT_HOST_URL+":"+Constants.ADMIN_PORT;
//			if(string.contains(adminUrl)) {
//				string.replaceAll(adminUrl, Constants.PRODUCT_HOST_URL);
//			}
//			string = string.replaceAll(Constants.HOST_URL+"/app/", Constants.HOST_URL+"/lms/app/");
//		} else {
//			String homeUrl = Constants.PRODUCT_HOST_URL+"/";
//			if(string.contains(homeUrl)) {
//				string = string.replaceAll(homeUrl, Constants.HOST_URL+"/");
//			}
//			string = string.replaceAll(Constants.HOST_URL+"/lms/app/", Constants.HOST_URL+"/app/");
//		}
		return string;
	}

	/**
	 *
	 * @param
	 * @param char[] pattern
	 */
	public static String patternReplace(String value, String pattern) {
		Pattern pa = Pattern.compile(pattern);
		Matcher ma = pa.matcher(value);
		String retVal = ma.replaceAll("");

		return 	retVal;
	}

	public static String lpad(String source, int len, String str) {
		String retVal = source;
		int tmplen = len - retVal.length();

		for(int i=0; i < tmplen; i++) {
			retVal = str + retVal;
		}
		return retVal;
	}

	public static String rpad(String source, int len, String str) {
		String retVal = source;
		int tmplen = len - retVal.length();

		for(int i=0; i < tmplen; i++) {
			retVal = retVal + str;
		}
		return retVal;
	}

	//날짜를 받아서 요일 값을 리턴
	public static String yyyymmddReturnDayOfWeek(String yyyymmdd) {
		String retVal = "";
        //2004,8,7의 요일을 구합니다.
        int year = Integer.parseInt(yyyymmdd.substring(0,4));
        int month =  Integer.parseInt(yyyymmdd.substring(4,6));
        int day =  Integer.parseInt(yyyymmdd.substring(6,8));

        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        //1월에 해당하는 필드는 Calendar 클래스내에서 0값에 해당
        //그래서 1을하나뺌
        month--;
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        switch (weekday) {
            case Calendar.SUNDAY:
            	retVal = "일요일";
                break;
            case Calendar.MONDAY:
            	retVal = "월요일";
                break;
            case Calendar.TUESDAY:
            	retVal = "화요일";
                break;
            case Calendar.WEDNESDAY:
            	retVal = "수요일";
                break;
            case Calendar.THURSDAY:
            	retVal = "목요일";
                break;
            case Calendar.FRIDAY:
            	retVal = "금요일";
                break;
            case Calendar.SATURDAY:
            	retVal = "토요일";
                break;
        }

		return retVal;
	}

	public static String now_Date(){  // 현재 날짜 시간 구하는 함수
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHH24mmss", Locale.KOREA );
		Date currentTime = new Date ( );
		String dTime = formatter.format ( currentTime );
		dTime = dTime.substring(0, 14);
		return dTime;
	}

	public static String generateNewId(String param) {
		String newId = "";
		UID uid = new UID();
		if(!"".equals(param)) newId = param+"_";
		newId += (uid.toString().replace(":",generateKeyString(1)).replace("-", generateKeyString(1)));
		if(newId.length() > 30) newId = newId.substring(0,30);
		return newId;
	}
	
	/**
	 * 첫문자를 대문자로 변환
	 * @param string
	 * @return
	 */
	public static String upperCaseFirst(String src) {

		 char[] arr = src.toCharArray();
	     arr[0] = Character.toUpperCase(arr[0]);
	     return new String(arr);
	      
	}	
	
	/**
	   * SHA-512
	   * @param text
	   * @return
	   * @throws NoSuchAlgorithmException
	   */
	  public static String shaencrypt(String text) throws NoSuchAlgorithmException {
		    String retVal = "";
		   
		    StringBuffer sb = new StringBuffer();
		    MessageDigest md = MessageDigest.getInstance("SHA-512");
		 
		    md.update(text.getBytes());
		    byte[] msgb = md.digest();
		 
		    for (int i = 0; i < msgb.length; i++) {
		        byte temp = msgb[i];
		        String str = Integer.toHexString(temp & 0xFF);
		        while (str.length() < 2) {
		            str = "0" + str;
		        }
		        str = str.substring(str.length() - 2);
		        sb.append(str);
		    }
		   
		    retVal = sb.toString();
		   
		    return retVal;
		}
	

}
