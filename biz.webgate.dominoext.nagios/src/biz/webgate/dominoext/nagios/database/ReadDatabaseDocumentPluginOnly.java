package biz.webgate.dominoext.nagios.database;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.Session;
import lotus.domino.View;
import biz.webgate.dominoext.nagios.statistic.RequestStatistic;
import biz.webgate.dominoext.nagios.statistic.ResultStatistic;

public class ReadDatabaseDocumentPluginOnly {

	
	
	public static ResultStatistic getNotesDocument(RequestStatistic requestStatistic, Session sesServer) {
		ResultStatistic resultStatistic = new ResultStatistic();
		
		try {
			Database database = sesServer.getDatabase(sesServer.getServerName(), requestStatistic.getDB());
			View view = database.getView(requestStatistic.getView());
			Document document = view.getDocumentByKey(requestStatistic.getDoc());
			
			if (document != null) {

				resultStatistic.calculateFieldValue(document.getItemValue(requestStatistic.getField()), 
					document.getFirstItem(requestStatistic.getField()).getType());

				if (requestStatistic.getPercent()) {
					resultStatistic.calculateAdditionalFieldValue(document.getItemValue(requestStatistic.getAdditionalField()), 
						document.getFirstItem(requestStatistic.getAdditionalField()).getType());
				}	

				resultStatistic.setDocumentCreated(document.getCreated());
				document.recycle();				
			}else{
				resultStatistic.setErrorMessage("No Document found");
			}
			database.recycle();
			view.recycle();
		} catch (NotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStatistic;
	}
	
	public static ResultStatistic getNotesDocValue(RequestStatistic requestStatistic, Session sesServer) {
		ResultStatistic resultStatistic = new ResultStatistic();
		
		try {
			Database database = sesServer.getDatabase(sesServer.getServerName(), requestStatistic.getDB());
			View view = database.getView(requestStatistic.getView());
			Document document = view.getDocumentByKey(requestStatistic.getDoc());
			
			if (document != null) {
				resultStatistic.setSingleValue(document.getItemValue(requestStatistic.getField()), 
						document.getFirstItem(requestStatistic.getField()).getType());

				resultStatistic.setDocumentCreated(document.getCreated());
				document.recycle();				
			}else{
				resultStatistic.setErrorMessage("No Document found");
			}
			database.recycle();
			view.recycle();
		} catch (NotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStatistic;
	}

	
}
