package com.digitzones.service;

import java.math.BigDecimal;
import java.util.List;

import com.digitzones.model.DeviceSite;
/**
 * 设备站点service
 * @author zdq
 * 2018年6月11日
 */
public interface IDeviceSiteService extends ICommonService<DeviceSite> {
	/**
	 * 查询生产单元内的站点数目
	 * @return
	 */
	public Long queryCountOfDeviceSite();
	/**
	 * 根据站点状态查询
	 * @param status
	 * @return
	 */
	public Long queryCountOfDeviceSiteByStatus(String status);
	/**
	 * 根据生产单元id查询站点
	 * @param productionUnitId
	 * @return
	 */
	public List<DeviceSite> queryDeviceSitesByProductionUnitId(Long productionUnitId);
	/**
	 * 查询所有的设备站点
	 * @return
	 */
	public List<DeviceSite> queryAllDeviceSites();
	/**
	 * 根据是否显示在参数状态页面查询设备站点
	 * @param isShow
	 * @return
	 */
	public List<DeviceSite> queryDeviceSitesByShow(boolean isShow);
	/**
	 * 根据设备id查询所有设备站点
	 * @param deviceId
	 * @return
	 */
	public List<DeviceSite> queryAllDeviceSitesByDeviceId(Long deviceId);
	/**
	 * 根据班次id查询设备站点
	 * @param classesId
	 * @return
	 */
	public List<DeviceSite> queryDeviceSitesByClassesId(Long classesId);
	/**
	 * 根据设备站点ID查找损时记录数
	 * @param deviceSiteId
	 * @return
	 */
	public Integer queryLostTimeCountByDeviceSiteId(Long deviceSiteId);
	/**
	 * 根据设备站点查找按灯记录数
	 * @param deviceSiteId
	 * @return
	 */
	public Integer queryPressLightCountByDeviceSiteId(Long deviceSiteId);
	/**
	 * 根据设备站点ID查找工序和设备站点的关联数
	 * @param deviceSiteId
	 * @return
	 */
	public Integer queryProcessDeviceSiteMappingCountByDeviceSiteId(Long deviceSiteId);
	/**
	 * 根据生产单元ID查找设备站点
	 * @param productionUnitId
	 * @return
	 */
	public List<BigDecimal> queryDeviceSiteIdsByProductionUnitId(Long productionUnitId);
	/**
	 * 查找所有生产单元的ID
	 * @return
	 */
	public List<BigDecimal> queryAllDeviceSiteIds();
}
