package at.alexander.jms.model.persistent;
import java.io.Serializable;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table (name = "action")
public class Action implements Serializable{

	private static final long serialVersionUID = -7780300237507883757L;
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="TYPE")
	private String type;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public void setAction(String type) {
		try{
			//Class<Command> c = (Class<Command>) Class.forName(type);
			this.type = type;
		} 
//		catch (ClassNotFoundException nfe){
//			
//		} 
		catch (ClassCastException cce){
			
		}

	}
	
	

}
