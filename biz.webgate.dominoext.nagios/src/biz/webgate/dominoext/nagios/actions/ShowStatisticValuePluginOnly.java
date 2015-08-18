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
		String result;
		RequestStatistic requestStatistic = new RequestStatistic().setParameter(params);
		
		System.out.println("DB= "+requestStatistic.getDB());
		System.out.println("View= "+requestStatistic.getView());
		System.out.println("Doc= "+requestStatistic.getDoc());
		System.out.println("Field= "+requestStatistic.getField());
		System.out.println("Warning= "+requestStatistic.getWarning());
		System.out.println("Critical= "+requestStatistic.getCritical());
		System.out.println("Operator= "+requestStatistic.getIsBigger());
		System.out.println("Percent= "+requestStatistic.getPercent());
		System.out.println("Additional Field= "+requestStatistic.getAdditionalField());
		System.out.println("Status= "+requestStatistic.getStatus());

		ResultStatistic resultStatistic = (ResultStatistic) ReadDatabaseDocumentPluginOnly.getNotesDocument(requestStatistic, sesCurrent);
				
		resultStatistic.calculateResult(requestStatistic);
		
		result = resultStatistic.getNAGIOSResponse(requestStatistic.getPercent());
		
		return result;
	}

}
