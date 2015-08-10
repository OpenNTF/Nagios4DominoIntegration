package biz.webgate.dominoext.nagios.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import lotus.domino.Session;
import biz.webgate.dominoext.nagios.IServletAction;
import biz.webgate.dominoext.nagios.NotesIniFactory;

public class CheckDiskWrite implements IServletAction{

	@Override
	public String buildResponse(Map<?, ?> params, Session sesCurrent) {
			String result = "";
			try {
				System.out.println(System.getProperty("os.name").toLowerCase());		
				
				String dataDirectory = NotesIniFactory.getDataDirectory();
				String fileName = "nagios_probe.txt";
				String text = "Nagios Probe Test New";
				File dir = new File(dataDirectory);
				File file = new File(dir, fileName);
				FileWriter fileWriter = new FileWriter (file);
				BufferedWriter out = new BufferedWriter(fileWriter); 
				out.write(text);
			    out.close();
				return result ="OK";
			} catch (Exception e) {
				// TODO: handle exception
			}
				
		// TODO Auto-generated method stub
		return result = "fail";
	}
	
	

}
