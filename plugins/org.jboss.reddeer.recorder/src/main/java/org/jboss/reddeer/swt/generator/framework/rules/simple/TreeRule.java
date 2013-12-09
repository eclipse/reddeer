package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.jboss.reddeer.swt.generator.framework.referencedComposite.ReferencedComposite;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;

public class TreeRule extends GenerationSimpleRule {
	
	private java.util.List<ReferencedComposite> composites;
	private int treeIndex;
	private String itemText;
	private List<String> parents;
	private boolean check;
	private boolean checkDetail;
	
	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Tree && event.item instanceof TreeItem && (event.type == SWT.Selection);
	}

	@Override
	public void initializeForEvent(Event event) {
		Widget w = event.widget;
		List<Widget> parentz = new LinkedList<Widget>();
		while (w != null) {
			parentz.add(w);
			w = ((Composite)w).getParent();
		}
		Collections.reverse(parentz);
		this.setComposites(RedDeerUtils.getComposites((Tree)event.widget));
		this.treeIndex = WidgetUtils.getIndex((Tree)event.widget);	
		this.itemText = WidgetUtils.cleanText(((TreeItem)event.item).getText());
		Shell s = WidgetUtils.getShell((Tree)event.widget);
		if(s!=null){
			setShellTitle(s.getText());
		}
		CTabItem v = RedDeerUtils.getView((Tree)event.widget);
		if(v!=null){
			setViewTitle(v.getText());
		}
		//((Tree)event.widget).getItems() check if there are the same items - if are, then use index
		TreeItem parent = ((TreeItem)event.item).getParentItem();
		parents = new ArrayList<String>();
		while (parent != null) {
			parents.add(WidgetUtils.cleanText(parent.getText()));
			parent = parent.getParentItem();
		}
		Collections.reverse(parents);
		if(checkDetail = event.detail == SWT.CHECK){
			check = ((TreeItem)event.item).getChecked();
		}
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder res = new StringBuilder();
		res.append("new DefaultTreeItem(");
		res.append(RedDeerUtils.getReferencedCompositeString(composites));
		if (treeIndex != 0) {
			res.append(treeIndex+",");
		}
		for(String parent: parents){
			res.append("\""+parent+"\",");
		}
		res.append("\""+itemText+"\")");
		if(checkDetail){
			res.append(".setChecked("+check+")");
		} else {
			res.append(".select()");
		}
		toReturn.add(res.toString());
		return toReturn;
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.tree.DefaultTreeItem");
		for(ReferencedComposite r: composites){
			toReturn.add(r.getImport());
		}
		return toReturn;
	}
	
	public int getTreeIndex() {
		return treeIndex;
	}

	public void setTreeIndex(int treeIndex) {
		this.treeIndex = treeIndex;
	}

	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
	}

	public List<String> getParents() {
		return parents;
	}

	public void setParents(List<String> parents) {
		this.parents = parents;
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
