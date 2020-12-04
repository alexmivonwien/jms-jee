package at.alexander.jms.ejb.producer;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.jboss.logging.Logger;

import at.alexander.jms.commons.EventType;
import at.alexander.jms.ejb.producer.configurator.JMSConfigurator;
import at.alexander.jms.ejb.producer.configurator.MessageProducerConfigurator;

/**
 * Session bean used to produce messages (events) on a particular time basis. It uses the JEE TimerService, so that messages are produced in repeating series Producing of messages is done via JMS, and
 * the types of events produced is determined by an AbstarctEventFactory
 * 
 * @author Alexander
 *
 */

@Stateless
public class MessageProducerJob implements MessageProducerJobRemote {

	private static final Logger logger = Logger.getLogger(MessageProducerJob.class);

	@Resource
	TimerService timerService;

	// initial settings for message producing
	private MessageProducerConfigurator configurator;

	// contains JMS configuration settings
	private JMSConfigurator jmsConfigurator;

	@PostConstruct
	private void init() {
		this.configurator = new MessageProducerConfigurator();
		this.jmsConfigurator = new JMSConfigurator();

	}

	@PreDestroy
	private void destroy() {
		jmsConfigurator.invalidateJmsConnection();

	}

	public void scheduleMessageProducing() {
		Calendar time = Calendar.getInstance(); // the current time.
		time.add(Calendar.MILLISECOND, configurator.getTimeIntervalMillis()); // add timeIntervalMillis to the current time.
		Date date = time.getTime();
		// Create a timer that will go off timeIntervalMillis milliseconds from now.
		timerService.createTimer(date, null);
		// logger.debug("A timer was created to start at " + date);
	};

	@Timeout
	public void generateEvent(javax.ejb.Timer timer) {
		logger.debug("Aboout to generate an event for timer " + timer);
		try {
			Session session = jmsConfigurator.getJmsConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(jmsConfigurator.getTopic());
			ObjectMessage objMsg = session.createObjectMessage();
			EventType eventType = configurator.getEventFactory().generateEvent();
			eventType.setCreationDate(new Date());
//			eventType.setSystem(sessionContext.toString());
//			if (sessionContext.getCallerPrincipal()!=null){	
//				eventType.setUser(sessionContext.getCallerPrincipal().getName());
//			}
			objMsg.setObject(eventType);
			producer.send(objMsg);
			producer.close();
			session.close();
			logger.debug("Successfully  generated an event for timer " + timer);
		} catch (JMSException je) {
			// @ TODO: 1.) Log the problem
			logger.error("An exception occured while generating event and trying to send a message for it", je);

			throw new EJBException(je);
		} catch (Exception e) {
			logger.error("An exception occured while generating event and trying to send a message for it", e);
			throw new EJBException(e);
		}

	}

}
