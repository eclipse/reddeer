package org.jboss.reddeer.common.logging;

/**
 * Provides some utility methods useful when creating log messages. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class LoggingUtils {

	public static String format(int[] items){
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < items.length; i++){
			sb.append(items[i]);
			if (i != items.length - 1){
				sb.append(", ");
			}
		}
		
		return sb.toString();
	}
	
	public static String format(Object[] items){
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < items.length; i++){
			sb.append(items[i]);
			if (i != items.length - 1){
				sb.append(", ");
			}
		}
		
		return sb.toString();
	}
}
