package biz.webgate.dominoext.nagios.statistic;

import java.text.NumberFormat;
import java.util.Vector;

import lotus.domino.DateTime;


public class ResultStatistic {

	private final int DOUBLE_TYPE = 768;
	private final String DATA_TYPE_ERROR = "Field Not found or invalid Item Type";
	private String m_Status;
	private double m_Field = Double.NEGATIVE_INFINITY;
	private double m_AdditionalField = Double.NEGATIVE_INFINITY;
	private DateTime m_DocumentCreated;
	private Integer m_Result;
	private double m_DiskFreePercent = Double.NEGATIVE_INFINITY;
	private double m_DiskUsedPercent = Double.NEGATIVE_INFINITY;
	
	public void calculateFieldValue(Vector<?> vector, int itemType) {
		if (vector != null & itemType == DOUBLE_TYPE) {
			m_Field = ((Double) vector.get(0)).doubleValue();
		}else {
			m_Status = DATA_TYPE_ERROR;
		}
	}

	public void calculateAdditionalFieldValue(Vector<?> vector, int itemType) {
		if (vector != null & itemType == DOUBLE_TYPE) {
			m_AdditionalField = ((Double) vector.get(0)).doubleValue();
		}else {
			m_Status = DATA_TYPE_ERROR;
		}
	}

	public String getStatus() {
		return m_Status;
	}

	public DateTime getDocumentCreated() {
		return m_DocumentCreated;
	}

	public void setDocumentCreated(DateTime documentCreated) {
		m_DocumentCreated = documentCreated;
	}

	public void calculateResult(RequestStatistic requestStatistic) {
		m_Result = 0;
		if (requestStatistic.getPercent()) {
			calculateDiskFreePercent();
			if(requestStatistic.getIsBigger()) {
				if(Double.parseDouble(requestStatistic.getWarning()) < m_DiskFreePercent) {
					m_Result = 1;
				}
			}else{
				if(Double.parseDouble(requestStatistic.getWarning()) > m_DiskFreePercent) {
					m_Result = 1;
				}			
			}
			
			if(requestStatistic.getIsBigger()) {
				if(Double.parseDouble(requestStatistic.getCritical()) < m_DiskFreePercent) {
					m_Result = 2;
				}
			}else{
				if(Double.parseDouble(requestStatistic.getCritical()) > m_DiskFreePercent) {
					m_Result = 2;
				}			
			
			}
			
		}else {
			if(requestStatistic.getIsBigger()) {
				if(Double.parseDouble(requestStatistic.getWarning()) < m_Field) {
					m_Result = 1;
				}
			}else{
				if(Double.parseDouble(requestStatistic.getWarning()) > m_Field) {
					m_Result = 1;
				}			
			}
			
			if(requestStatistic.getIsBigger()) {
				if(Double.parseDouble(requestStatistic.getCritical()) < m_Field) {
					m_Result = 2;
				}
			}else{
				if(Double.parseDouble(requestStatistic.getCritical()) > m_Field) {
					m_Result = 2;
				}			
			}
					
		}		
	}
	
	public String getNAGIOSResponse(Boolean percent) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		StringBuilder stringBuilder = new StringBuilder();
		if (percent) {
			stringBuilder.append(numberFormat.format(((Double) m_DiskFreePercent).doubleValue())+"% free \n");
			stringBuilder.append(numberFormat.format(((Double) m_DiskUsedPercent).doubleValue())+"% used \n");

		}else {
			stringBuilder.append(numberFormat.format(((Double) m_Field).doubleValue()) + "\n");			
		};
		stringBuilder.append(m_Result + "\n");
		stringBuilder.append(getStatusInfo() + "\n");
		stringBuilder.append(m_DocumentCreated + "\n");
		return stringBuilder.toString();
	}

	private String getStatusInfo() {
		switch (m_Result) {
		case 0:
			return "OK";
		case 1:
			return "WARNING";
		case 2:
			return "CRITICAL";
		}
		return "UNKNOWN";
	}
	
	private void calculateDiskFreePercent() {
		m_DiskFreePercent = 100 / m_AdditionalField * m_Field;
		m_DiskUsedPercent = (m_AdditionalField - m_Field) *(100 / m_AdditionalField);
	}
	
	
}