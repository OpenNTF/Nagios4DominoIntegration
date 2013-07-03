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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.domino.osgi.core.context.ContextInfo;

import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.Session;
import biz.webgate.dominoext.nagios.actions.ActionRegistry;
import biz.webgate.dominoext.nagios.logging.ErrorPageBuilder;

public class Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String strAction = req.getParameter("action");
		Session sesServer = null;
		try {
			NotesThread.sinitThread();
			sesServer = NotesFactory.createSession();
			String strResult = "";
			if (isAccessAllowed(req.getRemoteAddr(),
					NotesIniFactory.getNagiosCaller())) {

				strResult = ActionRegistry.getInstance()
						.getServletAction(strAction)
						.buildResponse(req.getParameterMap(), sesServer);
			} else {
				strResult = "Access form " + req.getRemoteAddr()
						+ " no allowed!";
			}
			resp.setContentType("text/plain");
			PrintWriter pwCurrent = resp.getWriter();
			pwCurrent.append(strResult);
			pwCurrent.close();

		} catch (Exception e) {
			ErrorPageBuilder.getInstance()
					.processError(resp, e.getMessage(), e);
		} finally {
			try {
				if (sesServer != null) {
					sesServer.recycle();
				}
				NotesThread.stermThread();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private boolean isAccessAllowed(String strCallerIP, String strNotesINIIP) {
		try {
			InetAddress inCurrent = InetAddress.getByName(strCallerIP);
			if (inCurrent.isAnyLocalAddress() || inCurrent.isLoopbackAddress()) {
				return true;
			}
			if (NetworkInterface.getByInetAddress(inCurrent) != null) {
				return true;
			}
			if (strNotesINIIP != null && !"".equals(strNotesINIIP)) {
				List<String> lstAddr = Arrays.asList(strNotesINIIP.split(";"));
				for (String strAdrCheck : lstAddr) {
					if (strAdrCheck.equals(strCallerIP)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
