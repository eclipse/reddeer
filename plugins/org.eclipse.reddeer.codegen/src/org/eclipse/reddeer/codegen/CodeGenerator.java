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
package org.eclipse.reddeer.codegen;

import java.util.List;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.ui.internal.dialogs.WorkbenchPreferenceDialog;
import org.hamcrest.core.IsInstanceOf;
import org.eclipse.reddeer.codegen.builder.ClassBuilder;
import org.eclipse.reddeer.codegen.builder.MethodBuilder;
import org.eclipse.reddeer.codegen.finder.ControlFinder;
import org.eclipse.reddeer.codegen.rules.CodeGenRules;
import org.eclipse.reddeer.codegen.rules.simple.ButtonCodeGenRule;
import org.eclipse.reddeer.codegen.rules.simple.ComboCodeGenRule;
import org.eclipse.reddeer.codegen.rules.simple.ShellCodeGenRule;
import org.eclipse.reddeer.codegen.rules.simple.TextCodeGenRule;
import org.eclipse.reddeer.codegen.wizards.MethodsPage;

/**
 * Class for generating source code for all supported widget rules. This class
 * is also looking for parent control.
 * 
 * @author djelinek
 */
@SuppressWarnings("restriction")
public class CodeGenerator {

	public static final String WIZARD_DIALOG = "WizardDialog";

	public static final String WIZARD_DIALOG_IMPORT = "org.eclipse.reddeer.jface.wizard.WizardDialog";

	public static final String PREFERENCE_DIALOG = "PreferenceDialog";

	public static final String PREFERENCE_DIALOG_IMPORT = "org.eclipse.reddeer.jface.preference.PreferenceDialog";

	private Shell lastActiveShell;

	private ControlFinder controlFinder;

	private ClassBuilder classBuilder;

	private List<String> options;

	/**
	 * Default Code Generator constructor
	 * 
	 * @param className
	 * @param packageName
	 * @param optional
	 *            list of checked optional properties from second wizard page
	 */
	public CodeGenerator(String className, String packageName, List<String> optional) {
		controlFinder = new ControlFinder();
		classBuilder = new ClassBuilder();
		this.classBuilder.setClassName(className);
		this.classBuilder.setPackage(packageName);
		this.options = optional;
	}

	/**
	 * This method is looking for parent Control of first wizard under Code
	 * Generator wizard
	 * 
	 * @return parent wizard Control
	 */
	public Control getControl() {
		Control[] c = lastActiveShell.getChildren();
		Object o = lastActiveShell.getData();
		if (!options.contains(MethodsPage.INCLUDE_ALL) && !options.contains(MethodsPage.INHERITING)) {
			if (o instanceof WizardDialog) {
				return ((WizardDialog) o).getCurrentPage().getControl();
			} else if (o instanceof WorkbenchPreferenceDialog) {
				return ((WorkbenchPreferenceDialog) o).getCurrentPage().getControl();
			} else
				return c[0];
		} else {
			if (options.contains(MethodsPage.INHERITING)) {
				if (o instanceof WizardDialog) {
					classBuilder.setExtendedClass(WIZARD_DIALOG);
				} else if (o instanceof WorkbenchPreferenceDialog) {
					classBuilder.setExtendedClass(PREFERENCE_DIALOG);
				}
			}
			return c[0];
		}
	}

	/**
	 * Generates code (methods, imports, etc.) for all supported widgets at found
	 * Control.
	 * 
	 * @return ClassBuilder instance
	 */
	public ClassBuilder generateCode() {
		classBuilder.addOptions(options);
		classBuilder.clearImports();
		List<Control> controls = controlFinder.find(getControl(), new IsInstanceOf(Control.class));
		controls.add(lastActiveShell);
		List<GenerationSimpleRule> simples = new CodeGenRules().createSimpleRules();
		Event e = new Event();
		for (Control control : controls) {
			e.widget = control;
			for (GenerationSimpleRule rule : simples) {
				if (!rule.appliesTo(e)) {
					continue;
				}
				rule.initializeForEvent(e);
				if (rule instanceof ButtonCodeGenRule) {
					for (MethodBuilder meth : ((ButtonCodeGenRule) rule).getActionMethods(control)) {
						if (options.contains(meth.getMethodType())) {
							classBuilder.addMethod(meth);
							classBuilder.addImports(rule.getImports());
						}
					}
				} else if (rule instanceof TextCodeGenRule) {
					for (MethodBuilder meth : ((TextCodeGenRule) rule).getActionMethods(control)) {
						if (options.contains(meth.getMethodType())) {
							classBuilder.addMethod(meth);
							classBuilder.addImports(rule.getImports());
						}
					}
				} else if (rule instanceof ComboCodeGenRule) {
					for (MethodBuilder meth : ((ComboCodeGenRule) rule).getActionMethods(control)) {
						if (options.contains(MethodsPage.CONSTANTS))
							classBuilder.addConstants(((ComboCodeGenRule) rule).getSelectionList(control));
						if (options.contains(meth.getMethodType())) {
							classBuilder.addMethod(meth);
							classBuilder.addImports(rule.getImports());
						}
					}
				} else if (rule instanceof ShellCodeGenRule) {
					for (MethodBuilder meth : ((ShellCodeGenRule) rule).getActionMethods(control)) {
						if (options.contains(meth.getMethodType())) {
							classBuilder.addMethod(meth);
							classBuilder.addImports(rule.getImports());
						}
					}
				}
			}
			if (classBuilder.getExtendedClass().equals(WIZARD_DIALOG) && classBuilder.isExtendible())
				classBuilder.addImport(WIZARD_DIALOG_IMPORT);
			else if (classBuilder.getExtendedClass().equals(PREFERENCE_DIALOG) && classBuilder.isExtendible())
				classBuilder.addImport(PREFERENCE_DIALOG_IMPORT);
		}
		return classBuilder;
	}

	public void setLastActiveShell(Shell sh) {
		this.lastActiveShell = sh;
	}
}
