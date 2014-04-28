package org.jboss.reddeer.swt.test.impl.spinner;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Spinner;
import org.jboss.reddeer.swt.impl.spinner.DefaultSpinner;
import org.jboss.reddeer.swt.impl.spinner.LabeledSpinner;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.swt.test.utils.LabelTestUtils;
import org.junit.Test;

/**
 * Test for Spinner implementation
 * 
 * @author Andrej Podhradsky
 * 
 */
public class SpinnerTest extends SWTLayerTestCase {

	@Test
	public void spinnerTest() {
		assertEquals(0, new DefaultSpinner().getValue());
		new DefaultSpinner(0).setValue(1);
		assertEquals(1, new DefaultSpinner(0).getValue());
		new DefaultSpinner(1).setValue(2);
		assertEquals(2, new DefaultSpinner(1).getValue());
		new LabeledSpinner("First spinner:").setValue(11);
		assertEquals(11, new LabeledSpinner("First spinner:").getValue());
		new LabeledSpinner("Second spinner:").setValue(22);
		assertEquals(22, new LabeledSpinner("Second spinner:").getValue());
	}

	@Override
	protected void createControls(org.eclipse.swt.widgets.Shell shell) {
		LabelTestUtils.createLabel(shell, "First spinner:");
		new Spinner(shell, SWT.BORDER);
		
		LabelTestUtils.createLabel(shell, "Second spinner:");
		new Spinner(shell, SWT.BORDER);
	}
}
