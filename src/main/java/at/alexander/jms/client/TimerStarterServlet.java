package at.alexander.jms.client;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.alexander.jms.ejb.producer.MessageProducerJobRemote;

public class TimerStarterServlet extends HttpServlet {

	@EJB
	MessageProducerJobRemote messageProducerJobRemote;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		messageProducerJobRemote.scheduleMessageProducing();
	}

}
