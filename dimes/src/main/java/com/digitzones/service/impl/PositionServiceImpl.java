package com.digitzones.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitzones.dao.IPositionDao;
import com.digitzones.model.Pager;
import com.digitzones.model.Position;
import com.digitzones.service.IPositionService;
@Service
public class PositionServiceImpl implements IPositionService {
	private IPositionDao positionDao;
	@Autowired
	public void setPositionDao(IPositionDao positionDao) {
		this.positionDao = positionDao;
	}

	@Override
	public Serializable addPosition(Position position) {
		return positionDao.save(position);
	}

	@Override
	public void deletePosition(Serializable id) {
		positionDao.deleteById(id);
	}

	@Override
	public void updatePosition(Position position) {
		positionDao.update(position);
	}

	@Override
	public List<Position> queryPositionByDepartmentId(Serializable deptId) {
		return positionDao.findByHQL("from Position p inner join fetch p.department d where d.id=?0", new Object[] {deptId});
	}

	@Override
	public Pager<Position> queryObjs(String hql, int pageNo, int pageSize, Object... values) {
		return positionDao.findByPage(hql, pageNo, pageSize, values);
	}
}
