package at.alexander.jms.ejb.producer;

import javax.ejb.Remote;

/**
 * The remote interface for the MessageProducerJob session bean.
 * Used by remote stand-alone clients to schedule the message production
 * All settings related to the schedule are stored within the current application
 * @author Alexander
 *
 */
@Remote
public interface MessageProducerJobRemote {
	
	/**
	 * used by remote stand-alone clients to schedule the message production
	 * all settings related to the schedule (i.e. time interval for message producing)
	 * are stored within the current application
	 */
	public void scheduleMessageProducing();
}
