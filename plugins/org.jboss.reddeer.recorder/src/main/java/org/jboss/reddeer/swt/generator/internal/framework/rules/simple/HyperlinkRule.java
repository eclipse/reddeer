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
package org.jboss.reddeer.swt.generator.internal.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.jboss.reddeer.swt.generator.internal.framework.referencedComposite.ReferencedComposite;
import org.jboss.reddeer.swt.generator.internal.framework.rules.RedDeerUtils;

public class HyperlinkRule extends AbstractSimpleRedDeerRule{
	
	private String text;
	private int index;
	private List<ReferencedComposite> composites;

	@Override
	public boolean appliesTo(Event event) {
		return (event.widget instanceof Hyperlink || event.widget instanceof ImageHyperlink) && event.type == SWT.MouseDown;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.widget = event.widget;
		this.text = ((Hyperlink)event.widget).getText();
		this.index = WidgetUtils.getIndex((Hyperlink)event.widget);
		this.composites = RedDeerUtils.getComposites((Hyperlink)event.widget);
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		builder.append("new UIFormHyperlink(");
		builder.append(RedDeerUtils.getReferencedCompositeString(composites));
		builder.append("\""+text+"\").click()");
		toReturn.add(builder.toString());
		return toReturn;
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.uiforms.hyperlink.UIFormHyperlink");
		for(ReferencedComposite r: composites){
			toReturn.add(r.getImport());
		}
		return toReturn;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((composites == null) ? 0 : composites.hashCode());
		result = prime * result + index;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HyperlinkRule other = (HyperlinkRule) obj;
		if (composites == null) {
			if (other.composites != null)
				return false;
		} else if (!composites.equals(other.composites))
			return false;
		if (index != other.index)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	

}
