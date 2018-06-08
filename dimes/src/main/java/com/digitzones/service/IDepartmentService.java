package com.digitzones.service;

import java.io.Serializable;
import java.util.List;

import com.digitzones.model.Department;

/**
 * 部门管理业务接口
 * @author zdq
 * 2018年6月7日
 */
public interface IDepartmentService extends ICommonService<Department>{
	/**
	 * 查找顶层部门，实际上就是公司信息
	 * @return
	 */
	public List<Department> queryTopDepartment();
	/**
	 * 查找子部门 
	 * @param pid 父部门id
	 * @return
	 */
	public List<Department> querySubDepartment(Serializable pid);
	/**
	 * 添加部门
	 * @param department
	 * @return
	 */
	public Serializable addDepartment(Department department);
	
}