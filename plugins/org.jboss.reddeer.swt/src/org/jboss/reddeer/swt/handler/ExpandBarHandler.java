package org.jboss.reddeer.swt.handler;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.swt.api.ExpandBar;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.swt.impl.expandbar.internal.BasicExpandBarItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link org.eclipse.swt.widgets.ExpandBar} widgets. 
 * 
 * @author Vlado Pakan
 *
 */
public class ExpandBarHandler {
	
	private static ExpandBarHandler instance;

	private ExpandBarHandler() {

	}

	/**
	 * Creates and returns instance of ComboHandler class
	 * 
	 * @return
	 */
	public static ExpandBarHandler getInstance() {
		if (instance == null) {
			instance = new ExpandBarHandler();
		}
		return instance;
	}
	
	/**
	 * See {@link ExpandBar}
	 * @deprecated This is not UI handling operation, it should be moved to the widget's impl
	 */
	public static void expandAll(final ExpandBar expandBar) {
		for (ExpandBarItem expandBarItem : expandBar.getItems()){
			expandBarItem.expand();
		}
		
	}
	/**
	 * See {@link ExpandBar}
	 * @deprecated This is not UI handling operation, it should be moved to the widget's impl
	 */
	public static void collapseAll(final ExpandBar expandBar) {
		for (ExpandBarItem expandBarItem : expandBar.getItems()){
			expandBarItem.collapse();
		}
	}
	/**
	 * See {@link ExpandBar}
	 * @deprecated Method should use SWT widget as its argument and should not be static
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
	public List<ExpandBarItem> getItems(final org.eclipse.swt.widgets.ExpandBar expandBar) {
		return Display.syncExec(new ResultRunnable<List<ExpandBarItem>>() {
		      @Override
		      public List<ExpandBarItem> run() {
		        LinkedList<ExpandBarItem> result = new LinkedList<ExpandBarItem>();
		        for (org.eclipse.swt.widgets.ExpandItem swtExpandItem : expandBar.getItems()){
		          result.addFirst(new BasicExpandBarItem(swtExpandItem));
		        }
		        return result;
		      }
		    });
	}
	/**
	 * See {@link ExpandBar}
	 * @deprecated This is not UI handling operation, it should be moved to the widget's impl
	 */
	public static int getItemsCount(final ExpandBar expandBar) {
		return ExpandBarHandler.getItems(expandBar).size();
	}
}
