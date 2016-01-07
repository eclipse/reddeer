/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.generator.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.part.ViewPart;
import org.hamcrest.core.IsInstanceOf;
import org.jboss.reddeer.generator.ButtonCodeGenerator;
import org.jboss.reddeer.generator.CodeGenerator;
import org.jboss.reddeer.generator.ComboCodeGenerator;
import org.jboss.reddeer.generator.ShellCodeGenerator;
import org.jboss.reddeer.generator.TextCodeGenerator;
import org.jboss.reddeer.generator.finder.ControlFinder;
import org.jboss.reddeer.core.lookup.WidgetLookup;

/**
 * Code Generator view.
 * 
 * @author apodhrad
 *
 */
public class CodeGeneratorView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.jboss.reddeer.generator.views.CodeGeneratorView";

	private Composite parent;
	private ControlFinder controlFinder;
	private StyledText text;
	private List<CodeGenerator> codeGenerators;

	/**
	 * Constructs Code Generator view with the following generators:
	 * <ul>
	 * <li>ShellCodeGenerator
	 * <li>TextCodeGenerator
	 * <li>ButtonCodeGenerator
	 * <li>ComboCodeGenerator
	 * </ul>
	 */
	public CodeGeneratorView() {
		super();

		controlFinder = new ControlFinder();
		codeGenerators = new ArrayList<CodeGenerator>();
		codeGenerators.add(new ShellCodeGenerator());
		codeGenerators.add(new TextCodeGenerator());
		codeGenerators.add(new ButtonCodeGenerator());
		codeGenerators.add(new ComboCodeGenerator());
	}

	/**
	 * Adds a given code generator.
	 * 
	 * @param codeGenerator
	 *            code generator
	 */
	public void addCodeGenerator(CodeGenerator codeGenerator) {
		codeGenerators.add(codeGenerator);
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;

		createTextOutput();
		hookCodeGenerator();
	}

	/**
	 * Creates styled text.
	 */
	protected void createTextOutput() {
		text = new StyledText(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		text.setText("Press ALT + g to generate code");
	}

	/**
	 * Hooks CTRL+SHIFT listener which calls code generating.
	 */
	protected void hookCodeGenerator() {
		parent.getDisplay().addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event e) {
				if ((e.stateMask == SWT.ALT) && (e.keyCode == 'g') && !text.isDisposed()) {
					generateCode(WidgetLookup.getInstance().getActiveWidgetParentControl());
				}
			}
		});
	}

	/**
	 * Calls all registered code generators on a given parent control/widget.
	 * 
	 * @param parentControl
	 *            parent control/widget
	 */
	protected void generateCode(Control parentControl) {
		text.setText("/* Code generated from " + parentControl.getClass() + " */\n\n");
		List<Control> controls = controlFinder.find(parentControl, new IsInstanceOf(Control.class));
		for (Control control : controls) {
			for (CodeGenerator codeGenerator : codeGenerators) {
				if (!codeGenerator.isSupported(control)) {
					continue;
				}
				String generatedCode = codeGenerator.getGeneratedCode(control);
				if (generatedCode != null) {
					text.append(generatedCode);
					text.append("\n");
				}
			}
		}
	}

	@Override
	public void setFocus() {
		// we don't need any focus
	}

}