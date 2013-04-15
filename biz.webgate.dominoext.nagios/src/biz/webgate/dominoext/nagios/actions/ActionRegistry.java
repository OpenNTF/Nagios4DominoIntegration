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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import biz.webgate.dominoext.nagios.IServletAction;

public class ActionRegistry {

	private static ActionRegistry m_Registry;
	private HashMap<String, IServletAction> m_Actions;

	private ActionRegistry() {

	}

	public static ActionRegistry getInstance() {
		if (m_Registry == null) {
			m_Registry = new ActionRegistry();
		}
		return m_Registry;
	}

	public IServletAction getServletAction(String strAction) {
		IServletAction isaRC = null;
		if (m_Actions == null) {
			initActions();
		}
		if (strAction == null || "".equals(strAction)) {
			strAction = "default";
		}
		isaRC = m_Actions.get(strAction.toLowerCase());
		if (isaRC == null) {
			isaRC = m_Actions.get("default");
		}
		return isaRC;
	}

	private void initActions() {
		m_Actions = new HashMap<String, IServletAction>();
		m_Actions.put("readthreshold", new LoadThresholdValues());
		m_Actions.put("readstats", new ReadStatistic());
		m_Actions.put("showall", new ShowAllStatistic());
		m_Actions.put("nagios", new ShowStatisticValue());
		m_Actions.put("default", new DefaultResponse());
	}

	public List<String> getActions() {
		if (m_Actions == null) {
			initActions();
		}
		List<String> lstRC = new ArrayList<String>(m_Actions.keySet());
		Collections.sort(lstRC);
		return lstRC;
	}
}
