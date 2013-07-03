package biz.webgate.nagios;

import java.util.ArrayList;
import java.util.List;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.Session;
import lotus.domino.View;
import biz.webgate.nagios.NagiosStorageService;
import biz.webgate.nagios.NagiosSetting;
import biz.webgate.xpages.dss.DominoStorageService;

public class NagiosStorageService {

	private static NagiosStorageService m_Service;

	private NagiosStorageService() {

	}

	public static NagiosStorageService getInstance() {
		if (m_Service == null) {
			m_Service = new NagiosStorageService();
		}
		return m_Service;
	}

	public boolean saveNagiosSetting(NagiosSetting NagiosSetting,
			Session sesCurrent) {
		try {
			return DominoStorageService.getInstance().saveObject(NagiosSetting,
					sesCurrent.getCurrentDatabase());

		} catch (Exception e) {

		}
		System.out.println("doc saved");
		return false;
	}

	public List<NagiosSetting> getAllNagiosSetting(Session sesCurrent) {
		List<NagiosSetting> lstNagiosSetting = new ArrayList<NagiosSetting>();
		try {
			Database curDatabase = sesCurrent.getCurrentDatabase();
			View curView = curDatabase.getView("lupNagiosSettings");
			Document curDocument = curView.getFirstDocument();
			while (curDocument != null) {
				Document docNext = curDocument;
				curDocument = curView.getNextDocument(curDocument);
				NagiosSetting newSetting = new NagiosSetting();
				if (DominoStorageService.getInstance().getObjectWithDocument(
						newSetting, docNext)) {
					lstNagiosSetting.add(newSetting);
				}
				docNext.recycle();
			}
			curView.recycle();
			curDatabase.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstNagiosSetting;
	}

	public NagiosSetting getNGSettingByID(String ID, Session sesCurrent) {
		NagiosSetting curNGdoc = new NagiosSetting();
		curNGdoc.setID(ID);
		try {
			Database curDatabase = sesCurrent.getCurrentDatabase();
			DominoStorageService.getInstance().getObject(curNGdoc, ID, curDatabase);			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return curNGdoc;
	}
	
	public boolean deleteNGSetting(String ID, Session sesCurrent) {
//		NagiosSetting curNGdoc = new NagiosSetting();
//		curNGdoc.setID(ID);
		try {
			Database curDatabase = sesCurrent.getCurrentDatabase();
			View curView = curDatabase.getView("lupNagiosSettings");
			Document curDocument = curView.getDocumentByKey(ID);
			curDocument.remove(true);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
