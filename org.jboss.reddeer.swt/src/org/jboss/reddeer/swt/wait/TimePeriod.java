package org.jboss.reddeer.swt.wait;

/**
 * Represents the time scale of how long the user operation might last.
 * CUSTOM TimePeriod has to be created calling static method TimePeriod.getCustom(long seconds)
 * with positive input parameter
 * @author Lucia Jelinkova
 *
 */
public enum TimePeriod {

	NONE(0),SHORT(1),NORMAL(10), LONG(60), VERY_LONG(300), CUSTOM(-1);
	
	private long seconds;
	
	private TimePeriod(long seconds) {
		this.seconds = seconds;
	}
	
	public long getSeconds(){
	  if (this.equals(TimePeriod.CUSTOM) && seconds == -1){
	    throw new UnsupportedOperationException("CUSTOM TimePeriod has to be created calling static method "
	        + "TimePeriod.getCustom(long seconds) with positive input parameter");
	  }
		return seconds;
	}
	
	public static TimePeriod getCustom(long seconds){
	  TimePeriod custom = TimePeriod.CUSTOM;
	  custom.seconds = seconds;
	  return custom;
	}
}
