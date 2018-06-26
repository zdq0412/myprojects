package com.digitzones.service.impl;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitzones.constants.Constant;
import com.digitzones.dao.IDeviceSiteDao;
import com.digitzones.dao.IPressLightRecordDao;
import com.digitzones.model.DeviceSite;
import com.digitzones.model.Pager;
import com.digitzones.model.PressLightRecord;
import com.digitzones.service.IPressLightRecordService;
@Service
public class PressLightRecordServiceImpl implements IPressLightRecordService {
	private IPressLightRecordDao pressLightRecordDao;
	private IDeviceSiteDao deviceSiteDao;
	@Autowired
	public void setDeviceSiteDao(IDeviceSiteDao deviceSiteDao) {
		this.deviceSiteDao = deviceSiteDao;
	}

	@Autowired
	public void setPressLightRecordDao(IPressLightRecordDao pressLightRecordDao) {
		this.pressLightRecordDao = pressLightRecordDao;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Pager queryObjs(String hql, int pageNo, int pageSize, Object... values) {
		return pressLightRecordDao.findByPage(hql, pageNo, pageSize, values);
	}

	@Override
	public void updateObj(PressLightRecord obj) {
		//停机
		if(obj.getHalt()) {
			DeviceSite deviceSite = deviceSiteDao.findById(obj.getDeviceSite().getId());
			deviceSite.setStatus(Constant.DeviceSite.HALT);
			deviceSiteDao.update(deviceSite);
		}else {
			DeviceSite deviceSite = deviceSiteDao.findById(obj.getDeviceSite().getId());
			deviceSite.setStatus(Constant.DeviceSite.RUNNING);
			deviceSiteDao.update(deviceSite);
		}
		pressLightRecordDao.update(obj);
	}

	@Override
	public PressLightRecord queryByProperty(String name, String value) {
		return pressLightRecordDao.findSingleByProperty(name, value);
	}

	@Override
	public Serializable addObj(PressLightRecord obj) {
		//停机
		if(obj.getHalt()) {
			DeviceSite deviceSite = deviceSiteDao.findById(obj.getDeviceSite().getId());
			deviceSite.setStatus(Constant.DeviceSite.HALT);
			deviceSiteDao.update(deviceSite);
		}
		return pressLightRecordDao.save(obj);
	}

	@Override
	public PressLightRecord queryObjById(Long id) {
		return pressLightRecordDao.findById(id);
	}

	@Override
	public void deleteObj(Long id) {
		pressLightRecordDao.deleteById(id);
	}

	@Override
	public Long queryCountByPressLightTime(Date pressLightTime) {
		return pressLightRecordDao.queryCountByPressLightTime(pressLightTime);
	}
}