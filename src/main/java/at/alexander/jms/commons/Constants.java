package at.alexander.jms.commons;

public interface Constants {

	// this topic name shall be placed as an entry in the standalone-full.xml
	// at line ca. 482 like this:
	// <jms-topic name="topic/testTopic" entries="java:/jms/topic/testTopic" />
	// thus being the last line before the standard available entry
	// <connection-factory name="InVmConnectionFactory" entries="java:/ConnectionFactory" connectors="in-vm"/>
	public static final String JMS_TOPIC_NAME = "java:/jms/topic/testTopic";

	public static final String DATA_SOURCE_NAME = "java:/MySqlDS_JmsJee";

	public static String JMS_CONNECTION_FACTORY = "ConnectionFactory";

	public static int TIMER_INTERVAL = 3000;

}
