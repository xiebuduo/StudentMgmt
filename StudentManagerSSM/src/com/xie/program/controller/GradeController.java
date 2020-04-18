package com.xie.program.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xie.program.entity.Grade;
import com.xie.program.page.Page;
import com.xie.program.service.GradeService;

/**
 * 年级控制器
 * 
 * @author Xiebuduo
 *
 */

@RequestMapping("/grade")
@Controller
public class GradeController {
	/**
	 * 用户首页
	 */
	@Autowired
	private GradeService gradeService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("grade/list");
		return model;
	}

	/**
	 * 获取年级列表
	 */

	@RequestMapping(value = "get_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(value = "name", required = false, defaultValue = "") String gradeName, Page page) {
		Map<String, Object> retn = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", "%" + gradeName + "%");
		queryMap.put("offset", page.getOffSet());
		queryMap.put("pageSize", page.getRows());

		retn.put("rows", gradeService.findList(queryMap));
		retn.put("total", gradeService.total(queryMap));
		return retn;
	}

	/**
	 * 添加年级信息
	 */

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Grade grade) {
		Map<String, String> retn = new HashMap<String, String>();
		if (grade == null) {
			retn.put("type", "error");
			retn.put("msg", "数据错误！请联系开发者！");
			return retn;
		}
		if (grade.getName() == null) {
			retn.put("type", "error");
			retn.put("msg", "年级名不能为空！");
			return retn;
		}
		if (gradeService.findByName(grade.getName()) != null) {
			retn.put("type", "error");
			retn.put("msg", "该年级已存在！");
			return retn;
		}
		if (gradeService.addGrade(grade) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "年级" + grade.getName() + "添加失败！");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "年级" + grade.getName() + "添加成功！");
		return retn;
	}

	/**
	 * 修改年级信息
	 */

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Grade grade) {
		Map<String, String> retn = new HashMap<String, String>();
		if (grade == null) {
			retn.put("type", "error");
			retn.put("msg", "数据错误！");
			return retn;
		}
		if (grade.getName() == null) {
			retn.put("type", "error");
			retn.put("msg", "年级名不能为空！");
			return retn;
		}
		Grade existsGrade = gradeService.findByName(grade.getName());
		if (existsGrade != null) {
			if (grade.getId() != existsGrade.getId()) {
				retn.put("type", "error");
				retn.put("msg", "该年级已存在！");
				return retn;
			}

		}
		if (gradeService.editGrade(grade) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "年级" + grade.getName() + "修改失败！");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "年级" + grade.getName() + "修改成功！");
		return retn;
	}

	/**
	 * 删除
	 */

	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> del(@RequestParam(value = "ids[]", required = true) Long[] ids) {
		Map<String, String> retn = new HashMap<String, String>();
		if (ids == null) {
			retn.put("type", "error");
			retn.put("msg", "请选择要删除的数据！");
			return retn;
		}
		StringBuilder idString = new StringBuilder();
		for (Long id : ids) {
			idString.append(id + ",");
		}
		idString = idString.deleteCharAt(idString.length() - 1);
		if (gradeService.delGrade(idString.toString()) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "删除失败");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "删除成功！");
		return retn;
	}
}
