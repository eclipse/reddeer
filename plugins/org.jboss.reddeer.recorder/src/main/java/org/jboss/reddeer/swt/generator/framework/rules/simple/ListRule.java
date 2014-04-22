package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.jboss.reddeer.swt.generator.framework.referencedComposite.ReferencedComposite;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;

public class ListRule extends AbstractSimpleRedDeerRule{
	
	private String[] selectedItems;
	private int index;
	private String label;
	private java.util.List<ReferencedComposite> composites;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof List && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.widget = event.widget;
		selectedItems = ((List)event.widget).getSelection();
		for(int i=0;i<selectedItems.length;i++) {
			selectedItems[i] = WidgetUtils.cleanText(selectedItems[i]);
		}
		label = WidgetUtils.getLabel(((List)event.widget));
		index = WidgetUtils.getIndex(((List)event.widget));
		this.setComposites(RedDeerUtils.getComposites((List)event.widget));
	}

	@Override
	public java.util.List<String> getActions() {
		java.util.List<String> toReturn = new java.util.ArrayList<String>();
		StringBuilder list = new StringBuilder();
		list.append("new DefaultList(");
		list.append(RedDeerUtils.getReferencedCompositeString(composites));
		if(label != null){
			list.append("\""+label+"\"");
		} else {
			list.append(index);
		}
		list.append(").select(");
		for(int i=0; i<selectedItems.length;i++){
			list.append("\""+selectedItems[i]+"\"");
			if(i+1<selectedItems.length){
				list.append(",");
			}
		}
		list.append(")");
		toReturn.add(list.toString());
		return toReturn;
	}
	
	@Override
	public java.util.List<String> getImports() {
		java.util.List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.list.DefaultList");
		for(ReferencedComposite r: composites){
			toReturn.add(r.getImport());
		}
		return toReturn;
	}

	public java.util.List<ReferencedComposite> getComposites() {
		return composites;
	}

	public void setComposites(java.util.List<ReferencedComposite> composites) {
		this.composites = composites;
	}
	
	
}
