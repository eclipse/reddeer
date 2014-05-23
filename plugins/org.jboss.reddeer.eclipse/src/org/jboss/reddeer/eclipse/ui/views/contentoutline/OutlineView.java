package org.jboss.reddeer.eclipse.ui.views.contentoutline;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.matcher.WithRegexMatcher;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
/**
 * Represents Outline view in Eclipse
 * 
 * @author jjankovi
 *
 */
public class OutlineView extends WorkbenchView {

	/**
	 * Construct the view with "Outline".
	 */
	public OutlineView() {
		super("Outline");
	}

	/**
	 * Returns collection of outline elements.
	 * 
	 * @return Collection of outline elements
	 */
	public Collection<TreeItem> outlineElements() {
		return getTreeForView();
	}

	/**
	 * Clicks on tooltip "Collapse All".
	 */
	public void collapseAll() {
		clickOnToolTip("Collapse All.*");
	}

	/**
	 * Clicks on tooltip "Sort".
	 */
	public void sort() {
		clickOnToolTip("Sort.*");
	}
	
	/**
	 * Clicks on tooltip "Hide Fields".
	 */
	public void hideFields() {
		clickOnToolTip("Hide Fields.*");
	}

	/**
	 * Clicks on tooltip "Hide Static Fields and Methods".
	 */
	public void hideStaticFieldsAndMethods() {
		clickOnToolTip("Hide Static Fields and Methods.*");
	}

	/**
	 * Clicks on tooltip "Hide Non-Public Members".
	 */
	public void hideNonPublicMembers() {
		clickOnToolTip("Hide Non-Public Members.*");
	}

	/**
	 * Clicks on tooltip "Hide Local Types".
	 */
	public void hideLocalTypes() {
		clickOnToolTip("Hide Local Types.*");
	}

	/**
	 * Clicks on tooltip "Link with Editor".
	 */
	public void linkWithEditor() {
		clickOnToolTip("Link with Editor.*");
	}
	
	private Collection<TreeItem>getTreeForView() {
		try {
			DefaultTree tree = new DefaultTree();
			return tree.getItems();
		} catch (SWTLayerException exc) {
			return new ArrayList<TreeItem>();
		}
		
	}
	
	private void clickOnToolTip(String regex) {
		WithRegexMatcher rm = new WithRegexMatcher(regex);
		new DefaultToolItem(rm).click();
	}
	
}
