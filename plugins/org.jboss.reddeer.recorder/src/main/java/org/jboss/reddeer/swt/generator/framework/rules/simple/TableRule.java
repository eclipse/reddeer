package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.jboss.reddeer.swt.generator.framework.referencedComposite.ReferencedComposite;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;

public class TableRule extends GenerationSimpleRule {
	
	private int index;
	private int items[];
	private List<String> listOfSelectedItems = new ArrayList<String>();
	private boolean check;
	private boolean checkDetail;
	private java.util.List<ReferencedComposite> composites;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Table && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		Table table = (Table)event.widget;
		if(!tableHasDuplicates(table.getItems())){
			for(TableItem item: table.getSelection()){
				this.listOfSelectedItems.add(WidgetUtils.cleanText(item.getText()));
			}
		} else {
			this.items = table.getSelectionIndices(); 
		}
		this.index = WidgetUtils.getIndex(table);
		Shell s = WidgetUtils.getShell((Table)event.widget);
		this.setComposites(RedDeerUtils.getComposites(table));
		if(s!=null){
			setShellTitle(s.getText());
		}
		if(checkDetail = event.detail == SWT.CHECK){
			check = table.getSelection()[0].getChecked();
		}
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder  = new StringBuilder();
		builder.append("new DefaultTable(");
		builder.append(RedDeerUtils.getReferencedCompositeString(composites));
		builder.append(index);
		builder.append(")");
		if(!listOfSelectedItems.isEmpty()){
			builder.append(".getItem(\""+listOfSelectedItems.get(0)+"\")");
		} else {
			builder.append(".getItem("+items[0]+")");
		}
		if(checkDetail){
			builder.append(".setChecked("+check+")");
		} else {
			builder.append(".select()");
		}
		toReturn.add(builder.toString());
		return toReturn;
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.table.DefaultTable");
		for(ReferencedComposite r: composites){
			toReturn.add(r.getImport());
		}
		return toReturn;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<String> getListOfSelectedItems() {
		return listOfSelectedItems;
	}

	public void setListOfSelectedItems(List<String> listOfSelectedItems) {
		this.listOfSelectedItems = listOfSelectedItems;
	}
	
	private boolean tableHasDuplicates(TableItem tableItems[]){
		Set<String> setOfStrings = new HashSet<String>();
		for(TableItem item: tableItems){
			if(!setOfStrings.add(item.getText())){
				return true;
			}
			
		}
		return false;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public java.util.List<ReferencedComposite> getComposites() {
		return composites;
	}

	public void setComposites(java.util.List<ReferencedComposite> composites) {
		this.composites = composites;
	}
}
