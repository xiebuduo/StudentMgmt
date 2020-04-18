package com.xie.program.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xie.program.entity.User;
import com.xie.program.page.Page;
import com.xie.program.service.UserService;

/**
 * 用户控制器
 * 
 * @author Xiebuduo
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	public UserService userService;

	// 用户首页
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("user/list");
		return model;
	}

	/**
	 * 获取用户列表
	 * @param username
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/get_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			Page page
			) {
		Map<String, Object> retn = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", "%"+username+"%");
		queryMap.put("offset", page.getOffSet());
		queryMap.put("pageSize", page.getRows());

		retn.put("rows", userService.findList(queryMap));
		retn.put("total", userService.total(queryMap));
		return retn;
	}

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user) {
		Map<String, String> retn = new HashMap<String, String>();
		if (user == null) {
			retn.put("type", "error");
			retn.put("msg", "数据绑定错误！请联系开发者！");
			return retn;
		}

		// 用户名为空
		if (StringUtils.isEmpty(user)) {
			retn.put("type", "error");
			retn.put("msg", "用户名不能为空！");
			return retn;
		}
		// 用户是否存在
		User existsUSer = userService.findByUserName(user.getUsername());
		if (existsUSer != null) {
			retn.put("type", "error");
			retn.put("msg", "该用户已存在！");
			return retn;
		}

		if (userService.addUser(user) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "添加失败！");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "添加成功！");
		return retn;
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> edit(User user){
		Map<String,String> retn=new HashMap<String, String>();
		if(user == null) {
			retn.put("type", "error");
			retn.put("msg", "数据错误！");
			return retn;
		}
		
		//用户名为空
		if("".equals(user.getUsername())) {
			retn.put("type", "error");
			retn.put("msg", "用户名不能为空！");
			return retn;
		}
		//密码为空
		if("".equals(user.getPassword())) {
			retn.put("type", "error");
			retn.put("msg", "密码不能为空！");
			return retn;
		}
		//用户名存在
		User existsUser = userService.findByUserName(user.getUsername());
		if(existsUser != null) {
			if(user.getId() != existsUser.getId()) {
				retn.put("type", "error");
				retn.put("msg", "该用户已存在！");
				return retn;
			}
		}
		
		//修改
		if(userService.editUser(user) <= 0) {
			if(user.getId() != existsUser.getId()) {
				retn.put("type", "error");
				retn.put("msg", "修改失败");
				return retn;
			}
		}
		retn.put("type", "success");
		retn.put("msg", "修改成功！");
		return retn;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> delete(
				@RequestParam(value="ids[]",required=true) Long[] ids
			){
		Map<String,String> retn=new HashMap<String, String>();
		if(ids == null) {
			retn.put("type","error");
			retn.put("msg","请选择要删除的数据！");
			return retn;
		}
		StringBuilder idString = new StringBuilder();
		for(Long id:ids) {
			idString.append(id+",");
		}
		idString = idString.deleteCharAt(idString.length()-1);
		if(userService.delUser(idString.toString()) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "删除失败！");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "删除成功！");
		return retn;
	}
}
