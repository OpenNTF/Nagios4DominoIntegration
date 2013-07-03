package biz.webgate.example.ninja;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

import biz.webgate.xpages.dss.annotations.DominoEntity;
import biz.webgate.xpages.dss.annotations.DominoStore;

@DominoStore(Form="frmNinja", PrimaryKeyField="Id", PrimaryFieldClass=String.class, View="lupNinjasById")
public class Ninja implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@DominoEntity(FieldName="IdT")
	private String m_Id;
	@DominoEntity(FieldName="NameT")
	private String m_Name;
	@DominoEntity(FieldName="ColorT")
	private String m_Color;
	@DominoEntity(FieldName="WeaponsT")
	private List<String> m_Weapons;
	@DominoEntity(FieldName="BirthdayD", dateOnly=true)
	private Date m_Birthday;
	@DominoEntity(FieldName="ReaderR",isReader=true)
	private String m_Reader = "[Admin]";
	
	public String getId() {
		return m_Id;
	}
	public void setId(String id) {
		m_Id = id;
	}
	public String getName() {
		return m_Name;
	}
	public void setName(String name) {
		m_Name = name;
	}
	public String getColor() {
		return m_Color;
	}
	public void setColor(String color) {
		m_Color = color;
	}
	public List<String> getWeapons() {
		return m_Weapons;
	}
	public void setWeapons(List<String> weapons) {
		m_Weapons = weapons;
	}
	public Date getBirthday() {
		return m_Birthday;
	}
	public void setBirthday(Date birthday) {
		m_Birthday = birthday;
	}
	public void setReader(String reader) {
		m_Reader = reader;
	}
	public String getReader() {
		return m_Reader;
	}
}
