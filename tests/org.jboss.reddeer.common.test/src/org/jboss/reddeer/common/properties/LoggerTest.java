package org.jboss.reddeer.common.properties;

import static org.junit.Assert.fail;

import org.jboss.reddeer.common.logging.Logger;
import org.junit.Test;

/**
 * Simple RedDeer logger test
 * @author Jiri Peterka
 *
 */

public class LoggerTest {

	
	private static final Logger log = Logger.getLogger(LoggerTest.class);
			
	@Test
	public void testLoggerMessageTypes() {
		
		final String message = " testing RedDeer logger message"; 
		
		try {
			log.debug("DEBUG" + message);
			log.dump("DUMP" + message);
			log.error("ERROR" + message);
			log.fatal("FATAL" + message);
			log.info("INFO" + message);
			log.step("STEP" + message);
			log.trace("TRACE" + message);
			
			
		} catch(Exception e) {
			fail("Logger error:" + e.getMessage());
		}
	}
}
