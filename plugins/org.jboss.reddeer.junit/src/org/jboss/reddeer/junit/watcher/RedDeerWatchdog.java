package org.jboss.reddeer.junit.watcher;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jboss.reddeer.junit.ExecutionSetting;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * RedDeer Watchdog hooks executed tests and performs operations when if test
 * failes
 * 
 * @author Jiri Peterka
 * 
 */
public class RedDeerWatchdog extends TestWatcher {
	Logger log = Logger.getLogger(RedDeerWatchdog.class);

	@Override
	protected void failed(Throwable e, Description description) {
		if (ExecutionSetting.getInstance().isPauseFailedTest()) {
			try {
				log.info("Test execution is paused because of failing test, press [Enter] to continue");
				System.in.read();
			} catch (IOException ex) {
				log.error(ex.getMessage());
			}
		}

	}
}