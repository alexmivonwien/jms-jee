package at.alexander.jms.model.persistent;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "config")
public class Config implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5552019107216524891L;
	
	public enum PropertyName {INTEVRAL};

	
	@Id
	private PropertyName name;
	private String value;

	@Enumerated (value= EnumType.STRING)
	public PropertyName getName() {
		return name;
	}
	public void setName(PropertyName name) {
		this.name = name;
	}
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
