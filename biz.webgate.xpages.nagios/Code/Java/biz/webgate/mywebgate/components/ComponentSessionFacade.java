package biz.webgate.mywebgate.components;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.context.FacesContext;

public class ComponentSessionFacade implements Serializable {

	private static final long serialVersionUID = 1L;
	private static HashMap<String, String> m_MultiValueFields = new HashMap<String, String>();

	private static HashMap<String, HashMap<String, Object>> m_LoadedCCs = new HashMap<String, HashMap<String, Object>>();

	@SuppressWarnings("unchecked")
	public void setValue(Object obj, String strFieldName, Object values) {
		try {
			String strType = "";
			Method mt = null;

			m_MultiValueFields.get(obj.getClass().getSimpleName() + "|"
					+ strFieldName);
			if (m_MultiValueFields.containsKey(obj.getClass().getSimpleName()
					+ "|" + strFieldName)) {
				strType = m_MultiValueFields.get(obj.getClass().getSimpleName()
						+ "|" + strFieldName);
			} else {
				strType = values.getClass().getSimpleName();
			}

			if ("List".equals(strType)) {
				mt = obj.getClass().getMethod("set" + strFieldName, List.class);
				if (values.getClass().getSimpleName().equals("String")) {
					List<String> lstrValue = new ArrayList<String>();
					lstrValue.add((String) values);

					values = lstrValue;
				}
			} else if ("ArrayList".equals(strType)) {
				mt = obj.getClass().getMethod("set" + strFieldName,
						ArrayList.class);
				if (values.getClass().getSimpleName().equals("String")) {
					ArrayList<String> lstrValue = new ArrayList<String>();
					lstrValue.add((String) values);

					values = lstrValue;
				} else if (values.getClass().getSimpleName().equals("Vector")) {
					Vector<String> vec = (Vector<String>) values;
					ArrayList<String> lstrValue = new ArrayList<String>();
					;
					for (String value : vec) {
						lstrValue.add(value);
					}
					values = lstrValue;
				}
			} else if ("String[]".equals(strType)) {
				mt = obj.getClass().getMethod("set" + strFieldName,
						new Class[] { String[].class });

				String[] strValue;

				if (values.getClass().getSimpleName().equals("Vector")) {
					Vector<String> vec = (Vector<String>) values;
					strValue = (String[]) vec.toArray(new String[vec.size()]);
					values = strValue;
				} else if (values.getClass().getSimpleName().equals("String")) {
					strValue = new String[1];
					strValue[0] = (String) values;
					values = strValue;
				}

			} else {
				mt = obj.getClass().getMethod("set" + strFieldName,
						String.class);
			}
			mt.invoke(obj, values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object getValue(Object obj, String strFieldName) {
		try {
			Method mt = obj.getClass().getMethod("get" + strFieldName);
			if (!m_MultiValueFields.containsKey(obj.getClass().getSimpleName()
					+ "|" + strFieldName)) {
				m_MultiValueFields.put(obj.getClass().getSimpleName() + "|"
						+ strFieldName, mt.getReturnType().getSimpleName());
			}
			Object values = mt.invoke(obj);
			return values;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public static Object getViewObject(String objName) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		// ExternalContext extCtx = ctx.getExternalContext();
		// Map<String, Object> sessionMap = extCtx.getSessionMap();
		Map<String, Object> viewMap = ctx.getViewRoot().getViewMap();
		return viewMap.get(objName);
	}

	public void updateMultiValueData(Object objObject, String strObjectAlias) {
		ArrayList<String> valuesToClean = new ArrayList<String>();

		for (HashMap<String, Object> ccId : m_LoadedCCs.values()) {
			if (ccId.get("ObjectAlias").equals(strObjectAlias)) {
				Object objValues = getViewObject("textArrayValue"
						+ ccId.get("Id"));
				String strFieldName = (String) ccId.get("FieldName");
				setValue(objObject, strFieldName, objValues);

				valuesToClean.add(ccId.get("ObjectAlias") + "|"
						+ ccId.get("Id"));
			}
		}

	/*	for (String valueToClean : valuesToClean) {
			m_LoadedCCs.remove(valueToClean);
		}
	*/
	}

	public void addCustomControl(String strId, Object objObject,
			String strObjectAlias, String strFieldName) {
		if (!m_LoadedCCs.containsKey(strObjectAlias + "|" + strId)) {
			HashMap<String, Object> curCC = new HashMap<String, Object>();
			curCC.put("ObjectAlias", strObjectAlias);
			curCC.put("Id", strId);
			curCC.put("Object", objObject);
			curCC.put("FieldName", strFieldName);

			m_LoadedCCs.put(strObjectAlias + "|" + strId, curCC);
		}
	}
}
