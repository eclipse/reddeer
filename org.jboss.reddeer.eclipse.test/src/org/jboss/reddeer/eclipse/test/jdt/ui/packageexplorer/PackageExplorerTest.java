package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.junit.Test;

public class PackageExplorerTest {

	private PackageExplorer packageExplorer;
	
	@Test
	public void open() {
		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		
	}
}
