package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

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
