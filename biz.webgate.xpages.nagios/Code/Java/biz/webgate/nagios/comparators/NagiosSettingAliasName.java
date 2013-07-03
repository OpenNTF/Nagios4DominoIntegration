package biz.webgate.nagios.comparators;

import java.util.Comparator;
import biz.webgate.nagios.NagiosSetting;

public class NagiosSettingAliasName implements Comparator<NagiosSetting>{
		public int compare(NagiosSetting o1, NagiosSetting o2) {
			return o1.getAliasName().toLowerCase().compareTo(o2.getAliasName().toLowerCase());
		}
}