package org.jboss.reddeer.eclipse.ui.views.contentoutline;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
import org.jboss.reddeer.swt.impl.tree.ViewTree;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

/**
 * Represents Outline view in Eclipse
 * 
 * @author jjankovi
 *
 */
public class OutlineView extends WorkbenchView {

	public OutlineView() {
		super("Outline");
	}

	public Collection<TreeItem> outlineElements() {
		return getTreeForView();
	}

	public void collapseAll() {
		clickOnToolTip("Collapse All.*");
	}
	
	public void sort() {
		clickOnToolTip("Sort.*");
	}
	
	public void hideFields() {
		clickOnToolTip("Hide Fields.*");
	}
	
	public void hideStaticFieldsAndMethods() {
		clickOnToolTip("Hide Static Fields and Methods.*");
	}
	
	public void hideNonPublicMembers() {
		clickOnToolTip("Hide Non-Public Members.*");
	}
	
	public void hideLocalTypes() {
		clickOnToolTip("Hide Local Types.*");
	}
	
	public void linkWithEditor() {
		clickOnToolTip("Link with Editor.*");
	}
	
	private Collection<TreeItem>getTreeForView() {
		try {
			ViewTree tree = new ViewTree();
			return tree.getItems();
		} catch (SWTLayerException exc) {
			return new ArrayList<TreeItem>();
		}
		
	}
	
	private void clickOnToolTip(String regex) {
		RegexMatcher rm = new RegexMatcher(regex);
		new ViewToolItem(rm).click();
	}
	
}
