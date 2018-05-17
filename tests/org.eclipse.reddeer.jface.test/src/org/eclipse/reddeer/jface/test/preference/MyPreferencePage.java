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
package org.eclipse.reddeer.jface.test.preference;

import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MyPreferencePage extends PreferencePage {
		
		static boolean valid = false;

		@Override
		protected Control createContents(Composite arg0) {
			Composite comp = new Composite(arg0, SWT.NONE);
			RowLayoutFactory.fillDefaults().applyTo(comp);
			Button b = new Button(comp, SWT.PUSH);
			b.setText("make valid");
			b.addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					valid = true;
					getContainer().updateButtons();
				}
			});
			
			setTitle("page");
			return comp;
		}
		
		@Override
		public boolean isValid() {
			return valid;
		}
		
	}