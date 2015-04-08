package org.jboss.reddeer.core.lookup;

import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.core.exception.Thrower;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.DefaultReferencedComposite;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.lookup.ToolBarLookup;

/**
 * Class providing methods for searching for tool items.
 * 
 * @author rhopp
 *
 */
public class ToolItemLookup {
	
	private static final Logger logger = Logger.getLogger(ToolItemLookup.class);

	private static ToolItemLookup instance = new ToolItemLookup();

	/**
	 * Returns instance of this {@link ToolItemLookup}.
	 * 
	 * @return instance.
	 */

	public static ToolItemLookup getInstance() {
		return instance;
	}

	/**
	 * Searches for nth ToolItem within given {@link ReferencedComposite} and
	 * matching {@code matchers}.
	 * 
	 * @param rc
	 *            {@link ReferencedComposite} in which serching is performed.
	 * @param index
	 *            index of found ToolItem.
	 * @param matchers
	 *            matchers to match against tool items.
	 * @return found ToolItem.
	 */

	public ToolItem getToolItem(ReferencedComposite rc, int index,
			Matcher<?>... matchers) {
		
		if (rc == null){
			rc = findReferencedComposite();
		}
		return WidgetLookup.getInstance().activeWidget(rc, ToolItem.class, index,
				matchers);
	}

	public ToolItem getWorkbenchToolItem(Matcher<String> matcher) {
		ToolBarLookup tl = ToolBarLookup.getInstance();
		List<ToolBar> workbenchToolBars = tl.getWorkbenchToolBars();
		
		Shell swtShell = ShellLookup.getInstance().getWorkbenchShell();
		String text = WidgetHandler.getInstance().getText(swtShell);
		WidgetHandler.getInstance().setFocus(swtShell);
		new WaitUntil(new ShellWithTextIsActive(text));
		
		final ToolItem ti = getToolItem(new DefaultReferencedComposite((Control) swtShell), 0, matcher);
		
		ToolBar tb = Display.syncExec(new ResultRunnable<ToolBar>() {
			@Override
			public ToolBar run() {
				return ti.getParent();
			}
		});
		if (workbenchToolBars.contains(tb)) {
			return ti;
		} else {
			Thrower.objectIsNull(ti, "ToolItem matching " + matcher.toString()
					+ " cannot be found in any workbench toolbar");
		}
		return null;
	}
	
	protected ReferencedComposite findReferencedComposite(){
		Control control = null;
		Control activeWidgetParentControl = WidgetLookup.getInstance().getActiveWidgetParentControl();
		if (activeWidgetParentControl instanceof Shell){
			control = activeWidgetParentControl;
		}else{
			control = getWorkbenchControl(activeWidgetParentControl);
		}	
		return new GenericReferencedComposite(control);
	}

	private Control getWorkbenchControl(final Control activeWorkbenchPartReference) {
		Control control = Display.syncExec(new ResultRunnable<Control>() {

			@Override
			public Control run() {
				Control control = activeWorkbenchPartReference;
				while (!((control instanceof CTabFolder) || (control instanceof Shell))) {
										control = control.getParent();
									}
				return control;
			}
		});
		return control;
	}
	
	protected class GenericReferencedComposite implements ReferencedComposite{
		
		private Control control;
		
		public GenericReferencedComposite(Control control) {
			this.control = control;
		}
		
		@Override
		public Control getControl() {
			return control;
		}
		
	}
	
}
