package org.jboss.reddeer.swt.test.impl.tree;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.junit.After;

public class ShellTreeTest extends AbstractTreeTest {

	private Tree swtTree;

	@Override
	public void setUp() {
		super.setUp();
		org.jboss.reddeer.swt.util.Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				shell.setText("Testing shell");
				shell.setLayout(new FillLayout());
				swtTree = new Tree(shell, SWT.BORDER|SWT.CHECK|SWT.MULTI);
				TreeColumn column = new TreeColumn(swtTree, SWT.LEFT);
		    column.setWidth(200);
				shell.open();
				shell.setFocus();
			}
		});
		tree = new DefaultTree();
	}
	
	@After
	public void cleanup() {
	  org.jboss.reddeer.swt.util.Display.syncExec(new Runnable() {
			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.swt.
						util.Display.getDisplay().getShells()) {
					if (shell.getText().equals("Testing shell")) {
						shell.dispose();
						break;
					}
				}
			}
		});
	}
}
