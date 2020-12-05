package at.alexander.jms.ejb.producer.configurator;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import at.alexander.jms.commons.Constants;
import at.alexander.jms.ejb.producer.eventgenerator.AbstractEventFactory;
import at.alexander.jms.ejb.producer.eventgenerator.RandomEventFactory;
import at.alexander.jms.model.persistent.Config;

/**
 * This class contains some initial settings needed for the proper functioning of the message (event) producing, as follows:
 * 
 * - the time interval, in which messages (events) are generated, - the concrete implementation of the factory class, which determines how messages (events) are generated - the jmsConnection to the
 * JMS Service
 * 
 * @author Alexander
 *
 */
public class MessageProducerConfigurator {

	private static final Logger logger = Logger.getLogger(MessageProducerConfigurator.class);

	// factory determining the generation of events
	private AbstractEventFactory eventFactory;

	private EntityManager em;

	// No injection on a non- ejb class possiible....
	// @Resource (mappedName=Constants.DATA_SOURCE_NAME)
	// private DataSource dataSource;

	// time interval, in which messages are produced
	private int timeIntervalMillis = Constants.TIMER_INTERVAL;

	public MessageProducerConfigurator(EntityManager em) {
		this.em = em;
		this.eventFactory = new RandomEventFactory();
		this.readTimeIntervalFromDB();
	}

	public AbstractEventFactory getEventFactory() {
		return this.eventFactory;
	}

	public int getTimeIntervalMillis() {
		return timeIntervalMillis;
	}

	private void readTimeIntervalFromDB() {
		logger.debug("entering " + this.getClass().getName() + ".readTimeIntervalFromDB()");
		try {
			Query query = this.em.createQuery(" from Config c where c.name = 'INTERVAL'");
			List<Config> configIntervals = query.getResultList();

			if (configIntervals.size() > 0) {
				this.timeIntervalMillis = Integer.valueOf(configIntervals.get(0).getVal());
			}
		} catch (NumberFormatException nfe) {
			// @ TODO: 1.) Log the problem
			logger.error(nfe);
			// @ TODO: 2.) Read the default value from JNDI
		}
	}
}
