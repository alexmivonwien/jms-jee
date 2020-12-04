package at.alexander.jms.ejb.producer.eventgenerator;

import at.alexander.jms.commons.EventType;

/**
 * An implementation of AbstractEventFactory
 * it generates events on a random basis
 * 
 * @author Alexander
 *
 */
public class RandomEventFactory implements AbstractEventFactory{

	@Override
	public EventType generateEvent(){
		 EventType [] eventTypes = EventType.values(); 
		 double casualNumber = Math.random();
		 int casualElement = (int)((eventTypes.length - 1)*casualNumber);		
		 return eventTypes[casualElement];
	}
}
