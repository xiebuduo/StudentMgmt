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

import com.xie.program.entity.Clazz;
import com.xie.program.page.Page;
import com.xie.program.service.ClazzService;
import com.xie.program.service.GradeService;

import net.sf.json.JSONArray;

/**
 * �༶������
 * 
 * @author Xiebuduo
 *
 */
@RequestMapping(value = "/clazz")
@Controller
public class ClazzController {

	@Autowired
	private GradeService gradeService;
	@Autowired
	private ClazzService clazzService;

	/**
	 * �༶�б�
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView list(ModelAndView model) {
		model.setViewName("clazz/list");
		model.addObject("gradeList", gradeService.findAll());
		model.addObject("gradeListJson", JSONArray.fromObject(gradeService.findAll()));
		return model;
	}

	/**
	 * ��ʾ�༶�б�
	 */
	@RequestMapping(value = "get_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "gid", required = false, defaultValue = "0") Long gid, Page page) {
		Map<String, Object> retn = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", "%" + name +"%");
		queryMap.put("gid", gid);
		queryMap.put("offset", page.getOffSet());
		queryMap.put("pageSize", page.getRows());
		
		retn.put("rows", clazzService.findList(queryMap));
		retn.put("total", clazzService.total(queryMap));
		return retn;
	}

	/**
	 * ��Ӱ༶
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Clazz clazz) {
		Map<String, String> retn = new HashMap<String, String>();
		if (clazz == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ�������ϵ����Ա��");
			return retn;
		}
		if (clazz.getName() == null) {
			retn.put("type", "error");
			retn.put("msg", "�༶������Ϊ�գ�");
			return retn;
		}
		if (clazz.getGid() == null) {
			retn.put("type", "error");
			retn.put("msg", "��ѡ�������꼶��");
			return retn;
		}
		Map<String, Object> findClazz=new HashMap<String, Object>();
		findClazz.put("name", clazz.getName());
		findClazz.put("gid", clazz.getGid());
		if (clazzService.findByName(findClazz) != null) {
			retn.put("type", "error");
			retn.put("msg", "�ð༶�Ѵ��ڣ�");
			return retn;
		}
		if (clazzService.addClazz(clazz) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "�༶" + clazz.getName() + "���ʧ�ܣ�");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "�༶" + clazz.getName() + "��ӳɹ���");
		return retn;
	}
	
	/**
	 * �޸�
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Clazz clazz){
		Map<String, String> retn = new HashMap<String, String>();
		if (clazz == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ�������ϵ����Ա��");
			return retn;
		}
		if (clazz.getName() == null) {

			retn.put("type", "error");
			retn.put("msg", "�༶������Ϊ�գ�");
			return retn;
		}
		Map<String, Object> findClazz=new HashMap<String, Object>();
		findClazz.put("name", clazz.getName());
		findClazz.put("gid", clazz.getGid());
		Clazz existClazz = clazzService.findByName(findClazz);
		if (existClazz != null) {
			if (existClazz.getId() != clazz.getId()) {
				retn.put("type", "error");
				retn.put("msg", "�ð༶�Ѵ��ڣ�");
				return retn;
			}
		}
		if (clazzService.editClazz(clazz) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "�༶" + clazz.getName() + "�޸�ʧ�ܣ�");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "�༶" + clazz.getName() + "�޸ĳɹ���");
		return retn;
	}
	/**
	 * ɾ���༶
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> del(
			@RequestParam(value = "ids[]", required = true) Long[] ids
			) {
		Map<String, String> retn = new HashMap<String, String>();
		if (ids == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ�������ϵ����Ա��");
			return retn;
		}
		StringBuilder claString = new StringBuilder();
		for (Long id:ids) {
			claString.append(id+",");
		}
		claString = claString.deleteCharAt(claString.length()-1);
		if(clazzService.delClazz(claString.toString()) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "ɾ��ʧ�ܣ�");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "ɾ���ɹ���");
		return retn;
	}
}
