package com.farmacloud.server.utiles;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

public final class Utiles 

{	public static final Logger log = Logger.getLogger(Utiles.class.getName());

	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiffMillis(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies, timeUnit);
	}
	
	public static void duerme(String service, int SLEEP_TIME_MILLIS)
	{
		try
		{
			Thread.sleep(SLEEP_TIME_MILLIS);
		} 
		catch (InterruptedException e) 
		{
			log.log(Level.SEVERE, ""+service+"\n"+e);
		}
	}
	
	public static <T> boolean validate(Validator validator, T obj)
	{
		Set<ConstraintViolation<T>> violations = validator.validate(obj, Default.class);
		if (violations.size() > 0)
		{
			log.info("se han encontrado violaciones al validar");
			for(ConstraintViolation<T> c : violations)
			{
				log.info("   "+c.getRootBeanClass().toString()+"\n\t\t"+
						 "message: "+c.getMessage()+"\n\t\t"+
						 "invalid value: "+c.getInvalidValue().toString());
			}
			return false;
		}
		else{
			log.info("sin errores validando "+obj.getClass().getSimpleName());
			return true;
		}	
	}
}
