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

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class StatisticParser {

	private static StatisticParser m_Parser;

	private SimpleDateFormat m_sdfCurrent = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	private StatisticParser() {

	}

	public static StatisticParser getInstance() {
		if (m_Parser == null) {
			m_Parser = new StatisticParser();
		}
		return m_Parser;
	}

	public List<StatisticEntry> getEntriesFromXML(String strXML) {
		List<StatisticEntry> lstRC = new ArrayList<StatisticEntry>();

		try {
			DocumentBuilder build = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(strXML));
			Document domDoc = build.parse(is);
			NodeList nlCurrent = domDoc.getElementsByTagName("stat");
			if (nlCurrent.getLength() == 0) {
				return lstRC;
			} else {
				for (int nCounter = 0; nCounter < nlCurrent.getLength(); nCounter++) {
					Node nStat = nlCurrent.item(nCounter);
					String strKey = nStat.getAttributes().getNamedItem("name")
							.getFirstChild().getNodeValue();
					StatisticEntry statEntry = StatisticService.getInstance()
							.getEntry(strKey);
					if (statEntry == null) {
						statEntry = new StatisticEntry();
						statEntry.setKey(strKey);
					}
					String strValue = nStat.getAttributes()
							.getNamedItem("value").getFirstChild()
							.getNodeValue();
					String strType = nStat.getAttributes().getNamedItem("type")
							.getFirstChild().getNodeValue();
					if ("0".equals(strType)) {
						statEntry.setType(StatisticEntry.TYPE_DOUBLE);
						try {
							statEntry.setValue(Double.parseDouble(strValue));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if ("1".equals(strType)) {
						statEntry.setType(StatisticEntry.TYPE_STRING);
						statEntry.setValue(strValue);
					}
					if ("2".equals(strType)) {
						statEntry.setType(StatisticEntry.TYPE_DATE);
						try {
							statEntry.setValue(m_sdfCurrent.parse(strValue));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if ("3".equals(strType)) {
						statEntry.setType(StatisticEntry.TYPE_DOUBLE);
						try {
							strValue = strValue.replace(".","");
							strValue = strValue.replace(",", ".");
							statEntry.setValue(Double.parseDouble(strValue));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					lstRC.add(statEntry);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstRC;
	}
}
