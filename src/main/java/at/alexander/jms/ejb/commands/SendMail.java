package at.alexander.jms.ejb.commands;

import java.util.Properties;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import at.alexander.jms.commons.EventType;

@Stateless (name = "SendMail")
public class SendMail implements Command {
	
	private static final Logger log = Logger.getLogger(SendMail.class);

	@Resource(name="mailFrom") 
	private String mailFrom;
	
	@Resource(name="mailTo") 
	private String mailTo;
	
	@Resource(name="smtpHost") 
	private String smtpHost;

	@Resource(name="smtpPort") 
	private int smtpPort;
	
	@Override
	public void execute(EventType trigger) {
		
	          Properties prop = new Properties();
	          Session session = Session.getDefaultInstance(prop, null);
	          Message message = new MimeMessage(session);
	          log.info("About to exectue a SendMailAction");

	          try {
	               prop.put("mail.smtp.host", smtpHost);
	               prop.put("mail.smtp.port", smtpPort);	 
	               //Define message	
	               message.setFrom(new InternetAddress(mailFrom));
	               message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo, false));
	               message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailTo));
	               message.setSubject("E-mail alert because of event");
	               message.setHeader("X-Mailer", "iQuest-Mailer");
	               String content = trigger + trigger.getDescription() + trigger.getSystem() + trigger.getCreationDate();
	               message.setContent(content, "text/plain");
	               Transport.send(message);

	          } catch (Exception e){
	        	  // @ TODO: Log the exception:
	        	  log.error(e.getMessage(), e);
	        	  // Re-throw the exception to roll-back any transactions:	        	  
	        	  throw new EJBException(e);
	          }

		
	}

}
