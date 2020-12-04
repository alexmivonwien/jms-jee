package at.alexander.jms.ejb.commands;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import at.alexander.jms.commons.EventType;

@Stateless (name = "LogFile")
public class LogFile implements Command{

	private static final Logger log = Logger.getLogger(LogFile.class);
	
	@Override
	public void execute(EventType trigger) {
		log.info("An event type " + trigger + " has been successfully consumed by " + this.getClass().getName() );	
	}

}
