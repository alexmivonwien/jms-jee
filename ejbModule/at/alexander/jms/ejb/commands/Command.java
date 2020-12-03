package at.alexander.jms.ejb.commands;

import javax.ejb.Local;

import at.alexander.jms.commons.EventType;

/**
 *  A typical implementation of the command pattern.
 *  The message consumer receives a massage and look-ups a command based
 *  on the massage content.
 *  The execute method contains the EventType as a parameter in order to have a contextual
 *  information about the circumstances, that caused the event (date, user, etc.)
 *  
 * @author Alexander
 *
 */

@Local
public interface Command {
	
	
	/**  The method contains the EventType as a parameter 
	 *   in order to have a contextual information about the circumstances, 
	 *   that caused the event (date, user, etc.)
	 */  
	void execute (EventType trigger);
	

}
