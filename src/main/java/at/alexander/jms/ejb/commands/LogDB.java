package at.alexander.jms.ejb.commands;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import at.alexander.jms.commons.EventType;
import at.alexander.jms.model.persistent.Log;

@Stateless(name = "LogDB")
public class LogDB implements Command {

	private static final Logger logger = Logger.getLogger(LogDB.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public void execute(EventType trigger) {
		logger.debug("About to execute logDB event");
		Log log = new Log();
		log.setDate(trigger.getCreationDate());
		log.setUser(trigger.getUser());
		switch (trigger) {
		case INFO: {
			log.setSeverity(Log.Severity.INFO);
			break;
		}
		case WARNING: {
			log.setSeverity(Log.Severity.WARNING);
			break;
		}
		case EXCEPTION: {
			log.setSeverity(Log.Severity.ERROR);
			break;
		}
		default: {
			log.setSeverity(Log.Severity.DEBUG);
			break;
		}
		}
		log.setMessage("A triggering event from system " + trigger.getSystem() + " caused a DB log ");
		em.persist(log);
	}

}
