Project Description:
===================

The component 'Alert' subscribes via JMS for the events:
--> "info",
--> "warning", and
--> "exception".

These events are read from a database and are made ready for "consumption" from the Topic-component Messanger, which is triggered by the Timer component. It is the Timer component, which generates the events above on a regular basis (interval), whose value is also read from the database.

Upon occuring of one of the events, the 'Alert' component reads from the database the (types of) actions, which are associated with that event,
and which are to be performed by dynamically calling the corresponding implementation.

Depending on the configuration, the types of actions (one ore more) could be one of the:
	--> Sending an E-Mail
--> Writting a log entry in the database
--> Writting a log entry into a file.


Te project described  here implements the above requirements

 ____________
|            |   publish event	   |-------------|  subscribe for events    |-------------|
|  Timer     | ------------------> | Messenger   |<-------------------------| Alert       |
|____________|					   |_____________|                          |_____________|
    -.																			   -.
	  -.																	     -.
		---------------------------> ( Database ) <-------------------------------


Before implementing the SOLUTION:
1.) Through the script add-user.sh an application user belonging to the group "guest" shall be created (for example, user = "jmsuser", password = "Password1", as explained here:

http://www.mastertheboss.com/jboss-server/jboss-jms/how-to-code-a-remote-jms-client-for-wildfly-8

This user and its credentials are needed upon creating the InitialContext in the JMSConfigurator like this:

			Properties props = new Properties();
			props.put(Context.SECURITY_PRINCIPAL, "jmsuser");
			props.put(Context.SECURITY_CREDENTIALS, "Password1!");
			javax.naming.InitialContext ctx = new InitialContext(props);


2.) The following entry shall be added at line 482 of standalone-full.xml:

&lt;jms-topic name="topic/testTopic" entries="java:/jms/topic/testTopic" /&gt;


3.) We created a test servlet that invokes the MessageProducerJobRemote (actually MessageProducerJob.scheduleMessageProducing()) to avoid
creating a remote InitialContext (because it gave errors!) 
