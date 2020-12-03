package at.alexander.jms.ejb.producer.eventgenerator;

import at.alexander.jms.commons.EventType;

/**
 * An implementation of AbstractEventFactory
 * it generates events based on the current date
 * (i.e. even date - only INFO events; odd date- all other dates, etc) 
 *   
 * @author Alexander
 */
public class DateEventFactory implements AbstractEventFactory{

	@Override
	public EventType generateEvent() {
		// TODO Auto-generated method stub
		return null;
	}

}
