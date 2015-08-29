package biz.webgate.dominoext.nagios.actions;

import java.util.Map;

import lotus.domino.Session;
import biz.webgate.dominoext.nagios.IServletAction;
import biz.webgate.dominoext.nagios.NagiosException;
import biz.webgate.dominoext.nagios.database.ReadDatabaseDocumentPluginOnly;
import biz.webgate.dominoext.nagios.statistic.RequestStatistic;
import biz.webgate.dominoext.nagios.statistic.ResultStatistic;

public class ShowStatisticValuePluginOnly implements IServletAction{

	@Override
	public String buildResponse(Map<?, ?> params, Session sesCurrent)
			throws NagiosException {
		String result="";
		String getValueOnly="";
		ResultStatistic resultStatistic;
		
		if (params.get("vo") != null & params.get("vo") != "") {
			String[] arrValue = (String[]) params.get("vo");
			getValueOnly = (arrValue[0]);
		}
		System.out.println("Value only " +getValueOnly);
		if(getValueOnly.equals("true")) {
			RequestStatistic requestStatistic = new RequestStatistic().setParameterGetOnly(params);
			if (requestStatistic.getStatus() == null | requestStatistic.getStatus() == "") {
				resultStatistic = (ResultStatistic) ReadDatabaseDocumentPluginOnly.getNotesDocValue(requestStatistic, sesCurrent);
			}else{
				result = requestStatistic.getStatus();
				return result;
			}
			result = resultStatistic.getNAgiosResponseSingleValue();

			return result;
		}else{
			RequestStatistic requestStatistic = new RequestStatistic().setParameter(params);
		
			if (requestStatistic.getStatus() == null | requestStatistic.getStatus() == "") {
				resultStatistic = (ResultStatistic) ReadDatabaseDocumentPluginOnly.getNotesDocument(requestStatistic, sesCurrent);
			}else{
				result = requestStatistic.getStatus();
				return result;
			}
			
			if (resultStatistic.getErrorMessage() != null & resultStatistic.getErrorMessage() != "") {
				return resultStatistic.getNAGIOSErrorMessage();
			}
			
			if (resultStatistic.getStatus() == null | resultStatistic.getStatus() == "") {
				resultStatistic.calculateResult(requestStatistic);
			}else{
				return resultStatistic.getStatus();
			}
			
			result = resultStatistic.getNAGIOSResponse(requestStatistic.getPercent());
			return result;
			}
		}

}
