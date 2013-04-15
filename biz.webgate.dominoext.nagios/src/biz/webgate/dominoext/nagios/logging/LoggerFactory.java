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
package biz.webgate.dominoext.nagios.logging;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.domino.osgi.core.context.ContextInfo;

public class LoggerFactory {
	private static HashMap<String, Logger> m_RegistredLoggers = new HashMap<String, Logger>();

	private static int m_logLevel = -1;

	public static Logger getLogger(String strName) {
		try {
			if (m_RegistredLoggers.containsKey(strName)) {
				return m_RegistredLoggers.get(strName);
			}
			Logger logRC = java.util.logging.Logger.getAnonymousLogger();
			if (m_logLevel == -1) {
				checkLogLevel();
			}
			logRC.setLevel(getLogLevel(getLogLevelForClass(strName)));
			ConsoleHandler ch = new ConsoleHandler(strName,
					getLogLevel(m_logLevel));
			logRC.addHandler(ch);
			m_RegistredLoggers.put(strName, logRC);
			return logRC;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Level getLogLevel(int nLevel) {
		switch (nLevel) {
		case 0:
			return Level.OFF;
		case 1:
			return Level.SEVERE;
		case 2:
			return Level.WARNING;
		case 3:
			return Level.INFO;
		case 4:
			return Level.FINE;
		case 5:
			return Level.FINER;
		case 6:
			return Level.FINEST;

		}
		return Level.ALL;

	}

	private static void checkLogLevel() {
		try {

			String strNagiosLogLevel = ContextInfo
					.getEnvironmentString("DEBUG_NAGIOS");
			if (strNagiosLogLevel == null || "".equals(strNagiosLogLevel)) {
				m_logLevel = 1;
			}

			try {
				if (strNagiosLogLevel != null && !"".equals(strNagiosLogLevel)) {
					m_logLevel = Integer.parseInt(strNagiosLogLevel);
				}
			} catch (Exception e) {
				m_logLevel = 0;
			}
			System.out.println("NAGIOS     LOG-LEVEL is set to: "
					+ strNagiosLogLevel + " / " + m_logLevel);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int getLogLevelForClass(String strClass) {
		return m_logLevel;
	}
}
