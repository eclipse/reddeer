/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Wizard page for creating an annotation.
 */
public class NewAnnotationWizardPage extends WizardPage {
	
	public NewAnnotationWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets a given package name.
	 * 
	 * @param packageName
	 *            Package name
	 */
	public NewAnnotationWizardPage setPackage(String packageName) {
		new LabeledText(this, "Package:").setText(packageName);
		return this;
	}

	/**
	 * Sets a given source folder.
	 * 
	 * @param sourceFolder
	 *            Source folder
	 */
	public NewAnnotationWizardPage setSourceFolder(String sourceFolder) {
		new LabeledText(this, "Source folder:").setText(sourceFolder);
		return this;
	}

	/**
	 * Sets a given enclosing type.
	 * 
	 * @param enclosing
	 *            Eclosing type
	 */
	public NewAnnotationWizardPage setEnclosingType(boolean enclosing) {
		new CheckBox(this, "Enclosing type:").toggle(enclosing);
		return this;
	}

	/**
	 * Sets a given name.
	 * 
	 * @param name
	 *            Name
	 */
	public NewAnnotationWizardPage setName(String name) {
		new LabeledText(this, "Name:").setText(name);
		return this;
	}

	/**
	 * Sets generating comments.
	 * 
	 * @param generate
	 *            Indicates whether to generate comments
	 */
	public NewAnnotationWizardPage setGenerateComments(boolean generate) {
		new CheckBox(this, "Generate comments").toggle(generate);
		return this;
	}

	/**
	 * Sets public visibility.
	 *
	 * @param isPublic            Is public?
	 */
	public NewAnnotationWizardPage setPublic(boolean isPublic) {
		new RadioButton(this, "public").toggle(isPublic);
		return this;
	}

	/**
	 * Sets default visibility.
	 * 
	 * @param isDefault
	 *            Is default?
	 */
	public NewAnnotationWizardPage setDefault(boolean isDefault) {
		new RadioButton(this, "default").toggle(isDefault);
		return this;
	}

	/**
	 * Sets private visibility.
	 * 
	 * @param isPrivate
	 *            Is private?
	 */
	public NewAnnotationWizardPage setPrivate(boolean isPrivate) {
		new RadioButton(this, "private").toggle(isPrivate);
		return this;
	}

	/**
	 * Sets protected visibility.
	 * 
	 * @param isProtected
	 *            Is protected?
	 */
	public NewAnnotationWizardPage setProtected(boolean isProtected) {
		new RadioButton(this, "protected").toggle(isProtected);
		return this;
	}

}
