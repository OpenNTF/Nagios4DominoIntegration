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
package biz.webgate.dominoext.nagios.threshold;

import java.util.Date;
import java.util.HashMap;
import biz.webgate.dominoext.nagios.statistic.StatisticEntry;
import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.Session;
import lotus.domino.View;

public class ValueService {

	private HashMap<String, Value> m_Values;
	private Date m_LastUpdate = null;
	private static ValueService m_Service;

	private ValueService() {

	}

	public static ValueService getInstance() {
		if (m_Service == null) {
			m_Service = new ValueService();
		}
		return m_Service;
	}

	public int loadValues(String strPath, Session sesCurrent) {
		int nCount = 0;
		m_Values = new HashMap<String, Value>();
		try {
			Database ndbSource = sesCurrent.getDatabase(
					sesCurrent.getServerName(), strPath);
			if (ndbSource == null) {
				return -1;
			}
			View viwLUP = ndbSource.getView("lupNagiosSettings");
			if (viwLUP == null) {
				return -2;
			}

			Document docNext = viwLUP.getFirstDocument();
			while (docNext != null) {
				Document docProcess = docNext;
				docNext = viwLUP.getNextDocument(docNext);
				Value val = new Value();
				if(docProcess.getItemValueString("DataSourceT").equals("Domino Database")) {
					val.setAlias(docProcess.getItemValueString("AliasT"));
					val.setKey(docProcess.getItemValueString("IDT"));
					val.setBigger("1".equals(docProcess.getItemValueString("gkT")));
					val.setCritical(docProcess.getItemValueDouble("CriticalT"));
					val.setWarning(docProcess.getItemValueDouble("WarningT"));
					val.setSourceDatabase(docProcess.getItemValueString("sourceDatabaseT"));
					val.setSourceView(docProcess.getItemValueString("sourceViewT"));
					val.setSourceDocumentByKey(docProcess.getItemValueString("sourceDocumentByKeyT"));
					val.setCustomCheck(docProcess.getItemValueString("customCheckT"));
				}else {
					val.setAlias(docProcess.getItemValueString("AliasT"));
					val.setKey(docProcess.getItemValueString("SearchStrT"));
					val.setBigger("1".equals(docProcess.getItemValueString("gkT")));
					val.setCritical(docProcess.getItemValueDouble("CriticalT"));
					val.setWarning(docProcess.getItemValueDouble("WarningT"));					
				}
					m_Values.put(val.getKey(), val);
				docProcess.recycle();
				nCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -99;
		}
		m_LastUpdate = new Date();
		return nCount;
	}
	
	public Value getValue(String strKey) {
		if (m_Values == null) {
			return null;
		}
		return m_Values.get(strKey);
	}
	
	public Date getLastUpdated() {
		return m_LastUpdate;
	}
	
	public void updateStatsEntry(StatisticEntry statEntry) {
		Value val = getValue(statEntry.getKey());
		if (val != null) {
			statEntry.setBigger(val.isBigger());
			statEntry.setClearText(val.getAlias());
			statEntry.setCriticalLevel(val.getCritical());
			statEntry.setWarningLevel(val.getWarning());
		}
	}

	public void updateDatabaseEntry(StatisticEntry statEntry) {
		Value val = getValue(statEntry.getKey());
		if (val != null) {
			statEntry.setBigger(val.isBigger());
			statEntry.setCriticalLevel(val.getCritical());
			statEntry.setWarningLevel(val.getWarning());
			statEntry.setSourceDatabase(val.getSourceDatabase());
			statEntry.setSourceView(val.getSourceView());
			statEntry.setCheckValueField(val.getCustomCheck());
			statEntry.setDocumentSearchKey(val.getSourceDocumentByKey());
		}
	}
}
