package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.WidgetUtils;

public class CTabRule extends AbstractSimpleRedDeerRule{
	
	private String text;

	@Override
	public boolean appliesTo(Event event) {
		if(event.widget != null) {
			System.out.println(event.widget.getClass());
		} 
		if (event.item != null) {
			System.out.println(event.item.getClass());
		}
		return event.widget instanceof CTabFolder && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.widget = event.widget;
		this.text = WidgetUtils.cleanText(((CTabFolder)event.widget).getSelection().getText());
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("new DefaultCTabItem(\"" + text + "\").activate()");
		return toReturn;
	}

	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.ctab.DefaultCTabItem");
		return toReturn;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		CTabRule other = (CTabRule) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
