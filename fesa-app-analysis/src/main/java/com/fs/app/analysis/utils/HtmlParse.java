package com.fs.app.analysis.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParse {

	public static String htmltotext(String html){
		String txtcontent = html.replaceAll("</?[^>]+>", ""); //剔出<html>的标签  
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符  
        return  txtcontent;
	}
	
	public static String delHTMLTag(String htmlStr){
		String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
        String regEx_textarea= "<textarea[^>]*?>[\\s\\S]*?<\\/textarea>";       //定义textarea标签的正则表达式（红色字为自己添加的内容，以下皆是）
        
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
        
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_textarea=Pattern.compile(regEx_textarea,Pattern.CASE_INSENSITIVE); 
        Matcher textarea=p_textarea.matcher(htmlStr); 
        htmlStr=textarea.replaceAll(""); //过滤textarea标签

         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 
        
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(htmlStr);
        htmlStr = m.replaceAll("");//去除空格回车制表符

        return htmlStr.trim(); //返回文本字符串 
	}
}
