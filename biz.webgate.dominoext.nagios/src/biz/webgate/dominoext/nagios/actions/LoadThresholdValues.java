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
import biz.webgate.dominoext.nagios.threshold.ValueService;

public class LoadThresholdValues implements IServletAction {

	@Override
	public String buildResponse(Map<?, ?> params, Session sesCurrent) throws NagiosException{
		try {
			String strNagiosDB = sesCurrent.getEnvironmentString("nagiosdb");
			if (strNagiosDB == null || "".equals(strNagiosDB)) {
				throw new NagiosException("notes.ini value nagiosdb is not set!");
			}
			int nCount = ValueService.getInstance().loadValues(strNagiosDB,
					sesCurrent);
			if (nCount < 0) {
				throw new NagiosException("Error during ValueService.loadValues() return code is: "+nCount);
			}
			return nCount +" Values read from nagiosdb ("+strNagiosDB +")";

		} catch (Exception e) {
			throw new NagiosException("Error during LoadThrsholdValues.buildValues()",e);
		}
	}

}
