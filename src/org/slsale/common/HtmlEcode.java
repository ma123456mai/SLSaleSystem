package org.slsale.common;

/**
 * htmlEncode防止js注入
 * */
public class HtmlEcode {
	public static String htmlEncode(String string){
		if(null == string ||"".equals(string)){
			return null;
		}else{
			String result = string;
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll("\"", "&quot;");
			return (result.toString());
		}
	}
	
	public static String HtmlDecode(String string){
		if(null == string ||"".equals(string)){
			return null;
		}else{
			String result = string;
			result = result.replaceAll("&amp;", "&");
			result = result.replaceAll("&gt;", ">");
			result = result.replaceAll("&lt;", "<");
			result = result.replaceAll("&quot;", "\"");
			return (result.toString());
		}
	}
}
