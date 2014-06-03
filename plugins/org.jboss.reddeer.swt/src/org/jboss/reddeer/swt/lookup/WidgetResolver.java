package org.jboss.reddeer.swt.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.soap.Text;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.swt.exception.SWTLayerException;

/**
 * Widget resolver resolves children and parent of supported types
 * Note: Must be used in UI Thread
 * <ul>
 * <li>ExpandBar</li>
 * <li>ExpandItem</li>
 * <li>CTabFolder</li>
 * <li>TabFolder</li>
 * <li>CTabItem</li>
 * <li>TabItem</li>
 * <li>ToolBar</li>
 * <li>Composite</li>
 * <li>Control</li>
 * </ul>
 * @author Jiri Peterka
 *
 */
public class WidgetResolver  {
	
	private static WidgetResolver instance = null;
	private WidgetResolver() {}
	
	/**
	 * Return WidgetResolver instance
	 * @return idgetResolver instance
	 */
	public static WidgetResolver getInstance() {
		if (instance == null) instance = new WidgetResolver();
		return instance;
	}
	
	private static final Class<?>[] supported = {
		ExpandBar.class,
		ExpandItem.class,
		CTabFolder.class,
		TabFolder.class,
		CTabItem.class,
		TabItem.class,
		ToolBar.class,
		Composite.class,
		Control.class,
		Text.class
	};
	
	private TabItem[] items;

	/**
	 * Returns parent of resolvable specific widget type, if not resolvable returns null
	 * Must be called from UI Thread
	 * @param w given widget
	 * @return parent widget or null
	 */
	public Widget getParent(Widget w) {
		if (isResolvable(w)) {
			if (w instanceof ExpandBar) {
				return ((ExpandBar) w).getParent();
			}
			if (w instanceof ExpandItem) {
				return ((ExpandItem) w).getParent();
			}
			else if (w instanceof CTabFolder) {
				return ((CTabFolder) w).getParent();
			}
			else if (w instanceof TabFolder) {
				return ((TabFolder) w).getParent();
			}
			else if (w instanceof CTabItem) {
				return ((CTabItem) w).getParent();
			}
			else if (w instanceof TabItem) {
				return ((TabItem) w).getParent();
			}
			else if (w instanceof ToolBar) {
				return ((ToolBar) w).getParent();
			}
			else if (w instanceof Composite) {
				// Control
				Composite parent = w instanceof Control ? ((Control) w).getParent() : null;
				
				// If composite under TabFolder find widget under TabFolder siblings
				if ((w instanceof Composite) && (parent instanceof TabFolder)) {
						
					if ((parent == null) || parent.isDisposed()) {
						throw new SWTLayerException("TabFolder is null or disposed while resolving");
					}
					TabItem[] tabItems = ((TabFolder) parent).getItems();

					int index = -1;
					index = Arrays.asList(tabItems).indexOf(w);
					
					if (index == -1) throw new SWTLayerException("Widget not found under TabFolder");
					
					return items[index];
				}
				return parent;
			}
			else if (w instanceof Control) {
				return ((Control) w).getParent();
			}
		}
		return null;
	}

	
	/**
	 * Return children
	 * @param w given widget
	 * @return list of children widgets
	 */
	public List<Widget> getChildren(Widget w) {
		List<Widget> children = new ArrayList<Widget>();
		
		if (w instanceof ExpandBar) {
			Widget[] items = ((ExpandBar) w).getItems();
			children = Arrays.asList(items);
			
		} else if (w instanceof ExpandItem) {
			Control control = ((ExpandItem) w).getControl();
			if (control != null) children.add(control);
			
		} else if (w instanceof CTabFolder) {
			Widget[] items = ((CTabFolder) w).getItems();
			children = Arrays.asList(items);

		} else if (w instanceof TabFolder) {
			Widget[] items = ((TabFolder) w).getItems();
			children = Arrays.asList(items);
		}
		else if (w instanceof CTabItem) {
			Control control = ((CTabItem) w).getControl();
			if (control != null) children.add(control);
		}
		else if (w instanceof TabItem) {
			Control control = ((TabItem) w).getControl();
			if (control != null) children.add(control);
		}
		else if (w instanceof ToolBar) {
			Widget[] items = ((ToolBar) w).getItems();
			children = Arrays.asList(items);
		}
		else if (w instanceof Composite)  {
			Widget[] items = ((Composite) w).getChildren();
			children = Arrays.asList(items);
		}
		else if (w instanceof Control) {
			// do nothing
		}
		else {
			//throw new SWTLayerException("Unsupported type for resolution");
			// do nothing
		}
		
		return children;
	}

	/**
	 * Returns true if widget has a parent
	 * @param w given widget
	 * @return widget's parent
	 */
	public boolean hasParent(Widget w) {
		return getParent(w) != null;
	}

	
	/**
	 * Returns true if suported widget has a children
	 * @param w widget
	 * @return true if widget has a parent
	 */
	public boolean hasChildren(Widget w) {
		return isResolvable(w) && (getChildren(w).size() > 0) ;
	}

	
	/**
	 * returns true if Widget is resolvable by resolver
	 * @param w widget
	 * @return true if widget type is resolvable
	 */
	public boolean isResolvable(Widget w) {
		// DateTime is not supported because of eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=206868
		if (w instanceof DateTime) return false;
		return Arrays.asList(supported).contains(w.getClass());
	}
}
