package biz.webgate.nagios;

import java.io.Serializable;
import biz.webgate.xpages.dss.annotations.DominoEntity;
import biz.webgate.xpages.dss.annotations.DominoStore;

@DominoStore(Form = "frmNagiosSettings", PrimaryKeyField = "ID", PrimaryFieldClass = String.class, View = "lupNagiosSettings")
public class NagiosSetting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DominoEntity(FieldName = "IDT")
	private String m_ID;
	@DominoEntity(FieldName = "SearchStrT")
	private String m_SearchStr;
	@DominoEntity(FieldName = "AliasNameT")
	private String m_AliasName;
	@DominoEntity(FieldName = "WarningT")
	private double m_Warning;
	@DominoEntity(FieldName = "CriticalT")
	private double m_Critical;
	@DominoEntity(FieldName = "gkT")
	private String m_Gk;
	
	public String getID() {
		return m_ID;
	}
	public void setID(String id) {
		m_ID = id;
	}
	public String getSearchStr() {
		return m_SearchStr;
	}
	public void setSearchStr(String searchStr) {
		m_SearchStr = searchStr;
	}
	public String getAliasName() {
		return m_AliasName;
	}
	public void setAliasName(String aliasName) {
		m_AliasName = aliasName;
	}
	public double getWarning() {
		return m_Warning;
	}
	public void setWarning(double warning) {
		m_Warning = warning;
	}
	public double getCritical() {
		return m_Critical;
	}
	public void setCritical(double critical) {
		m_Critical = critical;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getGk() {
		return m_Gk;
	}
	public void setGk(String gk) {
		m_Gk = gk;
	}
	
	
	
	
	
}
