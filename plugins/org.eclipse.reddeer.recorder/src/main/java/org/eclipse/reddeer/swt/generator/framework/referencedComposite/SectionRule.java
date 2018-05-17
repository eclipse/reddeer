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
package org.eclipse.reddeer.swt.generator.framework.referencedComposite;


public class SectionRule implements ReferencedComposite{
	
	private String text;
	
	public SectionRule(String text){
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public String getImport() {
		return "org.eclipse.reddeer.uiforms.section.UIFormSection";
	}

}
