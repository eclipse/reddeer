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
package org.jboss.reddeer.swt.generator.internal.framework.referencedComposite;


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
		return "org.jboss.reddeer.uiforms.section.UIFormSection";
	}

}
