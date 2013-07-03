package biz.webgate.nagios;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import lotus.domino.Session;

public class stats {

	public static String getCStats(String statsCommand, Session sesServer) {
		String statsResultConsole;
		try {
			statsResultConsole = sesServer.sendConsoleCommand(sesServer
					.getServerName(), "show stat " + statsCommand + " -xml");
			return readXML(statsResultConsole).get(0);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static List<String> getcbStats(String statsCommand, Session sesServer) {
		String statsResultConsole;
		System.out.println("CB wird geladen");
		try {
			statsResultConsole = sesServer.sendConsoleCommand(sesServer
					.getServerName(), "show stat " + statsCommand + " -xml");
			return readXML(statsResultConsole);
		} catch (Exception e) {
		}
		return null;
	}
	
	
	public static List<String> readXML(String strXML) {
		try {
			List<String> Value = new ArrayList<String>();
			DocumentBuilder dbuild = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(strXML));
			Document domDoc = dbuild.parse(is);
			NodeList nList = domDoc.getElementsByTagName("stat");
			if (nList.getLength() == 0) {
				Value.add("No Data");
				return Value;
			} else {
				if (nList.getLength() > 1) {
					Value = getcbProbestats(nList);
					return Value;
				} else {
					Node n = (Node) nList.item(0);
					String Valuetmp = n.getAttributes().getNamedItem("value")
							.getTextContent();
					Value.add(Valuetmp);
					return Value;
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static List<String> getcbProbestats(NodeList sn) {
		List<String> cbValue = new ArrayList<String>();
		for (int nCounter = 0; nCounter < sn.getLength(); nCounter++) {
			Node nStat = sn.item(nCounter);
			String Value = nStat.getAttributes().getNamedItem("name")
			.getTextContent();
			cbValue.add(Value);
		}
		return cbValue;
	}

}
