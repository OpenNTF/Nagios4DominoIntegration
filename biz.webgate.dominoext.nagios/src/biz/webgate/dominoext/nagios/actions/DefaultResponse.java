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

import java.util.Map;

import lotus.domino.Session;
import biz.webgate.dominoext.nagios.IServletAction;
import biz.webgate.dominoext.nagios.NagiosException;
import biz.webgate.dominoext.nagios.NotesIniFactory;

public class DefaultResponse implements IServletAction {

	@Override
	public String buildResponse(Map<?, ?> params, Session sesCurrent)
			throws NagiosException {
		StringBuilder sbCurrent = new StringBuilder();
		sbCurrent
				.append("NAGIOS Statistic Interface by WebGate Consulting AG, 2013\n");
		sbCurrent
				.append("=========================================================\n");
		sbCurrent
				.append("nagios.stats needs a action parameter with one of the following value:");
		for (String strAction : ActionRegistry.getInstance().getActions()) {
			sbCurrent.append(" - " + strAction+"\n");
		}
		sbCurrent.append("\n");
		sbCurrent.append("NOTES.INI Setup\n");
		sbCurrent.append(" - Nagioscaller = "+NotesIniFactory.getNagiosCaller()+"\n");
		sbCurrent.append(" - Nagiosdb     = "+NotesIniFactory.getNagiosDB()+"\n");
		sbCurrent.append(" - DEBUG_NAGIOS = "+NotesIniFactory.getNagiosDebug()+"\n");
		
		return sbCurrent.toString();
	}

}
