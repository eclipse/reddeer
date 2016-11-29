/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.test.impl.menu;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.matcher.WithTextMatchers;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ShellMenuTest {
	protected final Logger log = Logger.getLogger(this.getClass());
	private ProjectExplorer explorer = new ProjectExplorer();

	@Before
	public void setUp() {
		explorer.open();
	}

	@Test
	public void logTest() {
		log.debug("debug");
		log.error("error");
		log.fatal("fatal");
		log.warn("info");
		log.info("info");
	}

	@Test
	public void regexMenuTest() {

		log.info("regex menu test");
		try {
			RegexMatcher[] regexMatchers = { new RegexMatcher("Fi.*"), new RegexMatcher("Ne.*") };
			WithTextMatchers m = new WithTextMatchers(regexMatchers);
			new ShellMenu(m.getMatchers());
		} catch (SWTLayerException e) {
			fail("there should be no exception");
		}

	}

	@Test
	public void unavailableMenuTest() {
		log.info("unavailable regex menu test");
		try {
			RegexMatcher[] regexMatchers = { new RegexMatcher("Fi.*"), new RegexMatcher("Nez.*") };
			WithTextMatchers m = new WithTextMatchers(regexMatchers);
			new ShellMenu(m.getMatchers());
			fail("exception should be thrown");
		} catch (CoreLayerException e) { // do nothing

		}
	}

	@Test
	public void shellMenuItemTextTest() {
		new DefaultShell();
		Menu menu = new ShellMenu("File", "New");
		assertTrue("Menuitem text not expected to be empty", !menu.getText().equals(""));
	}

	@Test
	public void menuWithMnemonicTest() {
		log.info("menu with mnemonic test");
		new DefaultShell();
		Menu m = new ShellMenu("File", "New", "Other...");
		m.select();
		Shell s = new DefaultShell("New");
		s.close();
	}

	private class ProjectExplorer extends WorkbenchView {
		public ProjectExplorer() {
			super("General", "Project Explorer");
		}
	}
}
