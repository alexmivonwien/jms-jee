package at.alexander.jms.ejb.producer.configurator;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import at.alexander.jms.commons.Constants;

/**
 * This class contains JMS initial configuration settings, as well as a JMS connection. The JMS connection shall be closed, when it is not needed anymore, that is what the invalidate() method is used
 * for
 * 
 * @author Alexander
 *
 */
public class JMSConfigurator {

	private static final Logger logger = Logger.getLogger(JMSConfigurator.class);

	private Topic topic;

	private Connection jmsConnection;

	public JMSConfigurator() {
		try {

			Properties props = new Properties();
			props.put(Context.SECURITY_PRINCIPAL, "jmsuser");
			props.put(Context.SECURITY_CREDENTIALS, "Password1!");
			javax.naming.InitialContext ctx = new InitialContext(props);

			Object obj = ctx.lookup(Constants.JMS_CONNECTION_FACTORY);
			ConnectionFactory factory = (ConnectionFactory) obj;
			this.jmsConnection = factory.createConnection();
			obj = ctx.lookup(Constants.JMS_TOPIC_NAME);
			this.topic = (Topic) obj;

		} catch (JMSException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

	}

	public Connection getJmsConnection() {
		return this.jmsConnection;
	}

	public Topic getTopic() {
		return topic;
	}

	public void invalidateJmsConnection() {
		try {
			if (this.jmsConnection != null) {
				this.jmsConnection.close();
			}
		} catch (JMSException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

}
