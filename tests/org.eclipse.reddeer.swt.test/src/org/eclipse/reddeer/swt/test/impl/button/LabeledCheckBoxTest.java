/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.test.impl.button;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.reddeer.swt.impl.button.LabeledCheckBox;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.eclipse.reddeer.swt.test.utils.LabelTestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author apodhrad
 *
 */
public class LabeledCheckBoxTest extends SWTLayerTestCase {
	
	@Override
	protected void createControls(Shell shell) {
		LabelTestUtils.createLabel(shell, "Label 1");
		new Button(shell, SWT.CHECK);
		
		LabelTestUtils.createLabel(shell, "Label 2");
		Button checkBox = new Button(shell, SWT.CHECK);
		checkBox.setSelection(true);
		
	}
	
	@Test
	public void labeledCheckBoxTest() {
		Assert.assertFalse(new LabeledCheckBox("Label 1").isChecked());
		Assert.assertTrue(new LabeledCheckBox("Label 2").isChecked());
	}
}
