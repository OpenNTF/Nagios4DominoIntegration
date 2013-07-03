package biz.webgate.example.ninja;

import java.util.List;
import java.util.UUID;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class NinjaSessionFacade {
	public final static int SORT_BY_NAME = 1; 
	public final static int SORT_BY_COLOR = 2;
 
	public Ninja createNewNinja() { 
		Ninja ninRC = new Ninja();
		ninRC.setId(UUID.randomUUID().toString());
		return ninRC;
	}

	public List<Ninja> getAllNinjas(int sortOrder) {
		List<Ninja> lstAll = NinjaStorageService.getInstance().getAllNinjas(ExtLibUtil.getCurrentSession());
		NinjaSortFactory.sortNinjas(lstAll, sortOrder);
		return lstAll;
	}  

	public boolean saveNinja(Ninja niSave) {
		return NinjaStorageService.getInstance().saveNinja(niSave, ExtLibUtil.getCurrentSession());
	}
	
	public Ninja getNinjaById(String strNinjaId)
	{
		return NinjaStorageService.getInstance().getNinjaById(strNinjaId, ExtLibUtil.getCurrentSession());
	}
}
