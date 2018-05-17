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
package org.eclipse.reddeer.codegen.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.reddeer.codegen.wizards.MethodsPage;
import org.eclipse.swt.SWT;

/**
 * Class for completing and building a structure of JAVA class.
 * 
 * @author djelinek
 */
public class ClassBuilder {

	private static final String SPACE = " ";

	private static final String SEMICOLON = ";";

	private static final String TAB = "\t";

	private static final String NEW_LINE = "\n";

	private static final String D_NEW_LINE = "\n\n";

	private static final String JAVA_QUALIFIER = ".java";

	private String className;

	private String visibility;

	private StringBuffer classBuilder;

	private String packageName;

	private List<String> imports;

	private List<String> selectedOptions;

	private List<MethodBuilder> methods;

	private Map<String, String> constants;

	private String extendedClass;

	public ClassBuilder(String name, String projectPackage) {
		this.className = getClassName(name);
		this.visibility = "public";
		this.packageName = projectPackage;
		this.extendedClass = "";
		methods = new ArrayList<>();
		imports = new ArrayList<>();
		selectedOptions = new ArrayList<>();
		constants = new TreeMap<>();
		classBuilder = new StringBuffer();
	}

	public ClassBuilder(String name, String projectPackage, List<MethodBuilder> methods) {
		this.className = getClassName(name);
		this.visibility = "public";
		this.packageName = projectPackage;
		this.methods = methods;
		this.extendedClass = "";
		imports = new ArrayList<>();
		selectedOptions = new ArrayList<>();
		constants = new TreeMap<>();
		classBuilder = new StringBuffer();
	}

	/**
	 * Default constructor with predefined class parameters
	 * 
	 * <ul>
	 * <li>className - DefaultCodeGenClass</li>
	 * <li>visibility - public</li>
	 * </ul>
	 */
	public ClassBuilder() {
		this.className = "CodeGenDefault";
		this.visibility = "public";
		this.packageName = "org";
		this.extendedClass = "";
		methods = new ArrayList<>();
		imports = new ArrayList<>();
		selectedOptions = new ArrayList<>();
		constants = new TreeMap<>();
		classBuilder = new StringBuffer();
	}

	/**
	 * Static method for ClassBuilder instance
	 * 
	 * @return ClassBuilder instance
	 */
	public static ClassBuilder getInstance() {
		return new ClassBuilder();
	}

	/**
	 * Create class method with defined name
	 * 
	 * @param name
	 *            String
	 */
	public void createMethod(String name) {
		methods.add(new MethodBuilder().name(name));
	}

	/**
	 * Adds method
	 * 
	 * @param method
	 *            MethodBuilder - method
	 */
	public void addMethod(MethodBuilder method) {
		for (MethodBuilder m : methods) {
			if (m.getName().equals(method.getName()))
				return;
		}
		methods.add(method);
	}

	/**
	 * Adds list of methods
	 * 
	 * @param methods
	 *            MethodBuilder - list of methods
	 */
	public void addMethods(List<MethodBuilder> meths) {
		boolean exist;
		for (MethodBuilder meth : meths) {
			exist = false;
			for (MethodBuilder m : methods) {
				if (m.getName().equals(meth.getName()))
					exist = true;
			}
			if (!exist)
				this.methods.add(meth);
		}
	}

