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
package biz.webgate.dominoext.nagios;

import com.ibm.domino.osgi.core.context.ContextInfo;

public class NotesIniFactory {

	
	public static String getNagiosDB () {
		try {
			return ContextInfo.getEnvironmentString("nagiosdb");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNagiosCaller () {
		try {
			return ContextInfo.getEnvironmentString("nagioscaller");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static String getNagiosDebug () {
		try {
			return ContextInfo.getEnvironmentString("DEBUG_NAGIOS");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDataDirectory () {
		try {
			return ContextInfo.getEnvironmentString("Directory");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDefaultDB () {
		try {
			return ContextInfo.getEnvironmentString("nagios_default_db");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getDefaultView () {
		try {
			return ContextInfo.getEnvironmentString("nagios_default_view");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
