package org.jboss.reddeer.swt.test.impl.button;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Test;

public class LinkTest extends RedDeerTest{

	
	@Override
	public void setUp() {
		super.setUp();
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell(Display.getDisplay());
				shell.setText("Shell with link");
				Link link = new Link(shell, SWT.NONE);
				String text = "<a>shell</a>";
				link.setText(text);
				Rectangle clientArea = shell.getClientArea();
				link.setBounds(clientArea.x, clientArea.y, 400, 400);
				link.addListener (SWT.Selection, new Listener () {
					public void handleEvent(Event event) {
						Shell shell1 = new Shell(Display.getDisplay());
						shell1.setText("Shell opened by link");
						shell1.open();
					}
				});
				link.setVisible(true);
				shell.pack ();
				shell.open();
			}
		});
	}
	
	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.swt.
						util.Display.getDisplay().getShells()) {
					if (shell.getText().equals("Shell with link") || shell.getText().equals("Shell opened by link")) {
						shell.dispose();
					}
				}
			}
		});
	}
	
	@Test
	public void clickOnLink(){
		new DefaultShell("Shell with link");
		org.jboss.reddeer.swt.api.Link l = new org.jboss.reddeer.swt.impl.link.DefaultLink("shell");
		assertEquals("shell",l.getText());
		l.click();
		assertEquals("Shell opened by link",new DefaultShell().getText());
	}

}