	/**
	 * Adds package to class imported packages
	 * 
	 * @param packageName
	 *            String - name for class imported packages
	 */
	public void setPackage(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Adds import to class
	 * 
	 * @param importName
	 *            String - name for class import
	 */
	public void addImport(String importName) {
		if (!this.imports.contains(importName))
			this.imports.add(importName);
	}

	/**
	 * Adds imports to class
	 * 
	 * @param imports
	 *            List of imports
	 */
	public void addImports(List<String> imports) {
		for (String im : imports) {
			if (!this.imports.contains(im))
				this.imports.add(im);
		}
	}

	/**
	 * Delete all class imports
	 */
	public void clearImports() {
		this.imports.clear();
	}

	/**
	 * Delete specific import
	 * 
	 * @param importt
	 *            String
	 */
	public void removeImport(String importt) {
		if (imports.contains(importt))
			imports.remove(importt);
	}

	/**
	 * Delete specific import
	 * 
	 * @param type
	 *            SWT
	 */
	public void removeImport(int type) {
		String importt = "";
		switch (type) {
		case SWT.PUSH:
			importt = "org.eclipse.reddeer.swt.impl.button.PushButton";
			break;
		case SWT.CHECK:
			importt = "org.eclipse.reddeer.swt.impl.button.CheckBox";
			break;
		case SWT.ARROW:
			importt = "org.eclipse.reddeer.swt.impl.button.ArrowButton";
			break;
		case SWT.RADIO:
			importt = "org.eclipse.reddeer.swt.impl.button.RadioButton";
			break;
		case SWT.TOGGLE:
			importt = "org.eclipse.reddeer.swt.impl.button.ToggleButton";
			break;
		default:
			break;
		}
		if (imports.contains(importt))
			imports.remove(importt);
	}

	/**
	 * Add new selected additional property to optional properties list
	 * 
	 * @param options
	 *            List of selected optional properties
	 */
	public void addOptions(List<String> options) {
		this.selectedOptions = options;
	}

	/**
	 * Sets name of class
	 * 
	 * @param name
	 *            String
	 */
	public void setClassName(String name) {
		this.className = name;
	}

	/**
	 * Get class name
	 * 
	 * @return String
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * Get package name
	 * 
	 * @return String
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * Get extended class name
	 * 
	 * @return String
	 */
	public String getExtendedClass() {
		return this.extendedClass;
	}

	/**
	 * Set extended class name
	 * 
	 * @param name
	 *            String
	 */
	public void setExtendedClass(String name) {
		this.extendedClass = name;
	}

	/**
	 * Add new constants
	 * 
	 * @param map
	 *            Map of generated constants
	 */
	public void addConstants(Map<String, String> map) {
		if (map != null)
			this.constants.putAll(map);
	}

	@Override
	public String toString() {
		classBuilder = new StringBuffer(iniComment());
		// Add all packages into class
		classBuilder.append("package").append(SPACE).append(packageName).append(SEMICOLON).append(D_NEW_LINE);
		// remove methods which will be inherit and use appropriate "extends" class
		// header
		boolean extend = removeInheriteMethods();
		// Add all imports into class
		for (String projectImport : imports) {
			classBuilder.append("import").append(SPACE).append(projectImport).append(SEMICOLON).append(NEW_LINE);
		}
		if (!imports.isEmpty())
			classBuilder.append(NEW_LINE);
		// create head of class
		if (extend && selectedOptions.contains(MethodsPage.INHERITING)
				&& selectedOptions.contains(MethodsPage.INCLUDE_ALL)) {
			classBuilder.append(visibility).append(SPACE).append("class").append(SPACE).append(getClassName(className))
					.append(SPACE).append("extends").append(SPACE).append(extendedClass).append(SPACE).append("{");
		} else {
			classBuilder.append(visibility).append(SPACE).append("class").append(SPACE).append(getClassName(className))
					.append(SPACE).append("{");
		}
		// class constants
		if (!constants.isEmpty()) {
			for (String constant : constants.keySet()) {
				classBuilder.append(D_NEW_LINE).append(TAB).append("public static final String").append(SPACE)
						.append(constant).append(" = ").append("\"" + constants.get(constant) + "\"").append(SEMICOLON);
			}
		}
		// class methods
		Collections.sort(methods);
		classBuilder.append(D_NEW_LINE).append(TAB).append("// Generated class methods ").append("(")
				.append(methods.size()).append(")").append(NEW_LINE);
		for (MethodBuilder method : methods) {
			classBuilder.append(method.toString()).append(D_NEW_LINE);
		}
		// end of class
		classBuilder.append("}");
		return classBuilder.toString();
	}

	/**
	 * Validate if class could inherit or not
	 * 
	 * @return true/false
	 */
	public boolean isExtendible() {
		boolean extendible = false;
		for (MethodBuilder meth : this.methods) {
			if (meth.getName().contains("Finish") || meth.getName().contains("Cancel")
					|| meth.getName().contains("Next") || meth.getName().contains("Back")
					|| meth.getName().contains("OK")) {
				extendible = true;
			}
		}
		return extendible;
	}

	/**
	 * Remove methods which will be inherit
	 */
	public boolean removeInheriteMethods() {
		List<MethodBuilder> toRemove = new ArrayList<>();
		for (MethodBuilder meth : this.methods) {
			if (meth.getName().contains("Finish") || meth.getName().contains("Cancel")
					|| meth.getName().contains("Next") || meth.getName().contains("Back")
					|| meth.getName().contains("OK")) {
				toRemove.add(meth);
			}
		}
		if (isExtendible() && selectedOptions.contains(MethodsPage.INHERITING)
				&& selectedOptions.contains(MethodsPage.INCLUDE_ALL)) {
			for (MethodBuilder rm : toRemove) {
				methods.remove(rm);
			}
			int i = 0;
			for (MethodBuilder meth : this.methods) {
				if (meth.getRule().equals("BTN"))
					i++;
			}
			if (i == 0) {
				removeImport(SWT.PUSH);
			}
		}
		return true;
	}

	/**
	 * Generate initial header (initial class comment)
	 * 
	 * @return String
	 */
	private String iniComment() {
		StringBuffer sb = new StringBuffer();
		sb.append("/** \n" + " * This class '" + getFileName(className)
				+ "' was generated by RedDeer Code Generator. \n");
		sb.append(" * Selected options:");
		for (String optional : selectedOptions) {
			sb.append("\n *").append(TAB).append(TAB).append(optional);
		}
		sb.append("\n */ \n");
		return sb.toString();
	}

	/**
	 * Get class name (advanced), could contains .java
	 * 
	 * @param name
	 * @return String
	 */
	private String getClassName(String name) {
		try {
			if (name.substring(name.lastIndexOf("."), name.length()).equals(JAVA_QUALIFIER))
				return name.substring(0, name.lastIndexOf("."));
			else
				return name;
		} catch (Exception e) {
			return name;
		}
	}

	/**
	 * return file name as filename.java
	 * 
	 * @param name
	 * @return String
	 */
	private String getFileName(String name) {
		try {
			if (name.substring(name.lastIndexOf("."), name.length()).equals(JAVA_QUALIFIER))
				return name;
			else
				return name + JAVA_QUALIFIER;
		} catch (Exception e) {
			return name + JAVA_QUALIFIER;
		}
	}

}
