package at.alexander.jms.ejb.producer.configurator;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import at.alexander.jms.commons.Constants;

import at.alexander.jms.ejb.producer.eventgenerator.AbstractEventFactory;
import at.alexander.jms.ejb.producer.eventgenerator.RandomEventFactory;


/**
 * This class contains some initial settings needed 
 * for the proper functioning of the message (event) producing, as follows:
 * 
 *  - the time interval, in which messages (events) are generated,
 *  - the concrete implementation of the factory class, which determines how messages (events) are generated
 *  - the jmsConnection to the JMS Service
 * 
 * @author Alexander
 *
 */

public class MessageProducerConfigurator {

	
	private static final Logger logger = Logger.getLogger(MessageProducerConfigurator.class);	
	
	// factory determining the generation of events
	private AbstractEventFactory eventFactory;
	
	
	@PersistenceContext
	protected EntityManager em;

	// No injection on a non- ejb class possiible....
	//@Resource (mappedName=Constants.DATA_SOURCE_NAME) 
   // private DataSource dataSource;
    
    // time interval, in which messages are produced
	private int timeIntervalMillis = Constants.TIMER_INTERVAL;
	
	
	
	public MessageProducerConfigurator (){
		this.eventFactory = new RandomEventFactory();
		this.readTimeIntervalFromDB ();
	}

	

	public AbstractEventFactory getEventFactory() {
		return this.eventFactory;
	}
	
	
	public int getTimeIntervalMillis() {
		return timeIntervalMillis;
	}


	private void readTimeIntervalFromDB (){	
        java.sql.Connection con = null;
        PreparedStatement ps = null;   
        String timeInterval = null;
        logger.debug("entering " + this.getClass().getName() + ".readTimeIntervalFromDB()" );
        try {
    		javax.naming.InitialContext ctx = new InitialContext( );
			Object obj = ctx.lookup(Constants.DATA_SOURCE_NAME);
			DataSource dataSource = (DataSource)obj;
			dataSource.setLoginTimeout(10);
	        con = dataSource.getConnection();
	        ps = con.prepareStatement("SELECT * FROM CONFIG WHERE NAME = 'INTERVAL' ");
	        ResultSet res = ps.executeQuery();
	        
	        while (res.next()){
	        	timeInterval = res.getString(2);
	        	break;
	        }
			this.timeIntervalMillis = Integer.parseInt(timeInterval);
		}catch (NumberFormatException nfe){			
			//  @ TODO: 1.) Log the problem
				logger.error(nfe);
						
			//  @ TODO: 2.) Read the default value from JNDI
		}catch(SQLException sql) {
			logger.error(sql);
          //  throw new EJBException(sql);
        } catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		} 
        finally {
            try {
                if (ps != null) ps.close( );
                if (con!= null) con.close( );
            } catch(SQLException se) {
                se.printStackTrace( );
            }
        } // finally 
	}
}
