package at.alexander.jms.model.persistent;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT")

/**
 * entity representing the Event. Hibernate version used in Jbosss 4.2.3 has problem,s with mapping enumerated values That is why the type of the entity is string, not enumeration
 */
public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1055504405733562785L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "typ")
	private String type;

//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "EVENT_ID")
//	
	@OneToMany
	@JoinTable(name = "EVENT_ACT", joinColumns = { @JoinColumn(name = "EVENT_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ACT_ID") })
	private List<Action> eventActions;

	public List<Action> getEventActions() {
		return eventActions;
	}

	public void setEventActions(List<Action> eventActions) {
		this.eventActions = eventActions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
