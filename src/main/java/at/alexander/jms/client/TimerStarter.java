package at.alexander.jms.client;

import java.util.Properties;

import javax.naming.Context;

import at.alexander.jms.ejb.producer.MessageProducerJobRemoteXYZ;

public class TimerStarter {

	public static void main(String[] args) {

		try {
			Context jndiContext = getInitialContext();
			Object ref = jndiContext.lookup("MessageProducerJob/remote");
			MessageProducerJobRemoteXYZ timerRemote = (MessageProducerJobRemoteXYZ) ref;
			timerRemote.scheduleMessageProducing();
		}

		catch (javax.naming.NamingException ne) {
			ne.printStackTrace();
		} catch (ClassCastException cse) {
			cse.printStackTrace();
		}
	}// main

	public static Context getInitialContext() throws javax.naming.NamingException {
		Properties p = new Properties();
//		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
//		p.put(Context.URL_PKG_PREFIXES, " org.jboss.naming:org.jnp.interfaces");
//		p.put(Context.PROVIDER_URL, "jnp://localhost:1099");
//		// ... Specify the JNDI properties specific to the vendor.
//		return new javax.naming.InitialContext(p);

//		Properties properties = new Properties();
//		properties.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
//		properties.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
//		properties.setProperty("java.naming.provider.url", "jnp://localhost:1099");

		// Set up the namingContext for the JNDI lookup
		final Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		properties.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		properties.put(Context.SECURITY_PRINCIPAL, "jmsuser");
		properties.put(Context.SECURITY_CREDENTIALS, "Password1!");

		return new javax.naming.InitialContext(properties);
	}
}
