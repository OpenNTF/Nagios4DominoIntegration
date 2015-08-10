package biz.webgate.dominoext.nagios.database;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.Session;
import lotus.domino.View;
import biz.webgate.dominoext.nagios.statistic.StatisticEntry;
import biz.webgate.dominoext.nagios.threshold.ValueService;

public class ReadDatabaseDocument {
		
	public static StatisticEntry readDatabaseDocument(String ProbeID, Session sesServer) {
		StatisticEntry se = new StatisticEntry();
		se.setKey(ProbeID);
		ValueService.getInstance().updateDatabaseEntry(se);
		
		try {
			Database database = sesServer.getDatabase(sesServer.getServerName(), se.getSourceDatabase());
			View view = database.getView(se.getSourceView());
			Document document = view.getDocumentByKey(se.getDocumentSearchKey());
			
			se.setValue(document.getItemValueDouble(se.getCheckValueField()));
			se.setItemType(document.getFirstItem(se.getCheckValueField()).getType());
			se.setDocumentCreated(document.getCreated());

			view.recycle();
			database.recycle();
			sesServer.recycle();
		} catch (NotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return se;
	}
}
