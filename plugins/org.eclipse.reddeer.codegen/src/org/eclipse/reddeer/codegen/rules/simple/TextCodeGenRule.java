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
package org.eclipse.reddeer.codegen.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.reddeer.codegen.CodeGen;
import org.eclipse.reddeer.codegen.builder.MethodBuilder;
import org.eclipse.reddeer.codegen.rules.CodeGenRules;
import org.eclipse.reddeer.codegen.wizards.MethodsPage;
import org.eclipse.reddeer.swt.generator.framework.rules.RedDeerUtils;
import org.eclipse.reddeer.swt.generator.framework.rules.simple.TextRule;

/**
 * RedDeer CodeGen rules for Text widget/control.
 * 
 * @author djelinek
 */
public class TextCodeGenRule extends TextRule implements CodeGen {

	@Override
	public boolean appliesTo(Event event) {
		event.type = SWT.Modify;
		if (event.widget instanceof Text)
			try {
				if (!WidgetUtils.cleanText(WidgetUtils.getLabel((Text) event.widget)).isEmpty())
					return true;
				else
					return false;
			} catch (Exception e) {
				return false;
			}
		else
			return false;
	}

	/**
	 * Create constructor method
	 * 
	 * @param control
	 *            SWT widget
	 * @return MethodBuilder instance
	 */
	@Override
	public MethodBuilder constructor(Control control) {
		String type = "LabeledText";
		String label = getLabel();
		if (label == null || label.isEmpty()) {
			type = "DefaultText";
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		String ref = RedDeerUtils.getReferencedCompositeString(RedDeerUtils.getComposites(control));
		return MethodBuilder.method().returnType(type).get(label + CodeGenRules.TEXT_SUFFIX)
				.returnCommand("new " + type + "(" + ref + WidgetUtils.cleanText(label) + ")").type(MethodsPage.GETTER)
				.rule(CodeGenRules.TEXT_SUFFIX);
	}

	/**
	 * Create setter method
	 * 
	 * @param control
	 *            SWT widget
	 * @return MethodBuilder instance
	 */
	public MethodBuilder set(Control control) {
		String label = getLabel();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		return MethodBuilder.method().name("setText " + label).parameter("String str").command(getCommand("set"))
				.type(MethodsPage.SETTER).rule(CodeGenRules.TEXT_SUFFIX);
	}

	/**
	 * Create getter method
	 * 
	 * @param control
	 *            SWT widget
	 * @return MethodBuilder instance
	 */
	@Override
	public MethodBuilder get(Control control) {
		String label = getLabel();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		return MethodBuilder.method().returnType("String").get("Text " + label).returnCommand(getCommand("get"))
				.type(MethodsPage.GETTER).rule(CodeGenRules.TEXT_SUFFIX);
	}

	/**
	 * Returns lit of available/created methods
	 * 
	 * @param control
	 *            SWT widget
	 * @return List<MethodBuilder>
	 */
	@Override
	public List<MethodBuilder> getActionMethods(Control control) {
		List<MethodBuilder> forReturn = new ArrayList<>();
		forReturn.add(constructor(control));
		forReturn.add(set(control));
		forReturn.add(get(control));
		return forReturn;
	}

	/**
	 * Return right command for available actions (get/set)
	 * 
	 * @param type
	 *            Getter/Setter
	 * @return String
	 */
	public String getCommand(String type) {
		StringBuffer sb = new StringBuffer();
		String label = getLabel();
		if (label != null) {
			sb.append("new LabeledText(");
			sb.append(RedDeerUtils.getReferencedCompositeString(getComposites()));
			sb.append("\"" + WidgetUtils.cleanText(label) + "\"");
		} else {
			sb.append("new DefaultText(");
			sb.append(RedDeerUtils.getReferencedCompositeString(getComposites()));
			sb.append(getIndex());
		}
		if (type.equals("set"))
			sb.append(").setText(str)");
		else if (type.equals("get"))
			sb.append(").getText()");
		return sb.toString();
	}

}
