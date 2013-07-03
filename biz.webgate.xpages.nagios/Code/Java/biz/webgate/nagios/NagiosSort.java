package biz.webgate.nagios;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import biz.webgate.nagios.comparators.NagiosSettingAliasName;
import biz.webgate.nagios.NagiosSetting;

public class NagiosSort {	
	
	private static Comparator<NagiosSetting> m_AliasNameComp = new NagiosSettingAliasName();

	public static void sortNagiosSetting(List<NagiosSetting> AllSetting) {
		Collections.sort(AllSetting, m_AliasNameComp);
	
	}
}
