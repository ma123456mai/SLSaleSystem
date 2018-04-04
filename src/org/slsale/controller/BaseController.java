package org.slsale.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slsale.common.Constant;
import org.slsale.pojo.User;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController {
	// 当前用户
	private User user;

	public User getUser() {
		if(null == this.user){
			HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			if(session != null){
				user = (User)session.getAttribute(Constant.SESSION_USER);
			}else{
				user = null;
			}
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
/**
 * 日期格式化
 * */
	@InitBinder
	public void InitBinder(WebDataBinder webDataBinder){
		webDataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
			public void setAsText(String text){
				try {
					setValue(new SimpleDateFormat("yyyy-MM-dd").parse(text));
				} catch (Exception e) {
					e.getStackTrace();
					setValue(null);
				}
			}
			public String getAsText() {
				return new SimpleDateFormat("yyyy-MM-dd").format((Date)getValue());
			    }
		});
	}
	
}
