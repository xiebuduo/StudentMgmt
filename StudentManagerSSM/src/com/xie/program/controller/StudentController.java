package com.xie.program.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xie.program.entity.Student;
import com.xie.program.page.Page;
import com.xie.program.service.ClazzService;
import com.xie.program.service.StudentService;

import net.sf.json.JSONArray;

/**
 * ѧ��������
 * 
 * @author Xiebuduo
 *
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController {

	// ��ȡ�༶�б�
	@Autowired
	private ClazzService clazzService;
	@Autowired
	private StudentService studentService;

	/**
	 * ѧ����ҳ
	 */
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView model) {
		model.setViewName("student/list");
		model.addObject("clazzList", clazzService.findAll());
		model.addObject("clazzListJson", JSONArray.fromObject(clazzService.findAll()));
		return model;
	}

	/**
	 * ��ȡѧ���б�
	 */
	@RequestMapping(value = "/get_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "cid", required = false, defaultValue = "0") Long cid, Page page,
			HttpServletRequest request
			) {
		Integer userType = (Integer)request.getSession().getAttribute("userType");
		
		Map<String, Object> retn = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		//����û���ѧ������ֻ���ظ�ѧ����Ϣ
		if (userType == 2) {
			Student student = (Student)request.getSession().getAttribute("user");
			name = student.getName();
			queryMap.put("name",  name);
		} else {
			queryMap.put("name", "%" + name + "%");
		}
		queryMap.put("cid", cid);
		queryMap.put("offSet", page.getOffSet());
		queryMap.put("pageSize", page.getRows());

		retn.put("rows", studentService.findList(queryMap));
		retn.put("total", studentService.total(queryMap));

		return retn;

	}
	
	/**
	 * ���ѧ��
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Student student){
		Map<String, String> retn = new HashMap<String, String>();
		if (student == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ�������ϵ������Ա��");
			return retn;
		}
		//�жϸ�ѧ���Ƿ����
		Student existsStudent = studentService.findStu(student.getStuno());
		if (!(existsStudent == null)) {
			retn.put("type", "error");
			retn.put("msg", "�Ѵ��ڸ�ѧ�ŵ�ѧ����");
			return retn;
		}
		//���
		if (studentService.add(student) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "���ʧ�ܣ�");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "��ӳɹ���");
		return retn;
	}

	/**
	 * �޸�ѧ����Ϣ
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Student student){
		Map<String, String> retn = new HashMap<String, String>();
		if (student == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ�������ϵ������Ա��");
			return retn;
		}
		
		//�ж��Ƿ��ظ�
		Student stuExists = studentService.findStu(student.getStuno());
		if (stuExists != null) {
			if (student.getId() != stuExists.getId()) {
				retn.put("type", "error");
				retn.put("msg", "�Ѵ��ڸ�ѧ�ŵ�ѧ����");
				return retn;
			}
		}
		//�޸�
		if (studentService.edit(student) <= 0) {
			retn.put("type", "error");
			retn.put("msg", "�޸�ʧ�ܣ�");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "�޸ĳɹ���");
		return retn;
	}
	/**
	 * ɾ��
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> del(
				@RequestParam(value = "ids[]",required = true) Long[] ids
			){
		Map<String, String> retn =new HashMap<String, String>();
		if (ids == null) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ�������ϵ������Ա��");
			return retn;
		}
		StringBuilder stuString = new StringBuilder();
		for (Long id : ids) {
			stuString.append(id+",");
		}
		stuString = stuString.deleteCharAt(stuString.length()-1);
		if (studentService.del(stuString.toString()) <=0) {
			retn.put("type", "error");
			retn.put("msg", "���ݴ�������ϵ������Ա��");
			return retn;
		}
		retn.put("type", "success");
		retn.put("msg", "ɾ���ɹ���");
		return retn;
	}
	/**
	 * ͷ���ϴ�
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */

	@RequestMapping(value = "/upload_photo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadPhoto(MultipartFile photo, HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException {
		Map<String, String> retn = new HashMap<String, String>();
		if (photo == null) {
			retn.put("type", "error");
			retn.put("msg", "��ѡ���ļ���");
			return retn;
		}
		if (photo.getSize() > 1024 * 1024 * 10) {
			retn.put("type", "error");
			retn.put("msg", "���ϴ�������10M��ͼƬ��");
			return retn;
		}
		String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1,
				photo.getOriginalFilename().length());
		if (!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())) {
			retn.put("type", "error");
			retn.put("msg", "�ļ���ʽ����ȷ�����ϴ�jpg,png,gif,jpeg��ʽ��ͼƬ��");
			return retn;
		}

		String savePath = request.getServletContext().getRealPath("/") + "\\upload\\";
		System.out.println(savePath);
		File savePathFile = new File(savePath);
		if (!savePathFile.exists()) {
			savePathFile.mkdir();
		}
		
		String filename = new Date().getTime() + "." + suffix;
		photo.transferTo(new File(savePath + filename));
		
		retn.put("type", "success");
		retn.put("msg", "ͼƬ�ϴ��ɹ���");
		retn.put("src", request.getServletContext().getContextPath() + "/upload/" +filename);
		return retn;
	}

}
