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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StatisticService {

	private static StatisticService m_Service;
	private HashMap<String, StatisticEntry> m_Entries;
	private Date m_LastUpdated = null;

	private StatisticService() {

	}

	public static StatisticService getInstance() {
		if (m_Service == null) {
			m_Service = new StatisticService();
		}
		return m_Service;
	}

	public void addEntry(StatisticEntry statEntry) {
		if (m_Entries == null) {
			m_Entries = new HashMap<String, StatisticEntry>();
		}
		m_Entries.put(statEntry.getKey(), statEntry);
		m_LastUpdated = new Date();
	}

	public StatisticEntry getEntry(String strKey) {
		if (m_Entries == null) {
			return null;
		}
		return m_Entries.get(strKey);
	}

	public Date getLastUpdated() {
		return m_LastUpdated;
	}

	public List<String> getStatKeys() {
		if (m_Entries == null) {
			return null;
		}
		List<String> lstRC = new ArrayList<String>(m_Entries.keySet());
		Collections.sort(lstRC);
		return lstRC;
	}
}
