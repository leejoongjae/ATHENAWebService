package kr.co.athena.oauth.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectUtil {
	
	public static Map<String, Object> convertObjectToMap(Object obj){
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(int i=0; i <fields.length; i++){
			fields[i].setAccessible(true);
			try{
				map.put(fields[i].getName(), fields[i].get(obj));
			}catch(Exception e){
				e.printStackTrace();
			}
		}        
		return map;
	}
	 
	public static Object convertMapToObject(Map<String,Object> map, Object obj){
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator<String> itr = map.keySet().iterator();
	        
		while(itr.hasNext()){
			keyAttribute = (String) itr.next();
            methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);            
            Method[] methods = obj.getClass().getDeclaredMethods();
            for(int i=0;i<methods.length;i++){
                if(methodString.equals(methods[i].getName())){
                    try{
                        methods[i].invoke(obj, map.get(keyAttribute));
                	}catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
		
        return obj;
    }
	
	public static Date stringToDate(String format, String date) throws Exception {
		String oldstring = date;
		return new SimpleDateFormat(format).parse(oldstring);
	}
	public static String dateToString(Date date,String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}

	public static String camelCaseToUnderscore(String name) {
	    StringBuffer result = new StringBuffer();
	    boolean begin = true;
	    boolean lastUppercase = false;
	    for( int i=0; i < name.length(); i++ ) {
	        char ch = name.charAt(i);
	        if( Character.isUpperCase(ch) ) {
	            if( begin ) {
	                result.append(ch);
	            } else {
	                if( lastUppercase ) {
	                    if( i+1<name.length() ) {
	                        char next = name.charAt(i+1);
	                        if( Character.isUpperCase(next) ) {
	                            result.append(ch);
	                        } else {
	                            result.append('_').append(ch);
	                        }
	                    } else {
	                        result.append(ch);
	                    }
	                } else {
	                    result.append('_').append(ch);
	                }
	            }
	            lastUppercase=true;
	        } else {
	            result.append(Character.toUpperCase(ch));
	            lastUppercase=false;
	        }
	        begin=false;
	    }
	    return result.toString();
	}

}
