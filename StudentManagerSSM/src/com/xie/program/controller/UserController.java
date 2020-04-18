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
 * �û�������
 * 
 * @author Xiebuduo
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	public UserService userService;

	// �û���ҳ
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("user/list");
		return model;
	}

	/**
	 * ��ȡ�û��б�
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
	 * ����û�
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user) {
		Map<String, String> retn = new HashMap<String, String>();
		if (user == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݰ󶨴�������ϵ�����ߣ�");
			return retn;
		}

		// �û���Ϊ��
		if (StringUtils.isEmpty(user)) {
			retn.put("type", "error");
			retn.put("msg", "�û�������Ϊ�գ�");
			return retn;
		}
		// �û��Ƿ����
		User existsUSer = userService.findByUserName(user.getUsername());
		if (existsUSer != null) {
			retn.put("type", "error");
			retn.put("msg", "���û��Ѵ��ڣ�");
			return retn;
		}

		if (userService.addUser(user) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "���ʧ�ܣ�");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "��ӳɹ���");
		return retn;
	}
	
	/**
	 * �޸��û�
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> edit(User user){
		Map<String,String> retn=new HashMap<String, String>();
		if(user == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ���");
			return retn;
		}
		
		//�û���Ϊ��
		if("".equals(user.getUsername())) {
			retn.put("type", "error");
			retn.put("msg", "�û�������Ϊ�գ�");
			return retn;
		}
		//����Ϊ��
		if("".equals(user.getPassword())) {
			retn.put("type", "error");
			retn.put("msg", "���벻��Ϊ�գ�");
			return retn;
		}
		//�û�������
		User existsUser = userService.findByUserName(user.getUsername());
		if(existsUser != null) {
			if(user.getId() != existsUser.getId()) {
				retn.put("type", "error");
				retn.put("msg", "���û��Ѵ��ڣ�");
				return retn;
			}
		}
		
		//�޸�
		if(userService.editUser(user) <= 0) {
			if(user.getId() != existsUser.getId()) {
				retn.put("type", "error");
				retn.put("msg", "�޸�ʧ��");
				return retn;
			}
		}
		retn.put("type", "success");
		retn.put("msg", "�޸ĳɹ���");
		return retn;
	}
	
	/**
	 * ɾ��
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> delete(
				@RequestParam(value="ids[]",required=true) Long[] ids
			){
		Map<String,String> retn=new HashMap<String, String>();
		if(ids == null) {
			retn.put("type","error");
			retn.put("msg","��ѡ��Ҫɾ�������ݣ�");
			return retn;
		}
		StringBuilder idString = new StringBuilder();
		for(Long id:ids) {
			idString.append(id+",");
		}
		idString = idString.deleteCharAt(idString.length()-1);
		if(userService.delUser(idString.toString()) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "ɾ��ʧ�ܣ�");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "ɾ���ɹ���");
		return retn;
	}
}
