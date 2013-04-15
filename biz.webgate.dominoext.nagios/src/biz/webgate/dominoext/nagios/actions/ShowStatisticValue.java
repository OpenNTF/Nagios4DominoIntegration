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

import java.util.Date;
import java.util.Map;

import lotus.domino.DateTime;
import lotus.domino.Session;
import biz.webgate.dominoext.nagios.IServletAction;
import biz.webgate.dominoext.nagios.NagiosException;
import biz.webgate.dominoext.nagios.statistic.StatisticEntry;
import biz.webgate.dominoext.nagios.statistic.StatisticService;
import biz.webgate.dominoext.nagios.threshold.ValueService;

public class ShowStatisticValue implements IServletAction {

	@Override
	public String buildResponse(Map<?, ?> params, Session sesServer)
			throws NagiosException {
		String strRC = "";
		try {
			String strNagiosDB = sesServer.getEnvironmentString("nagiosdb");
			String[] arrStats = (String[]) params.get("stats");
			String strStats = "";
			if (arrStats == null) {
				throw new NagiosException("Missing stats parameter");
			}
			strStats = arrStats[0];
			if (strStats == null || "".equals(strStats)) {
				throw new NagiosException("Missing stats parameter");
			}
			DateTime dtCurrent = sesServer.createDateTime(new Date());
			dtCurrent.adjustMinute(-5);
			Date dt5Min = dtCurrent.toJavaDate();
			dtCurrent.adjustMinute(-55);
			Date dt60min = dtCurrent.toJavaDate();
			if (strNagiosDB != null && !"".equals(strNagiosDB)) {
				if (ValueService.getInstance().getLastUpdated() == null) {
					ValueService.getInstance().loadValues(strNagiosDB,
							sesServer);
				} else if (ValueService.getInstance().getLastUpdated()
						.before(dt60min)) {
					// WE read all 60min the Values
					ValueService.getInstance().loadValues(strNagiosDB,
							sesServer);
				}
			}
			if (StatisticService.getInstance().getLastUpdated() == null) {
				ActionRegistry.getInstance().getServletAction("readstats")
						.buildResponse(params, sesServer);
			} else if (StatisticService.getInstance().getLastUpdated()
					.before(dt5Min)) {
				ActionRegistry.getInstance().getServletAction("readstats")
						.buildResponse(params, sesServer);
			}
			StatisticEntry se = StatisticService.getInstance().getEntry(
					strStats);
			if (se == null) {
				throw new NagiosException("No statistic value for " + strStats);
			}
			ValueService.getInstance().updateStatsEntry(se);
			strRC = se.getKey() + ": " + se.getValue() + ": "
					+ se.getClearText() + " STATUS -> " + se.getStatusInfo()
					+ "\n";
		} catch (Exception e) {
			throw new NagiosException(
					"Error during ShowStatisticValue.buildValues()", e);
		}
		return strRC;
	}

}
