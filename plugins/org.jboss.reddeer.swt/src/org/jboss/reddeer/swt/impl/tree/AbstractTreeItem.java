package org.jboss.reddeer.swt.impl.tree;

import java.util.List;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.SWT;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.TreeItemHasMinChildren;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.TreeHandler;
import org.jboss.reddeer.swt.handler.TreeItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Basic TreeItem class is abstract class for all Tree Item implementations
 * 
 * @author jjankovi, mlabuda@redhat.com
 * 
 */
public abstract class AbstractTreeItem implements TreeItem {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected org.eclipse.swt.widgets.TreeItem swtTreeItem;

	private TreeHandler treeHandler = TreeHandler.getInstance();
	
	protected AbstractTreeItem(org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		if (swtTreeItem != null) {
			this.swtTreeItem = swtTreeItem;
		} else {
			throw new SWTLayerException(
					"SWT Tree Item passed to constructor is null");
		}

	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void select() {
		treeHandler.select(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtTreeItem);
	}

	private TreeItemTexts parseText() {
		return Display.syncExec(new ResultRunnable<AbstractTreeItem.TreeItemTexts>() {

			@Override
			public AbstractTreeItem.TreeItemTexts run() {
				String nonStyledText = null;
				String[] styledTexts = null;
				StyleRange[] styleRanges = getStyleRanges();
				
				if (styleRanges == null) {
					// Everything is ok, there is no styled texts
					nonStyledText = swtTreeItem.getText().trim();
					styledTexts = null;
				} else {
					// Here it goes. There are some styled texts
					String rawText = swtTreeItem.getText();
					String[] tmpStyledTexts = new String[styleRanges.length];
					String tmpNonStyledText = null;
					int currentTextIndex = 0;
					int i = 0;
					
					for (StyleRange range: getStyleRanges()) {
						// At some point there is a non-styled text
						if (range.start > currentTextIndex) {
							tmpNonStyledText = rawText.substring(currentTextIndex, 
									range.start).trim();
						}
						
						tmpStyledTexts[i] = rawText.substring(range.start, range.start +
								range.length).trim();
						currentTextIndex = range.start + range.length + 1;
						i++;
					}
					
					if (tmpNonStyledText == null) {
						tmpNonStyledText = rawText.substring(currentTextIndex).trim();
					}
					
					nonStyledText = tmpNonStyledText;
					styledTexts = tmpStyledTexts;
				}
				
				return new TreeItemTexts(nonStyledText, styledTexts);
			}
		});
	}

	// Should be run inside Display.syncExec method - in parseText method
	// it is also expected, that styled in array are sequential
	private StyleRange[] getStyleRanges() {
		Object data = swtTreeItem.getData("org.eclipse.jfacestyled_label_key_0");

		if (data == null) {
			return null;
		}

		if (!(data instanceof StyleRange[])) {
			throw new SWTLayerException(
					"Cannot parse tree item label. Data for key "
					+ "'org.eclipse.jfacestyled_label_key_0' are "
					+ "expected to be of type "	+ StyleRange[].class
					+ " but are " + data.getClass());
		}

		StyleRange[] styles = (StyleRange[]) data;
		
		if (styles.length == 0) {
			return null;
		}

		return styles;
	}

	@Override
	public String getNonStyledText() {
		return parseText().getNonStyledText();
	}

	@Override
	public String[] getStyledTexts() {
		return parseText().getStyledTexts();
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public String getToolTipText() {
		return TreeItemHandler.getInstance().getToolTipText(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public String getCell(final int index) {
		return TreeItemHandler.getInstance().getText(swtTreeItem, index);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public String[] getPath() {
		return treeHandler.getPath(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void expand() {
		expand(TimePeriod.SHORT);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void expand(TimePeriod timePeriod) {
		treeHandler.expand(swtTreeItem, timePeriod);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void collapse() {
		treeHandler.collapse(swtTreeItem);
	}

	/**
	 * Return direct descendant specified with parameters
	 * 
	 * @param text
	 *            text of tree item which should be returned
	 * @return direct descendant specified with parameters
	 */
	public TreeItem getItem(final String text) {
		return treeHandler.getItem(swtTreeItem, text);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void doubleClick() {
		logger.debug("Double Click Tree Item " + getText());
		select();
		logger.debug("Notify tree about mouse double click event");
		treeHandler.notifyTree(getSWTWidget(), treeHandler.createEventForTree(
				getSWTWidget(), SWT.MouseDoubleClick));
		logger.debug("Notify tree about default selection event");
		treeHandler.notifyTree(getSWTWidget(), treeHandler.createEventForTree(
				getSWTWidget(), SWT.DefaultSelection));
		logger.info("Double Clicked on: " + this);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public boolean isSelected() {
		return treeHandler.isSelected(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public boolean isDisposed() {
		return swtTreeItem.isDisposed();
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void setChecked(final boolean check) {
		treeHandler.setChecked(swtTreeItem, check);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public boolean isChecked() {
		return treeHandler.isChecked(swtTreeItem);
	}

	/**
	 * Return swt widget of Tree Item
	 */
	public org.eclipse.swt.widgets.TreeItem getSWTWidget() {
		return swtTreeItem;
	}

	/**
	 * Returns children tree items
	 * 
	 * @return
	 */
	@Override
	public List<TreeItem> getItems() {
		expand(TimePeriod.SHORT);
		return treeHandler.getChildrenItems(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public Tree getParent() {
		return treeHandler.getParent(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public boolean isExpanded() {
		return treeHandler.isExpanded(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void expand(int minItemsCount) {
		expand(minItemsCount, TimePeriod.SHORT);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void expand(int minItemsCount, TimePeriod timePeriod) {
		expand();
		new WaitUntil(new TreeItemHasMinChildren(this, minItemsCount),
				timePeriod);
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("TreeItem: ");
		boolean isFirst = true;
		for (String pathItem : this.getPath()) {
			if (isFirst) {
				isFirst = false;
			} else {
				result.append(" > ");
			}
			result.append(pathItem);

		}
		return result.toString();
	}

	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtTreeItem);
	}

	@Override
	public void setText(String text, int index) {
		TreeItemHandler.getInstance().setText(swtTreeItem, index, text);
	}

	@Override
	public void setText(String text) {
		TreeItemHandler.getInstance().setText(swtTreeItem, 0, text);
	}
	
	class TreeItemTexts {
		private String nonStyledText;
		private String[] styledTexts;
		
		public TreeItemTexts(String nonStyledText, String[] styledTexts) {
			this.nonStyledText = nonStyledText;
			this.styledTexts = styledTexts;
		}
		
		public String getNonStyledText() {
			return nonStyledText;
		}
		
		public String[] getStyledTexts() {
			return styledTexts;
		}
	}
}