/*
 * © Copyright WebGate Consulting AG, 2013
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
package biz.webgate.dominoext.nagios.threshold;

import java.io.Serializable;

public class Value implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_Key;
	private String m_Alias;
	private double m_Warning;
	private double m_Critical;
	private boolean m_isBigger;
	public String getKey() {
		return m_Key;
	}
	public void setKey(String key) {
		m_Key = key;
	}
	public String getAlias() {
		return m_Alias;
	}
	public void setAlias(String alias) {
		m_Alias = alias;
	}
	public double getWarning() {
		return m_Warning;
	}
	public void setWarning(double warning) {
		m_Warning = warning;
	}
	public double getCritical() {
		return m_Critical;
	}
	public void setCritical(double critical) {
		m_Critical = critical;
	}
	public boolean isBigger() {
		return m_isBigger;
	}
	public void setBigger(boolean isBigger) {
		m_isBigger = isBigger;
	}
}
