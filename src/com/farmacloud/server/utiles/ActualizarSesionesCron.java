package com.farmacloud.server.utiles;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ActualizarSesionesCron extends HttpServlet
{
	private static final Logger log = Logger.getLogger(ActualizarSesionesCron.class.getName());
	public static final int MAX_RETRIES = 5;
	public static final int SLEEP_TIME_MILLIS = 100;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		try 
		{
			log.info("Cron Job has been executed");
			
			
		
			
		}
		catch (Exception e) 
		{
			
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
