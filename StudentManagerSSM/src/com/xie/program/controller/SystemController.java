package com.xie.program.controller;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xie.program.entity.Student;
import com.xie.program.entity.User;
import com.xie.program.service.StudentService;
import com.xie.program.service.UserService;
import com.xie.program.utils.CpachaUtil;

/**
 * ϵͳ��ҳ����
 * 
 * @author Xiebuduo
 *
 */
@RequestMapping("/system")
@Controller
public class SystemController {

	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;

	// ��ҳ
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model) {
		model.setViewName("system/index");
		return model;
	}

	// ��¼ҳ��
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.setViewName("login");
		return model;
	}

	// ���ύ
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "vcode", required = true) String vcode,
			@RequestParam(value = "typed", required = true) int typed, HttpServletRequest request) {
		Map<String, String> retn = new HashMap<String, String>();

		// �û���Ϊ��
		if (StringUtils.isEmpty(userid)) {
			retn.put("type", "error");
			retn.put("msg", "ѧ�Ų���Ϊ�գ�");
			return retn;
		}

		// ����Ϊ��
		if (StringUtils.isEmpty(password)) {
			retn.put("type", "error");
			retn.put("msg", "���벻��Ϊ�գ�");
			return retn;
		}

		// ��֤��Ϊ��
		if (StringUtils.isEmpty(vcode)) {
			retn.put("type", "error");
			retn.put("msg", "��֤�벻��Ϊ�գ�");
			return retn;
		}
		// �Ự�Ƿ����
		String loginCpacha = (String) request.getSession().getAttribute("loginCpacha");
		if (StringUtils.isEmpty(loginCpacha)) {
			retn.put("type", "error");
			retn.put("msg", "���ȴ���ʱ���������ˢ�º����ԣ�");
			return retn;
		}
		// ��֤�����
		if (!vcode.equalsIgnoreCase(loginCpacha)) {
			retn.put("type", "error");
			retn.put("msg", "��֤�����");
			return retn;
		}

		// �����֤��session
		request.getSession().setAttribute("loginCpacha", null);

		// ��֤�û��Ƿ����
		// ����Ա
		if (typed == 1) {
			User user = userService.findByUserName(userid);
			if (user == null) {
				retn.put("type", "error");
				retn.put("msg", "�û�" + userid + "�����ڣ�");
				return retn;
			}

			// �û����������Ƿ���ȷ
			if (!user.getPassword().equals(password)) {
				retn.put("type", "error");
				retn.put("msg", "�������");
				return retn;
			}
			// ��¼�ɹ���
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("userType", 1);
		}
		if (typed == 2) {
			Student student = studentService.findStu(userid);
			if (student == null) {
				retn.put("type", "error");
				retn.put("msg", "���û������ڣ���������ԣ�");
				return retn;
			}
			if (!student.getPassword().equals(password)) {
				retn.put("type", "error");
				retn.put("msg", "�������");
				return retn;
			}
			request.getSession().setAttribute("user", student);
			request.getSession().setAttribute("userType", 2);
		}
		retn.put("type", "success");
		retn.put("msg", "��¼�ɹ���");
		return retn;
	}
	/**
	 * �˳�
	 */

	@RequestMapping(value = "log_out")
	public String logOut(HttpServletRequest request) {
		request.getSession().setAttribute("user",null);
		return "redirect:index";
	}
	// ��֤��ģ��
	@RequestMapping(value = "/get_cpacha", method = RequestMethod.GET)
	public void getCpacha(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "count", defaultValue = "4", required = false) Integer count,
			@RequestParam(value = "w", defaultValue = "98", required = false) Integer w,
			@RequestParam(value = "h", defaultValue = "40", required = false) Integer h) {
		CpachaUtil cpachaUtil = new CpachaUtil(count, w, h);
		String generatorCode = cpachaUtil.generatorVCode();
		request.getSession().setAttribute("loginCpacha", generatorCode);
		BufferedImage bufferedImage = cpachaUtil.generatorRotateVCodeImage(generatorCode, true);
		try {
			ImageIO.write(bufferedImage, "gif", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
