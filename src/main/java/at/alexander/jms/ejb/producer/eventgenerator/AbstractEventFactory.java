package at.alexander.jms.ejb.producer.eventgenerator;

import at.alexander.jms.commons.EventType;

/**
 * An typical example of the Abstract Factory Pattern
 * Implementations generate events based on their concrete type 
 * @author Alexander
 *
 */
public interface AbstractEventFactory {
	
	
	/**
	 * This method generates events,
	 * Implementations generate events based on their concrete type 
	 * @return
	 */
		EventType generateEvent();

}
