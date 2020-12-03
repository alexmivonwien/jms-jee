package at.alexander.jms.ejb.consumer;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.EJBs;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.alexander.jms.commons.Constants;
import at.alexander.jms.commons.EventType;
import at.alexander.jms.ejb.commands.Command;
import at.alexander.jms.model.persistent.Action;

@Stateless

@EJBs({
	
	@EJB(name="ejb/LogDB",
			beanInterface=Command.class,
			beanName="LogDB"),
			
	@EJB(name="ejb/LogFile",
		 beanInterface=Command.class,
		 beanName="LogFile"),
		
	@EJB(name="ejb/SendMail",
	     beanInterface=Command.class,
	     beanName="SendMail")

})


/**
 * The EventProcessorBean is used to process the events that have been 
 * consumed by the MessageConsumerMDB. It does so by querying the DB about what actions
 * correspond to the particular event recieved,
 * looking up the corresponding action implementations from JNDI and calling the actions' execute
 * method, which actually does the work.
 * 
 */

public class EventProcessorBean implements EventProcessorLocal{
	
	
	private static final Logger logger = Logger.getLogger(EventProcessorBean.class);
	
	@PersistenceContext (name = Constants.PERSITENCE_CONTEXT_NAME)
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public void processEvent(EventType eventType){
		logger.debug("Entering the processEvent() of  " + this.getClass().getName());
		// read from database	
		Query query = em.createQuery("select a FROM Event e, IN (e.eventActions) a WHERE e.type =:type");
		query.setParameter("type", eventType.toString());
		List<Action> actions = query.getResultList();				
		//Iterate through list, obtain the command Implementation and execute it
		String actionName = null;
		for (Action action: actions){
			actionName = action.getType();			
			try{
				// Take it from JNDI:
				javax.naming.InitialContext ctx = new InitialContext( );
				Object obj = ctx.lookup("java:comp/env/ejb/" +actionName);
				Command command = (Command)obj;
				command.execute(eventType);				
			} 
			catch (javax.naming.NamingException ne) {
				// @ TODO: Log the exception
				logger.error(ne);
				// re-throw it to rollback the transaction:
				throw new EJBException(ne);
			}
			catch (ClassCastException cce){
				// @ TODO: Log the exception
				logger.error(cce);
				// re-throw it to rollback the transaction:
				throw new EJBException(cce);
			}
		}// for
		
		logger.debug("Exiting the processEvent() of  " + this.getClass().getName());
		
	};
}
