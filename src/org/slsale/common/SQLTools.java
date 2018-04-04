package org.slsale.common;
/**
 * mybatis防止sql注入工具类
 * 
 * mybatis 模糊查询时可能会被sql注入
 * 为了防止此类情况，我们需要进行字符的替换
 * */
public class SQLTools {
	public static String tarnfer(String keyword){
		if(keyword.contains("%") || keyword.contains("_")){
			keyword = keyword.replaceAll("\\\\", "\\\\\\\\")
							.replaceAll("\\%", "\\\\%")
							.replaceAll("\\_", "\\\\_");
		}
		return keyword;
	}
}
