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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.reddeer.codegen.CodeGen;
import org.eclipse.reddeer.codegen.builder.MethodBuilder;
import org.eclipse.reddeer.codegen.rules.CodeGenRules;
import org.eclipse.reddeer.codegen.wizards.MethodsPage;
import org.eclipse.reddeer.swt.generator.framework.rules.RedDeerUtils;
import org.eclipse.reddeer.swt.generator.framework.rules.simple.ButtonRule;

/**
 * RedDeer CodeGen rules for Button widget/control.
 * 
 * @author djelinek
 */
public class ButtonCodeGenRule extends ButtonRule implements CodeGen {

	private String suffix;

	@Override
	public boolean appliesTo(Event event) {
		event.type = SWT.Selection;
		return super.appliesTo(event);
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
		String label = getText();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		String type = getType();
		String ref = RedDeerUtils.getReferencedCompositeString(getComposites());
		if (!ref.isEmpty())
			suffix = suffix + "group";
		return MethodBuilder.method().returnType(type).get(label + suffix)
				.returnCommand("new " + type + "(" + ref + WidgetUtils.cleanText(label) + ")").type(MethodsPage.GETTER)
				.rule(suffix);
	}

	/**
	 * Create action method – click, toogle, etc.
	 * 
	 * @param control
	 *            SWT widget
	 * @return MethodBuilder instance
	 */
	public MethodBuilder action(Control control) {
		String label = getText();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		String comm = getCommand("btn");
		String actionText = comm.substring(comm.lastIndexOf("."), comm.lastIndexOf("("));
		String ref = RedDeerUtils.getReferencedCompositeString(getComposites());
		if (!ref.isEmpty())
			suffix = "Group";
		if (actionText.equals(".toggle"))
			return MethodBuilder.method().name(actionText + " " + WidgetUtils.cleanText(label) + suffix)
					.parameter("boolean choice").command(comm).type(MethodsPage.ACTION);
		else
			return MethodBuilder.method().name(actionText + " " + WidgetUtils.cleanText(label) + suffix).command(comm)
					.type(MethodsPage.ACTION).rule(suffix);
	}

	/**
	 * Create isChecked method - CheckBox
	 * 
	 * @param control
	 *            SWT widget
	 * @return MethodBuilder instance
	 */
	public MethodBuilder isChecked(Control control) {
		String label = getText();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		String ref = RedDeerUtils.getReferencedCompositeString(getComposites());
		if (!ref.isEmpty())
			suffix = "Group";
		return MethodBuilder.method().returnType("boolean").name("isChecked " + WidgetUtils.cleanText(label) + suffix)
				.returnCommand(getCommand("check")).type(MethodsPage.ACTION).rule(suffix);
	}

	/**
	 * Create isSelected method - RadioButton
	 * 
	 * @param control
	 *            SWT widget
	 * @return MethodBuilder instance
	 */
	public MethodBuilder isSelected(Control control) {
		String label = getText();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		String ref = RedDeerUtils.getReferencedCompositeString(getComposites());
		if (!ref.isEmpty())
			suffix = "Group";
		return MethodBuilder.method().returnType("boolean").name("isSelected " + WidgetUtils.cleanText(label) + suffix)
				.returnCommand(getCommand("select")).type(MethodsPage.ACTION).rule(suffix);
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
		String label = getText();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		return MethodBuilder.method().returnType("String").get("Text " + label).returnCommand(getCommand("get"))
				.type(MethodsPage.GETTER).rule(suffix);
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
		forReturn.add(get(control));
		forReturn.add(action(control));
		switch (getType()) {
		case "CheckBox":
			forReturn.add(isChecked(control));
			break;
		case "RadioButton":
			forReturn.add(isSelected(control));
			break;
		default:
			break;
		}
		return forReturn;
	}

	/**
	 * Return button type and set right button suffix
	 * 
	 * @return String
	 */
	public String getType() {
		String type = null;
		int style = getStyle();
		if ((style & SWT.PUSH) != 0) {
			type = "PushButton";
			suffix = CodeGenRules.BUTTON_PUSH_SUFFIX;
		} else if ((style & SWT.CHECK) != 0) {
			type = "CheckBox";
			suffix = CodeGenRules.BUTTON_CHECK_SUFFIX;
		} else if ((style & SWT.ARROW) != 0) {
			type = "ArrowButton";
			suffix = CodeGenRules.BUTTON_ARROW_SUFFIX;
		} else if ((style & SWT.RADIO) != 0) {
			type = "RadioButton";
			suffix = CodeGenRules.BUTTON_RADIO_SUFFIX;
		} else if ((style & SWT.TOGGLE) != 0) {
			type = "ToggleButton";
			suffix = CodeGenRules.BUTTON_TOGGLE_SUFFIX;
		}
		if (type == null) {
			throw new IllegalArgumentException("Unsupported button style " + style);
		}
		return type;
	}

	/**
	 * Return right action command for different button types
	 * 
	 * @param type
	 *            Button type (Radio, Push, Check, etc.)
	 * @return String
	 */
	public String getCommand(String type) {
		StringBuilder builder = new StringBuilder("new " + getType() + "(");
		builder.append(RedDeerUtils.getReferencedCompositeString(getComposites()));
		String text = getText();
		int index = getIndex();
		if (text == null || text.isEmpty()) {
			builder.append(index);
		} else {
			builder.append("\"" + WidgetUtils.cleanText(text) + "\"");
		}
		builder.append(")");
		if (type.equals("btn")) {
			if ((getStyle() & SWT.CHECK) != 0 || (getStyle() & SWT.RADIO) != 0) {
				builder.append(".toggle(choice)");
			} else {
				builder.append(".click()");
			}
		} else if (type.equals("get")) {
			builder.append(".getText()");
		} else if (type.equals("check")) {
			builder.append(".isChecked()");
		} else if (type.equals("select")) {
			builder.append(".isSelected()");
		}
		return builder.toString();
	}

}
