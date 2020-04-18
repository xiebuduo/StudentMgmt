package com.xie.program.intercepter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xie.program.entity.User;

import net.sf.json.JSONObject;

public class LoginIntercepter implements HandlerInterceptor{

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		Object user=request.getSession().getAttribute("user");
//		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
//			Map<String,String> map=new HashMap<String, String>();
//			map.put("type", "error");
//			map.put("msg", "µÇÂ¼×´Ì¬Ê§Ð§£¡ÇëÖØÐÂµÇÂ¼£¡");
//			response.getWriter().write(JSONObject.fromObject(map).toString());
//			return false;
//		}
		if(user==null) {
			response.sendRedirect(request.getContextPath()+"/system/login");
			return false;
		}
		return true;
	}
	
}
