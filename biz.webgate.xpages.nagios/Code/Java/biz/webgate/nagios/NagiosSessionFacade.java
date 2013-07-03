package biz.webgate.nagios;

import java.util.List;
import java.util.UUID;
import biz.webgate.nagios.stats;


import com.ibm.xsp.extlib.renderkit.html_extended.DijitContainerCompatibleRichTextRenderer;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public class NagiosSessionFacade {

	public NagiosSetting CreateNewSetting() {
		NagiosSetting NewNagiosSetting = new NagiosSetting();
	    NewNagiosSetting.setID(UUID.randomUUID().toString());
		return NewNagiosSetting;
	}
	
	public boolean saveNagiosSetting(NagiosSetting NGSetting) {
		return NagiosStorageService.getInstance().saveNagiosSetting(NGSetting, ExtLibUtil.getCurrentSession());		
	}
	
	public List<NagiosSetting> getAllNagiosSetting() {
		List<NagiosSetting> allSetting = NagiosStorageService.getInstance().getAllNagiosSetting(ExtLibUtil.getCurrentSession());
		NagiosSort.sortNagiosSetting(allSetting);
		return allSetting;
	}
	
	public NagiosSetting getNagiosSettingByID(String curNagiosSettingID) {
		return NagiosStorageService.getInstance().getNGSettingByID(curNagiosSettingID, ExtLibUtil.getCurrentSession());
	}
	
	public boolean DeleteCurDocument(String curNagiosSettingID) {
		return NagiosStorageService.getInstance().deleteNGSetting(curNagiosSettingID,ExtLibUtil.getCurrentSession());
	}
	
	public String getCurrentValue(String statsCommand) {
		return stats.getCStats(statsCommand, ExtLibUtil.getCurrentSession());
		
	}
	public List<String> getcbValue(String cbProbe) {
		return stats.getcbStats(cbProbe, ExtLibUtil.getCurrentSession());
		}
}
