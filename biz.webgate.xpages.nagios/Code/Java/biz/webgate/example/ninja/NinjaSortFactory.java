package biz.webgate.example.ninja;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NinjaSortFactory {
	
	private static Comparator<Ninja> m_NameComp = new NinjaComparator(NinjaSessionFacade.SORT_BY_NAME);
	private static Comparator<Ninja> m_ColorComp = new NinjaComparator(NinjaSessionFacade.SORT_BY_COLOR);
	
	//Sort Methode
	public static void sortNinjas(List<Ninja> lstNinjas, int sortOrder) {

		switch (sortOrder) {
		case NinjaSessionFacade.SORT_BY_NAME:
			Collections.sort(lstNinjas, m_NameComp);
			break;
		case NinjaSessionFacade.SORT_BY_COLOR:
			Collections.sort(lstNinjas, m_ColorComp);
			break;
		}
	}
}
