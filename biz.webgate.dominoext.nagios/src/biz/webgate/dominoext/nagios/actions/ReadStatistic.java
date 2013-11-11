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
package biz.webgate.dominoext.nagios.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lotus.domino.Session;
import biz.webgate.dominoext.nagios.IServletAction;
import biz.webgate.dominoext.nagios.NagiosException;
import biz.webgate.dominoext.nagios.statistic.StatisticEntry;
import biz.webgate.dominoext.nagios.statistic.StatisticParser;
import biz.webgate.dominoext.nagios.statistic.StatisticService;

public class ReadStatistic implements IServletAction {
	private ArrayList<String> m_lstPackages = null;

	@Override
	public String buildResponse(Map<?, ?> params, Session sesServer)
			throws NagiosException {
		int nCount = 0;
		if (m_lstPackages == null) {
			iniPackages();
		}
		try {
			String strResult;
			for (String strStatistic : m_lstPackages) {
				strResult = sesServer.sendConsoleCommand(
						sesServer.getServerName(), "!show stat " + strStatistic
								+ " -xml");
				List<StatisticEntry> lstRC = StatisticParser.getInstance()
						.getEntriesFromXML(strResult,
								sesServer.getInternational().getDecimalSep(),
								sesServer.getInternational().getThousandsSep());
				if (lstRC != null) {
					for (StatisticEntry entry : lstRC) {
						StatisticService.getInstance().addEntry(entry);
						nCount++;
					}
				}
			}
		} catch (Exception e) {
			throw new NagiosException(
					"Error during ReadStatistic.buildValues()", e);
		}
		return nCount + " Entries from statistics read";
	}

	private void iniPackages() {
		m_lstPackages = new ArrayList<String>();
		m_lstPackages.add("AGENT");
		m_lstPackages.add("CALENDAR");
		m_lstPackages.add("DAOS");
		m_lstPackages.add("DATABASE");
		m_lstPackages.add("DISK");
		m_lstPackages.add("DOMINO");
		m_lstPackages.add("EVENT");
		m_lstPackages.add("FT");
		m_lstPackages.add("HTTP");
		m_lstPackages.add("MAIL");
		m_lstPackages.add("MEM");
		m_lstPackages.add("MONITOR");
		m_lstPackages.add("NET");
		m_lstPackages.add("PLATFORM");
		m_lstPackages.add("SERVER");
		m_lstPackages.add("STATS");
		m_lstPackages.add("UPDATE");

	}
}
