package at.alexander.jms.commons;

import java.io.Serializable;
import java.util.Date;

public enum EventType implements Serializable{
	
	INFO, WARNING, EXCEPTION;

	private Date creationDate;
	private String user;
	private String system;
		
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	private String description;
	
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

							
	

}
