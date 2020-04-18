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
 * 系统主页控制
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

	// 主页
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model) {
		model.setViewName("system/index");
		return model;
	}

	// 登录页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.setViewName("login");
		return model;
	}

	// 表单提交
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "vcode", required = true) String vcode,
			@RequestParam(value = "typed", required = true) int typed, HttpServletRequest request) {
		Map<String, String> retn = new HashMap<String, String>();

		// 用户名为空
		if (StringUtils.isEmpty(userid)) {
			retn.put("type", "error");
			retn.put("msg", "学号不能为空！");
			return retn;
		}

		// 密码为空
		if (StringUtils.isEmpty(password)) {
			retn.put("type", "error");
			retn.put("msg", "密码不能为空！");
			return retn;
		}

		// 验证码为空
		if (StringUtils.isEmpty(vcode)) {
			retn.put("type", "error");
			retn.put("msg", "验证码不能为空！");
			return retn;
		}
		// 会话是否过期
		String loginCpacha = (String) request.getSession().getAttribute("loginCpacha");
		if (StringUtils.isEmpty(loginCpacha)) {
			retn.put("type", "error");
			retn.put("msg", "您等待的时间过长！请刷新后重试！");
			return retn;
		}
		// 验证码错误
		if (!vcode.equalsIgnoreCase(loginCpacha)) {
			retn.put("type", "error");
			retn.put("msg", "验证码错误！");
			return retn;
		}

		// 清除验证码session
		request.getSession().setAttribute("loginCpacha", null);

		// 验证用户是否存在
		// 管理员
		if (typed == 1) {
			User user = userService.findByUserName(userid);
			if (user == null) {
				retn.put("type", "error");
				retn.put("msg", "用户" + userid + "不存在！");
				return retn;
			}

			// 用户名和密码是否正确
			if (!user.getPassword().equals(password)) {
				retn.put("type", "error");
				retn.put("msg", "密码错误！");
				return retn;
			}
			// 登录成功！
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("userType", 1);
		}
		if (typed == 2) {
			Student student = studentService.findStu(userid);
			if (student == null) {
				retn.put("type", "error");
				retn.put("msg", "该用户不存在，请检查后重试！");
				return retn;
			}
			if (!student.getPassword().equals(password)) {
				retn.put("type", "error");
				retn.put("msg", "密码错误！");
				return retn;
			}
			request.getSession().setAttribute("user", student);
			request.getSession().setAttribute("userType", 2);
		}
		retn.put("type", "success");
		retn.put("msg", "登录成功！");
		return retn;
	}
	/**
	 * 退出
	 */

	@RequestMapping(value = "log_out")
	public String logOut(HttpServletRequest request) {
		request.getSession().setAttribute("user",null);
		return "redirect:index";
	}
	// 验证码模块
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
