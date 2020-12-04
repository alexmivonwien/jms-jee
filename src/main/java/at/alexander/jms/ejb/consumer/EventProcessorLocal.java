package at.alexander.jms.ejb.consumer;

import javax.ejb.Local;


import at.alexander.jms.commons.EventType;

/**
 * The Local interface for the EventProcessorBean
 * @author Alexander
 *
 */
@Local
public interface EventProcessorLocal {

	public void processEvent(EventType eventType);
	
}
