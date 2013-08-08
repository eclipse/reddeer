package org.jboss.reddeer.swt.handler;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.swt.api.ExpandBar;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.swt.internal.BasicExpandBarItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Implements ExpandBar UI methods
 * @author Vlado Pakan
 *
 */
public class ExpandBarHandler {
	/**
	 * See {@link ExpandBar}
	 */
	public static void expandAll(final ExpandBar expandBar) {
		for (ExpandBarItem expandBarItem : expandBar.getItems()){
			expandBarItem.expand();
		}
		
	}
	/**
	 * See {@link ExpandBar}
	 */
	public static void collapseAll(final ExpandBar expandBar) {
		for (ExpandBarItem expandBarItem : expandBar.getItems()){
			expandBarItem.collapse();
		}
	}
	/**
	 * See {@link ExpandBar}
	 */
	public static List<ExpandBarItem> getItems(final ExpandBar expandBar) {
		return Display.syncExec(new ResultRunnable<List<ExpandBarItem>>() {
		      @Override
		      public List<ExpandBarItem> run() {
		        LinkedList<ExpandBarItem> result = new LinkedList<ExpandBarItem>();
		        for (org.eclipse.swt.widgets.ExpandItem swtExpandItem : expandBar.getSWTWidget().getItems()){
		          result.addFirst(new BasicExpandBarItem(swtExpandItem));
		        }
		        return result;
		      }
		    });
	}
	/**
	 * See {@link ExpandBar}
	 */
	public static int getItemsCount(final ExpandBar expandBar) {
		return ExpandBarHandler.getItems(expandBar).size();
	}
}
