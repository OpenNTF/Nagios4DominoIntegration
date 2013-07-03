package biz.webgate.example.ninja;

import java.util.Comparator;

public class NinjaComparator implements Comparator<Ninja> {
		private int m_SortOrder = NinjaSessionFacade.SORT_BY_NAME;
		
		public NinjaComparator(int iSortOrder){
			m_SortOrder = iSortOrder;
		}
		
		public int compare(Ninja o1, Ninja o2) {
			String s1 ="";
			String s2 = "";
			switch (m_SortOrder) {
			case NinjaSessionFacade.SORT_BY_COLOR:
				 s1 = o1.getColor().toLowerCase();
				 s2 = o2.getColor().toLowerCase();
				break;
			case NinjaSessionFacade.SORT_BY_NAME:
				s1 = o1.getName().toLowerCase();
				s2 = o2.getName().toLowerCase();
			default:
				break;
			}
			
			return s1.compareTo(s2);
		}


		
	}
