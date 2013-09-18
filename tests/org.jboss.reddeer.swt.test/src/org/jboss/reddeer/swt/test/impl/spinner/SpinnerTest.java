package org.jboss.reddeer.swt.test.impl.spinner;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.spinner.DefaultSpinner;
import org.jboss.reddeer.swt.impl.spinner.LabeledSpinner;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.junit.Test;

/**
 * Test for Spinner implementation
 * 
 * @author Andrej Podhradsky
 * 
 */
public class SpinnerTest extends RedDeerTest {

	@Test
	public void spinnerTest() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				org.eclipse.swt.widgets.Display display = Display.getDisplay();
				org.eclipse.swt.widgets.Shell shell = new org.eclipse.swt.widgets.Shell(display);
				shell.setText("Spinner Test");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});

		Shell spinnerShell = new DefaultShell("Spinner Test");

		assertEquals(0, new DefaultSpinner().getValue());
		new DefaultSpinner(0).setValue(1);
		assertEquals(1, new DefaultSpinner(0).getValue());
		new DefaultSpinner(1).setValue(2);
		assertEquals(2, new DefaultSpinner(1).getValue());
		new LabeledSpinner("First spinner:").setValue(11);
		assertEquals(11, new LabeledSpinner("First spinner:").getValue());
		new LabeledSpinner("Second spinner:").setValue(22);
		assertEquals(22, new LabeledSpinner("Second spinner:").getValue());

		spinnerShell.close();
	}

	private void createControls(org.eclipse.swt.widgets.Shell shell) {
		Label swtLabel1 = new Label(shell, SWT.NONE);
		swtLabel1.setText("First spinner:");
		swtLabel1.setSize(120, 30);
		swtLabel1.setLocation(100, 50);
		Spinner swtSpinner1 = new Spinner(shell, SWT.BORDER);
		swtSpinner1.setSize(100, 30);
		swtSpinner1.setLocation(225, 50);
		Label swtLabel2 = new Label(shell, SWT.NONE);
		swtLabel2.setText("Second spinner:");
		swtLabel2.setSize(120, 30);
		swtLabel2.setLocation(100, 100);
		Spinner swtSpinner2 = new Spinner(shell, SWT.BORDER);
		swtSpinner2.setSize(100, 30);
		swtSpinner2.setLocation(225, 100);
	}

}
