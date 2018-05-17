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
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.reddeer.codegen.CodeGen;
import org.eclipse.reddeer.codegen.builder.MethodBuilder;
import org.eclipse.reddeer.codegen.rules.CodeGenRules;
import org.eclipse.reddeer.codegen.wizards.MethodsPage;
import org.eclipse.reddeer.swt.generator.framework.rules.RedDeerUtils;
import org.eclipse.reddeer.swt.generator.framework.rules.simple.ComboRule;

/**
 * RedDeer CodeGen rules for Combo widget/control.
 * 
 * @author djelinek
 */
public class ComboCodeGenRule extends ComboRule implements CodeGen {

	private Event event;

	private List<String> forReturn;

	public ComboCodeGenRule() {
		forReturn = new ArrayList<>();
	}

	@Override
	public boolean appliesTo(Event ev) {
		this.event = ev;
		event.type = SWT.Modify;
		if (event.widget instanceof Combo)
			try {
				if (!WidgetUtils.cleanText(WidgetUtils.getLabel((Combo) event.widget)).isEmpty())
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
		String type = "LabeledCombo";
		String label = getLabel();
		if (label == null || label.isEmpty()) {
			type = "DefaultCombo";
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		String ref = RedDeerUtils.getReferencedCompositeString(getComposites());
		return MethodBuilder.method().returnType(type).get(label + CodeGenRules.COMBO_SUFFIX)
				.returnCommand("new " + type + "(" + ref + WidgetUtils.cleanText(label) + ")").type(MethodsPage.GETTER)
				.rule(CodeGenRules.COMBO_SUFFIX);
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
				.type(MethodsPage.GETTER).rule(CodeGenRules.COMBO_SUFFIX);
	}

	/**
	 * Create combo box get selection method
	 * 
	 * @param control
	 *            SWT widget
	 * @return MethodBuilder instance
	 */
	public MethodBuilder getSelection(Control control) {
		String label = getLabel();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		return MethodBuilder.method().returnType("String").get("Selection" + label)
				.returnCommand(getCommand("getSelection")).type(MethodsPage.GETTER).rule(CodeGenRules.COMBO_SUFFIX);
	}

	/**
	 * Create combo box get items method
	 * 
	 * @param control
	 *            SWT widget
	 * @return MethodBuilder instance
	 */
	public MethodBuilder getItems(Control control) {
		String label = getLabel();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		return MethodBuilder.method().returnType("List<String>").get("Items" + label).returnCommand(getCommand("items"))
				.type(MethodsPage.GETTER).rule(CodeGenRules.COMBO_SUFFIX);
	}

	/**
	 * Create combo box selection method
	 * 
	 * @param control
	 *            SWT widget
	 * @return MethodBuilder instance
	 */
	public MethodBuilder setSelection(Control control) {
		String label = getLabel();
		if (label == null || label.isEmpty()) {
			label = String.valueOf(getIndex());
		} else {
			label = "\"" + label + "\"";
		}
		return MethodBuilder.method().name("setSelection " + label).parameter("String str")
				.command(getCommand("setSelection")).type(MethodsPage.SETTER).rule(CodeGenRules.COMBO_SUFFIX);
	}

	/**
	 * Return map of combo box items
	 * 
	 * @param control
	 *            SWT widget
	 * @return Map<String, String>
	 */
	public Map<String, String> getSelectionList(Control control) {
		Combo combo = ((Combo) control);
		Map<String, String> items = new TreeMap<>();
		if (combo.getItemCount() > 0) {
			for (String item : combo.getItems()) {
				String key = constantMask(WidgetUtils.cleanText(item).toUpperCase().replaceAll("\\W", " "));
				items.put(key, item);
			}
			return items;
		}
		return null;
	}

	@Override
	public List<String> getImports() {
		forReturn = super.getImports();
		// isn't right –> shouldn't be added when get method isn't in
		// ClassBuilder ...
		forReturn.add("java.util.List");
		return forReturn;
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
		forReturn.add(getSelection(control));
		forReturn.add(getItems(control));
		forReturn.add(setSelection(control));
		return forReturn;
	}

	/**
	 * Return right command for getter or setter variant
	 * 
	 * @param type
	 *            Getter/Setter
	 * @return String
	 */
	public String getCommand(String type) {
		StringBuffer sb = new StringBuffer();
		String label = getLabel();
		if (label != null) {
			sb.append("new LabeledCombo(");
			sb.append(RedDeerUtils.getReferencedCompositeString(getComposites()));
			sb.append("\"" + WidgetUtils.cleanText(label) + "\"");
		} else {
			sb.append("new DefaultCombo(");
			sb.append(RedDeerUtils.getReferencedCompositeString(getComposites()));
			sb.append(getIndex());
		}
		if (type.equals("setSelection"))
			sb.append(").setSelection(str)");
		else if (type.equals("get"))
			sb.append(").getText()");
		else if (type.equals("getSelection"))
			sb.append(").getSelection()");
		else if (type.equals("items"))
			sb.append(").getItems()");
		return sb.toString();
	}

	/**
	 * Return right name string mask for generated static combo constants
	 * 
	 * @param str
	 *            String
	 * 
	 * @return String
	 */
	private String constantMask(String str) {
		List<String> list = new ArrayList<>();
		String group = "";
		try {
			group = WidgetUtils.getGroup(((Combo) event.widget)).replaceAll("\\W", " ");
		} catch (Exception e) {
			group = getLabel().replaceAll("\\W", " ");
		}
		String[] s1 = group.split(" ");
		for (int i = 0; i < s1.length; i++) {
			if (!s1[i].isEmpty())
				list.add(s1[i].trim().toUpperCase());
		}
		String[] s2 = str.split(" ");
		for (int i = 0; i < s2.length; i++) {
			if (!s2[i].isEmpty())
				list.add(s2[i].trim().toUpperCase());
		}
		return String.join("_", list);
	}

}
