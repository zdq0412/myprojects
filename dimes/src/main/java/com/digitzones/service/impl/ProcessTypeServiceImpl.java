package com.digitzones.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitzones.dao.IProcessTypeDao;
import com.digitzones.model.Pager;
import com.digitzones.model.ProcessType;
import com.digitzones.service.IProcessTypeService;
@Service
public class ProcessTypeServiceImpl implements IProcessTypeService {
	private IProcessTypeDao processTypeDao;
	@Autowired
	public void setProcessTypeDao(IProcessTypeDao processTypeDao) {
		this.processTypeDao = processTypeDao;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Pager queryObjs(String hql, int pageNo, int pageSize, Object... values) {
		return processTypeDao.findByPage(hql, pageNo, pageSize, values);
	}

	@Override
	public void updateObj(ProcessType obj) {
		processTypeDao.update(obj);
	}

	@Override
	public ProcessType queryByProperty(String name, String value) {
		return processTypeDao.findSingleByProperty(name, value);
	}

	@Override
	public Serializable addObj(ProcessType obj) {
		return processTypeDao.save(obj);
	}

	@Override
	public ProcessType queryObjById(Long id) {
		return processTypeDao.findById(id);
	}

	@Override
	public void deleteObj(Long id) {
		processTypeDao.deleteById(id);
	}
}
