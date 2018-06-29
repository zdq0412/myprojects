package com.digitzones.controllers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 装备管理控制器
 * @author zdq
 * 2018年6月7日
 */
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitzones.model.Equipment;
import com.digitzones.model.Pager;
import com.digitzones.service.IMeasuringToolService;
@Controller
@RequestMapping("/measuringTool")
public class MeasuringToolController {
	private IMeasuringToolService measuringToolService;
	@Autowired
	public void setMeasuringToolService(IMeasuringToolService measuringToolService) {
		this.measuringToolService = measuringToolService;
	}
	/**
	 * 分页查询参数信息
	 * @param pid
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryEquipmentsByEquipmentTypeId.do")
	@ResponseBody 
	public ModelMap queryEquipmentsByEquipmentTypeId(@RequestParam(value="measuringToolTypeId",required=false)Long measuringToolTypeId,@RequestParam(value="rows",defaultValue="20")Integer rows,@RequestParam(defaultValue="1")Integer page) {
		Pager<Object[]> pager = null;
		pager = measuringToolService.queryObjs("select p from Equipment p inner join p.equipmentType pt  where pt.id=?0", page, rows, new Object[] {measuringToolTypeId});
		ModelMap mm = new ModelMap();
		mm.addAttribute("rows",pager.getData());
		mm.addAttribute("total", pager.getTotalCount());
		mm.addAttribute("code", "0");
		mm.addAttribute("msg", "");
		return mm;
	}
	
	
	/**
	 * 添加参数
	 * @param measuringTool
	 * @return
	 */
	@RequestMapping("/addEquipment.do")
	@ResponseBody
	public ModelMap addEquipment(Equipment measuringTool) {
		ModelMap modelMap = new ModelMap();
		Equipment measuringTool4Code = measuringToolService.queryByProperty("code", measuringTool.getCode());
		if(measuringTool4Code!=null) {
			modelMap.addAttribute("success", false);
			modelMap.addAttribute("msg", "量具编码已被使用");
		}else {
			measuringToolService.addObj(measuringTool);
			modelMap.addAttribute("success", true);
			modelMap.addAttribute("msg", "添加成功!");
		}
		return modelMap;
	}
	@RequestMapping("/upload.do")
	@ResponseBody
	public ModelMap upload(Part file,HttpServletRequest request) {
		String dir = request.getServletContext().getRealPath("/")+"console/measuringToolImgs/";
		String realName = file.getSubmittedFileName();
		ModelMap modelMap = new ModelMap();
		String fileName = new Date().getTime()+ realName.substring(realName.lastIndexOf("."));
		InputStream is;
		try {
			is = file.getInputStream();
			File out = new File(dir,fileName);
			FileCopyUtils.copy(is, new FileOutputStream(out));
			modelMap.addAttribute("statusCode", 200);
			modelMap.addAttribute("title", "操作提示");
			modelMap.addAttribute("message", "文件上传成功！");
			modelMap.addAttribute("filePath", "console/measuringToolImgs/" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modelMap;
	}
	/**
	 * 根据id查询参数
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryEquipmentById.do")
	@ResponseBody
	public Equipment queryEquipmentById(Long id) {
		Equipment measuringTool = measuringToolService.queryObjById(id);
		return measuringTool;
	}
	/**
	 * 更新参数信息
	 * @param measuringTool
	 * @return
	 */
	@RequestMapping("/updateEquipment.do")
	@ResponseBody
	public ModelMap updateEquipment(Equipment measuringTool) {
		ModelMap modelMap = new ModelMap();
		measuringToolService.updateObj(measuringTool);
		modelMap.addAttribute("success", true);
		modelMap.addAttribute("msg", "编辑成功!");
		return modelMap;
	}
	/**
	 * 根据id删除参数信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteEquipment.do")
	@ResponseBody
	public ModelMap deleteEquipment(String id) {
		if(id!=null && id.contains("'")) {
			id = id.replace("'", "");
		}
		ModelMap modelMap = new ModelMap();
		measuringToolService.deleteObj(Long.valueOf(id));
		modelMap.addAttribute("success", true);
		modelMap.addAttribute("statusCode", 200);
		modelMap.addAttribute("title", "操作提示");
		modelMap.addAttribute("message", "成功删除!");
		return modelMap;
	}
	/**
	 * 停用该参数
	 * @param id
	 * @return
	 */
	@RequestMapping("/disabledEquipment.do")
	@ResponseBody
	public ModelMap disabledEquipment(String id) {
		if(id!=null && id.contains("'")) {
			id = id.replace("'", "");
		}
		ModelMap modelMap = new ModelMap();
		Equipment d = measuringToolService.queryObjById(Long.valueOf(id));
		d.setDisabled(true);

		measuringToolService.updateObj(d);
		modelMap.addAttribute("statusCode", 200);
		modelMap.addAttribute("message", "已停用");
		modelMap.addAttribute("title", "操作提示!");
		return modelMap;
	}
	/**
	 * 根据工序id查询参数 信息
	 * @param processId
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryEquipmentByProcessId.do")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelMap queryEquipmentByProcessId(Long processId,@RequestParam(value="rows",defaultValue="20")Integer rows,@RequestParam(defaultValue="1")Integer page) {
		ModelMap modelMap = new ModelMap();
		String hql = "select ds from ProcessesEquipmentMapping pdm inner join  pdm.measuringTools ds  inner join pdm.processes p where p.id=?0";
		Pager<Equipment> pager = measuringToolService.queryObjs(hql, page, rows, new Object[] {processId});
		modelMap.addAttribute("total", pager.getTotalCount());
		modelMap.addAttribute("rows", pager.getData());
		return modelMap;
	}
	/**
	 * 查询所有的装备
	 * @return
	 */
	@RequestMapping("/queryAllMeasuringTools.do")
	@ResponseBody
	public List<Equipment> queryAllMeasuringTools(){
		return measuringToolService.queryAllMeasuringTools();
	}
} 