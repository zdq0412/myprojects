package com.digitzones.controllers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.digitzones.config.QRConfig;
import com.digitzones.model.Equipment;
import com.digitzones.model.Pager;
import com.digitzones.service.IEquipmentService;
import com.digitzones.util.QREncoder;
import com.digitzones.vo.EquipmentVO;
@Controller
@RequestMapping("/equipment")
public class EquipmentController {
	private QRConfig config ;
	@Autowired
	public void setConfig(QRConfig config) {
		this.config = config;
	}
	private QREncoder qrEncoder = new QREncoder();
	private IEquipmentService equipmentService;
	@Autowired
	public void setEquipmentService(IEquipmentService equipmentService) {
		this.equipmentService = equipmentService;
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
	public ModelMap queryEquipmentsByEquipmentTypeId(@RequestParam(value="equipmentTypeId",required=false)Long equipmentTypeId,@RequestParam(value="rows",defaultValue="20")Integer rows,@RequestParam(defaultValue="1")Integer page,HttpServletRequest request) {
		Pager<Equipment> pager = null;
		pager = equipmentService.queryObjs("select p from Equipment p inner join p.equipmentType pt  where pt.id=?0", page, rows, new Object[] {equipmentTypeId});
		ModelMap mm = new ModelMap();
		List<EquipmentVO> vos = new ArrayList<>();
		if(pager.getData()!=null&&pager.getData().size()>0) {
			for(Equipment e:pager.getData()) {
				vos.add(model2VO(e, request));
			}
		}
		mm.addAttribute("rows",vos);
		mm.addAttribute("total", pager.getTotalCount());
		mm.addAttribute("code", "0");
		mm.addAttribute("msg", "");
		return mm;
	}
	/**
	 * 添加参数
	 * @param equipment
	 * @return
	 */
	@RequestMapping("/addEquipment.do")
	@ResponseBody
	public ModelMap addEquipment(Equipment equipment,HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		Equipment equipment4Code = equipmentService.queryByProperty("code", equipment.getCode());
		if(equipment4Code!=null) {
			modelMap.addAttribute("success", false);
			modelMap.addAttribute("msg", "装备编码已被使用");
		}else {
			if(equipment.getPicName()!=null &&!"".equals(equipment.getPicName())) {
				String dir = request.getServletContext().getRealPath("/");
				File file = new File(dir,equipment.getPicName());
				equipmentService.addEquipment(equipment, file);
			}else {
				equipmentService.addObj(equipment);
			}
			modelMap.addAttribute("success", true);
			modelMap.addAttribute("msg", "添加成功!");
		}
		return modelMap;
	}
	@RequestMapping("/upload.do")
	@ResponseBody
	public ModelMap upload(Part file,HttpServletRequest request) {
		String dir = request.getServletContext().getRealPath("/")+"console/equipmentImgs/";
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
			modelMap.addAttribute("filePath", "console/equipmentImgs/" + fileName);
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
	public EquipmentVO queryEquipmentById(Long id,HttpServletRequest request) {
		Equipment equipment = equipmentService.queryObjById(id);
		return model2VO(equipment, request);
	}
	/**
	 * 更新参数信息
	 * @param equipment
	 * @return
	 */
	@RequestMapping("/updateEquipment.do")
	@ResponseBody
	public ModelMap updateEquipment(Equipment equipment,HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		
		if(equipment.getPicName()!=null &&!"".equals(equipment.getPicName())) {
			String dir = request.getServletContext().getRealPath("/");
			File file = new File(dir,equipment.getPicName());
			equipmentService.updateEquipment(equipment, file);
		}else {
			equipmentService.updateObj(equipment);
		}
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
		equipmentService.deleteObj(Long.valueOf(id));
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
		Equipment d = equipmentService.queryObjById(Long.valueOf(id));
		d.setDisabled(true);

		equipmentService.updateObj(d);
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
	public ModelMap queryEquipmentByProcessId(Long processId,@RequestParam(value="rows",defaultValue="20")Integer rows,@RequestParam(defaultValue="1")Integer page,HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		String hql = "select ds from ProcessesEquipmentMapping pdm inner join  pdm.equipments ds  inner join pdm.processes p where p.id=?0";
		Pager<Equipment> pager = equipmentService.queryObjs(hql, page, rows, new Object[] {processId});
		List<EquipmentVO> vos = new ArrayList<>();
		if(pager.getData()!=null&&pager.getData().size()>0) {
			for(Equipment e:pager.getData()) {
				vos.add(model2VO(e, request));
			}
		}
		modelMap.addAttribute("rows",vos);
		modelMap.addAttribute("total", pager.getTotalCount());
		return modelMap;
	}
	/**
	 * 查询所有的装备
	 * @return
	 */
	@RequestMapping("/queryAllEquipments.do")
	@ResponseBody
	public List<Equipment> queryAllEquipments(String q){
		if(q==null) {
			return equipmentService.queryAllEquipments();
		}else {
			return equipmentService.queryEquipmentsByCodeOrNameOrUnity(q);
		}
	}

	/**
	 * 打印装备的二维码
	 * @param ids 设备id字符串
	 * @return
	 */
	@RequestMapping("/printQr.do")
	@ResponseBody
	public List<EquipmentVO> printQr(String ids,HttpServletRequest request) {
		String dir = request.getServletContext().getRealPath("/");
		List<EquipmentVO> vos = new ArrayList<>();
		String[] idStr = ids.split(",");
		for(int i = 0 ;i<idStr.length;i++) {
			String id = idStr[i];
			Equipment e = equipmentService.queryObjById(Long.valueOf(id));
			EquipmentVO vo = model2VO(e,request);
			vo.setQrPath(qrEncoder.generatePR(e.getCode(),dir , config.getQrPath()));
			vos.add(vo);
		}
		return vos;
	}

	private EquipmentVO model2VO(Equipment e,HttpServletRequest request) {
		if(e==null) {
			return null;
		}
		EquipmentVO vo = new EquipmentVO();
		vo.setId(e.getId());
		vo.setName(e.getName());
		vo.setCode(e.getCode());
		vo.setManufacturer(e.getManufacturer());
		vo.setUnitType(e.getUnitType());
		if(e.getPic()!=null) {
			String dir = request.getServletContext().getRealPath("/");
			InputStream is;
			try {
				is = e.getPic().getBinaryStream();
				File out = new File(dir,e.getPicName());
				FileCopyUtils.copy(is, new FileOutputStream(out));
				vo.setPic(dir+"/"+e.getPicName());
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		}
		return vo;
	}
} 
