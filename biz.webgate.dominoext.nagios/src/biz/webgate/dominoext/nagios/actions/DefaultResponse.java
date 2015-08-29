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
		sbCurrent.append("NAGIOS Statistic Interface by WebGate Consulting AG, 2013\n");
		sbCurrent.append("=========================================================\n");
		sbCurrent.append("nagios.stats needs a action parameter with one of the following value:\n");
		for (String strAction : ActionRegistry.getInstance().getActions()) {
			sbCurrent.append(" - " + strAction+"\n");
		}
		sbCurrent.append("\n");
		sbCurrent.append("NOTES.INI Setup\n");
		sbCurrent.append(" - Nagioscaller = "+NotesIniFactory.getNagiosCaller()+"\n");
		sbCurrent.append(" - Nagiosdb     = "+NotesIniFactory.getNagiosDB()+"\n");
		sbCurrent.append(" - DEBUG_NAGIOS = "+NotesIniFactory.getNagiosDebug()+"\n");
		sbCurrent.append(" - NAGIOS_DEFAULT_DB = "+NotesIniFactory.getDefaultDB()+"\n");
		sbCurrent.append(" - NAGIOS_DEFAULT_VIEW = "+NotesIniFactory.getDefaultView()+"\n");
		sbCurrent.append("\n");
		sbCurrent.append("Action Parameter for PluginOnly Mode\n");
		sbCurrent.append(" - Database: db=xxx.nsf (optional)\n");
		sbCurrent.append(" - View: view=xyz (optional)\n");
		sbCurrent.append(" - Document: doc=docname (required)\n");
		sbCurrent.append(" - Field to check: field=fieldname (required)\n");
		sbCurrent.append(" - Warning threshold: w= as Number (required)\n");
		sbCurrent.append(" - Critical threshold: c= as Number (required)\n");
		sbCurrent.append(" - Operator Default: Statistic < Warning/Critical = Alarm\n");
		sbCurrent.append(" - Operator isBigger: op=g (optional)\n");
		sbCurrent.append(" - Result in Percent: p=true (optional)\n");
		sbCurrent.append(" - Additional Field: addfield=fieldname (required if p=true)\n");
		sbCurrent.append(" - Field value only: vo=true (To get the Value from a Field) \n");
		sbCurrent.append(" - Supported Field only types are: text, numbers, DateTime \n");
		sbCurrent.append("\n");
		sbCurrent.append(">>>>> Example Default Result >> ..../nagios.stats?action=pluginonly&doc=CN=Server1/O=ACME&field=Disk./.Free&w=200000000000&c=2000000000&op=gr \n");
		sbCurrent.append(">>>>> Example Percent Result >> ..../nagios.stats?action=pluginonly&doc=CN=Server1/O=ACME&field=Disk./.Free&w=10&c=20&op=gr&p=true&addField=Disk./.Size\n");
		sbCurrent.append(">>>>> Example Value Only >> ..../nagios.stats?action=pluginonly&doc=CN=Server1/O=ACME&field=Disk./.Free&vo=true \n");
		sbCurrent.append("\n");


		return sbCurrent.toString();
	}

}
