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
 * �꼶������
 * 
 * @author Xiebuduo
 *
 */

@RequestMapping("/grade")
@Controller
public class GradeController {
	/**
	 * �û���ҳ
	 */
	@Autowired
	private GradeService gradeService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("grade/list");
		return model;
	}

	/**
	 * ��ȡ�꼶�б�
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
	 * ����꼶��Ϣ
	 */

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Grade grade) {
		Map<String, String> retn = new HashMap<String, String>();
		if (grade == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ�������ϵ�����ߣ�");
			return retn;
		}
		if (grade.getName() == null) {
			retn.put("type", "error");
			retn.put("msg", "�꼶������Ϊ�գ�");
			return retn;
		}
		if (gradeService.findByName(grade.getName()) != null) {
			retn.put("type", "error");
			retn.put("msg", "���꼶�Ѵ��ڣ�");
			return retn;
		}
		if (gradeService.addGrade(grade) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "�꼶" + grade.getName() + "���ʧ�ܣ�");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "�꼶" + grade.getName() + "��ӳɹ���");
		return retn;
	}

	/**
	 * �޸��꼶��Ϣ
	 */

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Grade grade) {
		Map<String, String> retn = new HashMap<String, String>();
		if (grade == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ���");
			return retn;
		}
		if (grade.getName() == null) {
			retn.put("type", "error");
			retn.put("msg", "�꼶������Ϊ�գ�");
			return retn;
		}
		Grade existsGrade = gradeService.findByName(grade.getName());
		if (existsGrade != null) {
			if (grade.getId() != existsGrade.getId()) {
				retn.put("type", "error");
				retn.put("msg", "���꼶�Ѵ��ڣ�");
				return retn;
			}

		}
		if (gradeService.editGrade(grade) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "�꼶" + grade.getName() + "�޸�ʧ�ܣ�");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "�꼶" + grade.getName() + "�޸ĳɹ���");
		return retn;
	}

	/**
	 * ɾ��
	 */

	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> del(@RequestParam(value = "ids[]", required = true) Long[] ids) {
		Map<String, String> retn = new HashMap<String, String>();
		if (ids == null) {
			retn.put("type", "error");
			retn.put("msg", "��ѡ��Ҫɾ�������ݣ�");
			return retn;
		}
		StringBuilder idString = new StringBuilder();
		for (Long id : ids) {
			idString.append(id + ",");
		}
		idString = idString.deleteCharAt(idString.length() - 1);
		if (gradeService.delGrade(idString.toString()) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "ɾ��ʧ��");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "ɾ���ɹ���");
		return retn;
	}
}
