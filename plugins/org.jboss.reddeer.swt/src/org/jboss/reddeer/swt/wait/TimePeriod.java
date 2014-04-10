package org.jboss.reddeer.swt.wait;

/**
 * Represents the time period of how long the user operation might last. Usually, it is not important if the task takes 5 or 10 seconds, that's 
 * why the pre-defined values should be sufficient for most of the use cases. 
 * <br/>
 * However, if for some reason you need to define your own time period, you can use {@link #getCustom(long)} method. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TimePeriod {

	public static final TimePeriod NONE = new TimePeriod(0);

	public static final TimePeriod SHORT = new TimePeriod(1);

	public static final TimePeriod NORMAL = new TimePeriod(10);

	public static final TimePeriod LONG = new TimePeriod(60);

	public static final TimePeriod VERY_LONG = new TimePeriod(300);

	private long seconds;

	private TimePeriod(long seconds) {
		this.seconds = seconds;
	}

	public long getSeconds(){
		return seconds;
	}

	public static TimePeriod getCustom(long seconds){
		if (seconds < 0){
			throw new IllegalArgumentException("Time in seconds has to be positive number");
		}
		return new TimePeriod(seconds);
	}
}
