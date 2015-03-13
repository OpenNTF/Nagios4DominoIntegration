package biz.webgate.dominoext.nagios.actions;

import java.util.Date;
import java.util.Map;

import lotus.domino.DateTime;
import lotus.domino.Session;
import biz.webgate.dominoext.nagios.IServletAction;
import biz.webgate.dominoext.nagios.NagiosException;
import biz.webgate.dominoext.nagios.NotesIniFactory;
import biz.webgate.dominoext.nagios.database.ReadDatabaseDocument;
import biz.webgate.dominoext.nagios.statistic.StatisticEntry;
import biz.webgate.dominoext.nagios.threshold.ValueService;

public class ShowStatisticValueFromDatabase implements IServletAction{

	@Override
	public String buildResponse(Map<?, ?> params, Session sesServer)
			throws NagiosException {
		String strRC = "";
		try {
			String strNagiosDB = NotesIniFactory.getNagiosDB();
			String[] arrProbeID = (String[]) params.get("id");
			String strProbeID = "";
			if (arrProbeID == null) {
				throw new NagiosException("Missing stats parameter");
			}
			strProbeID = arrProbeID[0];
			if (strProbeID == null || "".equals(strProbeID)) {
				throw new NagiosException("Missing stats parameter");
			}			
			DateTime dtCurrent = sesServer.createDateTime(new Date());
			dtCurrent.adjustMinute(-60);
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
			StatisticEntry se = ReadDatabaseDocument.readDatabaseDocument(strProbeID, sesServer);
			if (se == null) {
				throw new NagiosException("No statistic value for " + strProbeID);
			}
			strRC = se.getNAGIOSDatabaseResponse();
			
		return strRC;	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return null;
	}

}
