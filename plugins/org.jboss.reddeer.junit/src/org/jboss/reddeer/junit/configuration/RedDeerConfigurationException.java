package org.jboss.reddeer.junit.configuration;

/**
 * Exception throws during configuration processing. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RedDeerConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 2832066367510275146L;

	public RedDeerConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public RedDeerConfigurationException(String message) {
		super(message);
	}
}
