package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.swt.util.Bot;
import org.junit.Test;

public class PackageExplorerTest {

	private PackageExplorer packageExplorer;
	
	@Test
	public void open() {
		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		assertTrue("Active View has to be Package Explorer but is " + Bot.get().activeView().getTitle(),
		  Bot.get().activeView().getTitle().equals("Package Explorer"));
		
	}
}
