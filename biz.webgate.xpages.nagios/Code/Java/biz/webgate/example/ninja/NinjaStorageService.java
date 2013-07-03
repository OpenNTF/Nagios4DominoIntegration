package biz.webgate.example.ninja;

import java.util.ArrayList;
import java.util.List;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.Session;
import lotus.domino.View;
import biz.webgate.xpages.dss.DominoStorageService;

public class NinjaStorageService {

	private static NinjaStorageService m_Service;

	private NinjaStorageService() {

	}

	public static NinjaStorageService getInstance() {
		if (m_Service == null) {
			m_Service = new NinjaStorageService();
		}
		return m_Service;
	}

	public List<Ninja> getAllNinjas(Session sesCurrent) {
		List<Ninja> lstNinja = new ArrayList<Ninja>();
		try {
			Database ndbCurrent = sesCurrent.getCurrentDatabase();
			View viwNinjas = ndbCurrent.getView("lupNinjasById");
			Document docNext = viwNinjas.getFirstDocument();
			while (docNext != null) {
				Document docProcess = docNext;
				docNext = viwNinjas.getNextDocument(docNext);
				Ninja ninNew = new Ninja();
				if (DominoStorageService.getInstance().getObjectWithDocument(
						ninNew, docProcess)) {
					lstNinja.add(ninNew);
				}
				docProcess.recycle();
			}
			viwNinjas.recycle();
			ndbCurrent.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lstNinja;
	}

	public boolean saveNinja(Ninja niSave, Session sesCurrent) {
		try {
			return DominoStorageService.getInstance().saveObject(niSave,
					sesCurrent.getCurrentDatabase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Ninja getNinjaById(String strNinjaId, Session sesCurrent) {
		Ninja ninjaCurrent = new Ninja();
		ninjaCurrent.setId(strNinjaId);
		try {
			Database ndbCurrent = sesCurrent.getCurrentDatabase();
			if(!DominoStorageService.getInstance().getObject(ninjaCurrent,
					strNinjaId, ndbCurrent))
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ninjaCurrent;
	}
}
