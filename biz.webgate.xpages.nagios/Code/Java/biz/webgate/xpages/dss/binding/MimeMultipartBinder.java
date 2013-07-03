package biz.webgate.xpages.dss.binding;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.ibm.xsp.http.MimeMultipart;

import lotus.domino.Document;
import lotus.domino.MIMEEntity;
import lotus.domino.Stream;

public class MimeMultipartBinder implements IBinder<MimeMultipart> {

	private static MimeMultipartBinder m_Binder;

	public void processDomino2Java(Document docCurrent, Object objCurrent,
			String strNotesField, String strJavaField,
			HashMap<String, Object> addValues) {
		try {
			MimeMultipart strValue = null;
			Method mt = objCurrent.getClass().getMethod("set" + strJavaField,
					MimeMultipart.class);

			MIMEEntity entity = docCurrent.getMIMEEntity(strNotesField);
			if (entity != null) {
				strValue = MimeMultipart.fromHTML(entity.getContentAsText());
			}
			mt.invoke(objCurrent, strValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processJava2Domino(Document docCurrent, Object objCurrent,
			String strNotesField, String strJavaField,
			HashMap<String, Object> addValues) {
		try {
			
			MimeMultipart body = getValue(objCurrent, strJavaField);
			
			Stream stream = docCurrent.getParentDatabase().getParent().createStream();
			stream.writeText(body.getHTML());
			
			MIMEEntity entity = docCurrent.getMIMEEntity(strNotesField);
			if(entity == null)
				entity = docCurrent.createMIMEEntity(strNotesField);
			
			entity.setContentFromText(stream,"text/html;charset=UTF-8", 1725);
			stream.close();
			//docCurrent.replaceItemValue(strNotesField, strValue);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static IBinder<MimeMultipart> getInstance() {
		if (m_Binder == null) {
			m_Binder = new MimeMultipartBinder();
		}
		return m_Binder;
	}

	public MimeMultipart getValue(Object objCurrent, String strJavaField) {
		try {
			Method mt = objCurrent.getClass().getMethod("get" + strJavaField);
			return (MimeMultipart) mt.invoke(objCurrent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
