package com.digitzones.dao;

import java.util.Date;

import com.digitzones.model.Classes;
import com.digitzones.model.LostTimeRecord;
/**
 * 损时记录dao
 * @author zdq
 * 2018年6月11日
 */
public interface ILostTimeRecordDao extends ICommonDao<LostTimeRecord> {
	/**
	 * 根据年月信息查询当月的总共的损时数
	 * @param year
	 * @param month
	 * @return
	 */
	public Double queryHoursOfLostTimeRecordByYearAndMonth(Integer year,Integer month) ;
	/**
	 * 查询计划停机时间
	 * @param year
	 * @param month
	 * @return
	 */
	public Double queryHoursOfPlanHaltByYearAndMonth(Integer year,Integer month) ;
	/**
	 * 根据时间段查找损时信息
	 * @param c
	 * @param deviceSiteId
	 * @param date 损时日期
	 * @return
	 */
	public Double queryLostTime(Classes c,Long deviceSiteId,Date date);
	/**
	 * 查找计划停机时间
		@param c
	 * @param deviceSiteId
	 * @return
	 */
	public Double queryPlanHaltTime(Classes c,Long deviceSiteId,Date date);
	/**
	 * 每天损时
	 * @param c
	 * @param deviceSiteId
	 * @param date
	 * @return
	 */
	public Double queryLostTime4PerDay(Classes c,Long deviceSiteId,Date date);
}
