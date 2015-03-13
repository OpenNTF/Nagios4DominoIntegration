/*
 * © Copyright WebGate Consulting AG, 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package biz.webgate.dominoext.nagios.statistic;

import java.io.Serializable;
import java.text.NumberFormat;
import lotus.domino.DateTime;


public class StatisticEntry implements Serializable {

	public static final int TYPE_DOUBLE = 0;
	public static final int TYPE_STRING = 1;
	public static final int TYPE_DATE = 2;
	public static final int ITEM_TYPE_NUMBERS = 768;
	public static final int ITEM_TYPE_TEXT_LIST = 1280;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String m_Key;
	private Object m_Value;
	private int m_Type;
	private String m_ClearText;
	private double m_WarningLevel = Double.NEGATIVE_INFINITY;
	private double m_CriticalLevel = Double.NEGATIVE_INFINITY;
	private boolean isBigger;
	private String m_SourceDatabase; 
	private String m_SourceView;
	private String m_DocumentSearchKey;
	private String m_CheckValueField;
	private DateTime m_DocumentCreated;


	public String getKey() {
		return m_Key;
	}

	public void setKey(String key) {
		m_Key = key;
	}

	public Object getValue() {
		return m_Value;
	}

	public void setValue(Object value) {
		m_Value = value;
	}

	public int getType() {
		return m_Type;
	}

	public void setType(int type) {
		m_Type = type;
	}

	public String getClearText() {
		if (m_ClearText == null) {
			return m_Key;
		}
		return m_ClearText;
	}

	public void setClearText(String clearText) {
		m_ClearText = clearText;
	}

	public double getWarningLevel() {
		return m_WarningLevel;
	}

	public void setWarningLevel(double warningLevel) {
		m_WarningLevel = warningLevel;
	}

	public double getCriticalLevel() {
		return m_CriticalLevel;
	}

	public void setCriticalLevel(double criticalLevel) {
		m_CriticalLevel = criticalLevel;
	}

	public boolean isBigger() {
		return isBigger;
	}

	public void setBigger(boolean isBigger) {
		this.isBigger = isBigger;
	}

	public String getSourceDatabase() {
		return m_SourceDatabase;
	}

	public void setSourceDatabase(String sourceDatabase) {
		m_SourceDatabase = sourceDatabase;
	}

	public String getSourceView() {
		return m_SourceView;
	}

	public void setSourceView(String sourceView) {
		m_SourceView = sourceView;
	}

	public String getDocumentSearchKey() {
		return m_DocumentSearchKey;
	}

	public void setDocumentSearchKey(String documentSearchKey) {
		m_DocumentSearchKey = documentSearchKey;
	}

	public String getCheckValueField() {
		return m_CheckValueField;
	}

	public void setCheckValueField(String checkValueField) {
		m_CheckValueField = checkValueField;
	}

	public DateTime getDocumentCreated() {
		return m_DocumentCreated;
	}

	public void setDocumentCreated(DateTime documentCreated) {
		m_DocumentCreated = documentCreated;
	}

	public int getStatus() {
		if (m_Type == StatisticEntry.TYPE_DOUBLE) {
			if (!Double.isInfinite(m_CriticalLevel)) {
				if (isBigger) {
					if (getDoubleValue() > m_CriticalLevel) {
						return 2;
					}
				} else {
					if (getDoubleValue() < m_CriticalLevel) {
						return 2;
					}

				}
			}
			if (!Double.isInfinite(m_WarningLevel)) {
				if (isBigger) {
					if (getDoubleValue() > m_WarningLevel) {
						return 1;
					}
				} else {
					if (getDoubleValue() < m_WarningLevel) {
						return 1;
					}

				}
			}

		}
		return 0;

	}

	public String getStatusInfo() {
		switch (getStatus()) {
		case 0:
			return "OK";
		case 1:
			return "WARNING";
		case 2:
			return "CRITICAL";
		}
		return "UNKNOWN";
	}

	public double getDoubleValue() {
		if (m_Type == StatisticEntry.TYPE_DOUBLE) {
			return ((Double) m_Value).doubleValue();
		}
		return Double.NEGATIVE_INFINITY;
	}

	public String getNAGIOSResponse() {
		NumberFormat nfCurrent = NumberFormat.getNumberInstance();
		StringBuilder sb = new StringBuilder();
		if (m_Type == StatisticEntry.TYPE_DOUBLE) {
			sb.append(nfCurrent.format(((Double) m_Value).doubleValue()) + "\n");
		} else {
			sb.append(m_Value + "\n");
		}
		sb.append("" + getStatus() + "\n");
		sb.append(getStatusInfo() + " " + getClearText() + "\n");
		return sb.toString();
	}
	
	public int setItemType(int type) {
		switch (type) {
		case ITEM_TYPE_NUMBERS:
			m_Type = 0;
			break;
		case ITEM_TYPE_TEXT_LIST:
			m_Type = 1;
			break;
		}
		return -1;
	}

//  Wird Später das getNagiosResponse ablösen. Hier werde ich die Performance Daten einbauen	
	public String getNAGIOSDatabaseResponse() {
		NumberFormat nfCurrent = NumberFormat.getNumberInstance();
		StringBuilder sb = new StringBuilder();
		if (m_Type == StatisticEntry.TYPE_DOUBLE) {
			sb.append(nfCurrent.format(((Double) m_Value).doubleValue()) + "\n");
		} else {
			sb.append(m_Value + "\n");
		}
		sb.append("" + getStatus() + "\n");
		sb.append(getStatusInfo());
		return sb.toString();
	}
}
