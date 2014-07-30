package org.jboss.reddeer.swt.test.impl.menu;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.handler.ShellHandler;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ShellMenuTest {
	
	@After
	public void cleanup(){
		ShellHandler.getInstance().closeAllNonWorbenchShells();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void openPreferences(){
		new ShellMenu(new RegexMatcher("Window"), new RegexMatcher("Preferences")).select();
		
		DefaultShell activeShell = new DefaultShell();
		assertThat(activeShell.getText(), is("Preferences"));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void openAbout(){
		new ShellMenu(new RegexMatcher("Help"), new RegexMatcher("About.*")).select();
		
		DefaultShell activeShell = new DefaultShell();
		assertThat(activeShell.getText(), is("About Eclipse Platform"));
	}

	@Test
	public void getText(){
		WithTextMatchers m = new WithTextMatchers(new RegexMatcher[] {
				new RegexMatcher("Window.*"),
				new RegexMatcher("Show View.*"),
				new RegexMatcher("Other...*") });
		
		ShellMenu menu = new ShellMenu(m.getMatchers());
		String menuText1 = menu.getText();
		menu.select();
		cleanup();
		String menuText2 = menu.getText();
		
		assertEquals("Menu text has to be equal before and after select is called", menuText1, menuText2);
	}
}
