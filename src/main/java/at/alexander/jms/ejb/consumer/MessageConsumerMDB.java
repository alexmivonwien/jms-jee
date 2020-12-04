package at.alexander.jms.ejb.consumer;

import javax.ejb.EJB;


import javax.ejb.EJBException;
import javax.ejb.MessageDriven;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.ejb.ActivationConfigProperty;

import org.jboss.logging.Logger;

import at.alexander.jms.commons.Constants;
import at.alexander.jms.commons.EventType;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty (
				propertyName="destinationType",
				propertyValue="javax.jms.Topic"),		
				
		@ActivationConfigProperty(
				propertyName="destination", 
				propertyValue=Constants.JMS_TOPIC_NAME),
	 
		@ActivationConfigProperty(
				propertyName="acknowledgeMode",
				propertyValue="Auto-acknowledge")
				
})	

public class MessageConsumerMDB implements javax.jms.MessageListener {
	@EJB
	private EventProcessorLocal eventProcessor;
	
	private static final Logger logger = Logger.getLogger(MessageConsumerMDB.class);
	
	
	public void onMessage(Message message){
		logger.debug("A message " + message+ " has been succesfully received");
		try{
			ObjectMessage objMessage = (ObjectMessage)message;
			EventType eventType = (EventType)objMessage.getObject();		
			eventProcessor.processEvent(eventType);
		}catch (JMSException jmsex){
			// @ TODO : Log the exception:
			// Re-throw the exception as an EJB exception to rollback any transactions:			
			throw new EJBException(jmsex);			
		}catch (ClassCastException  cse){
			// @ TODO : Log the exception:
			
//			// Re-throw the exception as an EJB exception to rollback any transactions:			
			throw new EJBException(cse);			
		}catch (Exception  ex){
//			// @ TODO : Log the exception:
			
//			// Re-throw the exception as an EJB exception to rollback any transactions:			
			throw new EJBException(ex);	
		}
	}
}
