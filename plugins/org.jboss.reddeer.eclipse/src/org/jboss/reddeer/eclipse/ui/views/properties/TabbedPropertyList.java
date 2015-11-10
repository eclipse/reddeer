package org.jboss.reddeer.eclipse.ui.views.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.matcher.WithClassNameMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Represents tabbed property list used in Properties View.
 * 
 * @author Andrej Podhradsky
 * @author Jozef Marko
 *
 */
public class TabbedPropertyList extends AbstractWidget<org.eclipse.swt.widgets.Composite> {

	/**
	 * Finds tabbed property list. The widget is of type {@link org.eclipse.widgets.Composite}.
	 */
	public TabbedPropertyList() {
		this(null, 0);
	}

	/**
	 * Finds tabbed property list in the specified referenced composite and at the given index. The widget is of type
	 * {@link org.eclipse.widgets.Composite}.
	 * 
	 * @param ref
	 *            Referenced Composite
	 * @param index
	 *            Index
	 */
	public TabbedPropertyList(ReferencedComposite ref, int index) {
		super(org.eclipse.swt.widgets.Composite.class, ref, index, new WithClassNameMatcher("TabbedPropertyList"));
	}

	/**
	 * Returns list of tab labels except bottom and top elements.
	 * 
	 * @return List of tab labels except bottom and top elements
	 */
	public List<String> getTabs() {
		List<String> elements = new ArrayList<String>();
		for (Control control : getChildren()) {
			if (control != null && control.getClass().getSimpleName().equals("ListElement")) {
				elements.add(control.toString());
			}
		}
		return elements;
	}

	/**
	 * Selects a tab with the specified label.
	 * 
	 * @param label
	 *            Label
	 */
	public void selectTab(final String label) {
		for (Control control : getChildren()) {
			if (label.equals(control.toString())) {
				WidgetHandler.getInstance().notify(SWT.MouseDown, control);
				WidgetHandler.getInstance().notify(SWT.MouseUp, control);
				return;
			}
		}
		throw new SWTLayerException("Cannot find tab with label '" + label + "'");
	}

	/**
	 * Returns the list of all elements including bottom and top elements.
	 * 
	 * @return List of all elements including bottom and top elements
	 */
	protected List<Control> getChildren() {
		return Display.syncExec(new ResultRunnable<List<Control>>() {

			@Override
			public List<Control> run() {
				List<Control> elements = new ArrayList<Control>();
				for (Control control : swtWidget.getChildren()) {
					elements.add(control);
				}
				return elements;
			}
		});
	}

}
