package com.digitzones.model;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * 参数
 * @author zdq
 * 2018年6月3日
 */
@Entity
@Table(name="PARAMETERS")
public class Parameters extends CommonModel {
	private static final long serialVersionUID = 1L;
	private Boolean kfc;
	private ParameterType parameterType;
	/**记录是工艺参数还是设备参数*/
	private String baseCode;
	/**取值规则*/
	private String rules;
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	@ManyToOne
	@JoinColumn(name="PARAMETERTYPE_ID")
	public ParameterType getParameterType() {
		return parameterType;
	}
	public void setParameterType(ParameterType parameterType) {
		this.parameterType = parameterType;
	}
	public Boolean getKfc() {
		return kfc;
	}
	public void setKfc(Boolean kfc) {
		this.kfc = kfc;
	}
	public String getBaseCode() {
		return baseCode;
	}
	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}
}
