package biz.webgate.dominoext.nagios.statistic;

import java.io.Serializable;
import java.util.Map;

import biz.webgate.dominoext.nagios.NotesIniFactory;

public class RequestStatistic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String STATUS = "value is missing";
	
	private String m_DB;
	private String m_View;
	private String m_Doc;
	private String m_Field;
	private String m_Warning;
	private String m_Critical;
	private Boolean m_Percent;
	private String m_AdditionalField;
	private Boolean m_IsBigger;
	private String m_Status;
	

	public RequestStatistic setParameterGetOnly(Map<?, ?> params) {
		if (params.get("db") == null | params.get("db") == "") {
			m_DB=NotesIniFactory.getDefaultDB();
		}else {
			String[] arrDB = (String[]) params.get("db");
			m_DB=(arrDB[0]);
		}
		
		if (params.get("view") == null | params.get("view") == "") {
			m_View=NotesIniFactory.getDefaultView();
		}else {
			String[] arrView = (String[]) params.get("view");
			m_View=(arrView[0]);
		}
		
		if (params.get("doc") == null | params.get("doc") == "") {
			m_Status=("Doc "+ STATUS);
		}else {
			String[] arrDoc = (String[]) params.get("doc");
			m_Doc=(arrDoc[0]);
		}
		
		if (params.get("field") == null | params.get("field") == "") {
			m_Status=("field "+ STATUS);
		}else {
			String[] arrField = (String[]) params.get("field");
			m_Field=(arrField[0]);
		}
		return this;		
	}
	
	
	public RequestStatistic setParameter(Map<?, ?> params) {
		if (params.get("db") == null | params.get("db") == "") {
			m_DB=NotesIniFactory.getDefaultDB();
		}else {
			String[] arrDB = (String[]) params.get("db");
			m_DB=(arrDB[0]);
		}
		
		if (params.get("view") == null | params.get("view") == "") {
			m_View=NotesIniFactory.getDefaultView();
		}else {
			String[] arrView = (String[]) params.get("view");
			m_View=(arrView[0]);
		}
		
		if (params.get("doc") == null | params.get("doc") == "") {
			m_Status=("Doc "+ STATUS);
		}else {
			String[] arrDoc = (String[]) params.get("doc");
			m_Doc=(arrDoc[0]);
		}
		
		if (params.get("field") == null | params.get("field") == "") {
			m_Status=("field "+ STATUS);
		}else {
			String[] arrField = (String[]) params.get("field");
			m_Field=(arrField[0]);
		}
		
		if (params.get("w") == null | params.get("w") == "") {
			m_Status=("Warning "+ STATUS);
		}else {
			String[] arrWarning = (String[]) params.get("w");
			m_Warning=(arrWarning[0]);
		}

		if (params.get("c") == null | params.get("c") == "") {
			m_Status=("Critical "+ STATUS);
		}else {
			String[] arrCritical = (String[]) params.get("c");
			m_Critical=(arrCritical[0]);
		}

		if (params.get("p") == null | params.get("p") == "") {
			m_Percent=false;
		}else {
			m_Percent=true;
		}

		if (params.get("op") == null | params.get("op") == "") {
			m_IsBigger = false;
		}else {
			String[] arrOperator = (String[]) params.get("op");
			if (arrOperator[0].equals("g")) {
				m_IsBigger = true;
			}else{
				m_IsBigger = false;
			}
		}

		if (m_Percent) {
			if (params.get("addfield") == null | params.get("addfield") == "") {
				m_Status=("additional Field "+ STATUS);
			}else {
				String[] arrAdditionalField = (String[]) params.get("addfield");
				m_AdditionalField=(arrAdditionalField[0]);
			}
		}
		return this;
	}
	
	public String getDB() {
		return m_DB;
	}
	public String getView() {
		return m_View;
	}
	public String getDoc() {
		return m_Doc;
	}
	public String getField() {
		return m_Field;
	}
	public String getWarning() {
		return m_Warning;
	}
	public String getCritical() {
		return m_Critical;
	}
	public Boolean getPercent() {
		return m_Percent;
	}
	public String getAdditionalField() {
		return m_AdditionalField;
	}
	public boolean getIsBigger() {
		return m_IsBigger;
	}
	public String getStatus() {
		return m_Status;
	}	
}
