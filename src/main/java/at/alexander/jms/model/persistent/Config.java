package at.alexander.jms.model.persistent;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIG")
public class Config implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5552019107216524891L;

	@Id
	private String name;
	private String val;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
