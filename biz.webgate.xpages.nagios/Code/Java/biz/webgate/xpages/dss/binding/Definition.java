/*
 * © Copyright WebGate Consulting AG, 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package biz.webgate.xpages.dss.binding;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ibm.xsp.http.MimeMultipart;

public class Definition {

	public static IBinder<String> STRING = StringBinder.getInstance();
	public static IBinder<Integer> INTEGER = IntBuilder.getInstance();
	public static IBinder<Double> DOUBLE = DoubleBinder.getInstance();
	public static IBinder<Double[]> DOUBLE_ARR = DoubleArrayBinder
	.getInstance();
	public static IBinder<Long> LONG = LongBinder.getInstance();
	public static IBinder<String[]> STRING_ARR = StringArrayBinder
			.getInstance();
	public static IBinder<Date> DATE = DateBinder.getInstance();
	public static IBinder<Boolean> BOOLEAN = BooleanBinder.getInstance();
	public static IBinder<List<String>> STRING_LIST = ListStringBinder.getInstance();

	public static IBinder<Date> FORMULA_DATE = FormulaDateBinder.getInstance();
	public static IBinder<String> FORMULA_STRING = FormulaStringBinder
			.getInstance();
	public static IBinder<Double> FORMULA_DOUBLE = FormulaDoubleBinder
			.getInstance();
	public static IBinder<MimeMultipart> MIME_MULTIPART = MimeMultipartBinder.getInstance();
	
	
	private String m_NotesField;
	private String m_JavaField;
	private IBinder<?> m_Binder;
	private HashMap<String, Object> m_AdditionalValues;

	public Definition(String notesField, String javaField, IBinder<?> binCurrent, HashMap<String, Object> addValues) {
		m_NotesField = notesField;
		m_JavaField = javaField;
		m_Binder = binCurrent;
		m_AdditionalValues = addValues;
	}

	public String getNotesField() {
		return m_NotesField;
	}

	public void setNotesField(String notesField) {
		m_NotesField = notesField;
	}

	public String getJavaField() {
		return m_JavaField;
	}

	public void setJavaField(String javaField) {
		m_JavaField = javaField;
	}

	public IBinder<?> getBinder() {
		return m_Binder;
	}

	public void setBinder(IBinder<?> binCurrent) {
		m_Binder = binCurrent;
	}

	public void setAdditionalValues(HashMap<String, Object> additionalValues) {
		m_AdditionalValues = additionalValues;
	}

	public HashMap<String, Object> getAdditionalValues() {
		return m_AdditionalValues;
	}

	public static IBinder<?> getBinder(Class<?> clCurrent) {
		//System.out.println(clCurrent);
		if (clCurrent.equals(Boolean.class) || clCurrent.equals(Boolean.TYPE)) {
			return BOOLEAN;
		}
		if (clCurrent.equals(String.class)) {
			return STRING;
		}
		if (clCurrent.equals(Integer.class) || clCurrent.equals(Integer.TYPE)) {
			return INTEGER;
		}
		if (clCurrent.equals(Double.class) || clCurrent.equals(Double.TYPE)) {
			return DOUBLE;
		}
		if (clCurrent.equals(Double[].class)) {
			return DOUBLE_ARR;
		}
		if (clCurrent.equals(Long.class) || clCurrent.equals(Long.TYPE)) {
			return LONG;
		}
		if (clCurrent.equals(Date.class)) {
			return DATE;
		}
		if (clCurrent.equals(String[].class)) {
			return STRING_ARR;
		}
		if (clCurrent.equals(List.class)) {
			//TODO: Check for the genericType but actually we recomend bizLogic by Programmer
			return STRING_LIST;
		}
		if (clCurrent.equals(MimeMultipart.class)) {
			return MIME_MULTIPART;
		}
		return null;
	}
	
	public static IBinder<?> getFormulaBinder(Class<?> clCurrent) {
		if (clCurrent.equals(String.class)) {
			return FORMULA_STRING;
		}
		if (clCurrent.equals(Double.class) || clCurrent.equals(Double.TYPE)) {
			return FORMULA_DOUBLE;
		}
		if (clCurrent.equals(Date.class)) {
			return FORMULA_DATE;
		}
		return null;
	}

}
