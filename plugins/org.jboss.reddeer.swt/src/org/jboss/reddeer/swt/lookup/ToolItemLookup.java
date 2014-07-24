package org.jboss.reddeer.swt.lookup;

import java.util.List;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Class providing methods for searching for tool items.
 * 
 * @author rhopp
 *
 */

public class ToolItemLookup {

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
		return WidgetLookup.getInstance().activeWidget(rc, ToolItem.class, 0,
				matchers);
	}

	public ToolItem getWorkbenchToolItem(Matcher<String> matcher) {
		ToolBarLookup tl = ToolBarLookup.getInstance();
		List<ToolBar> workbenchToolBars = tl.getWorkbenchToolBars();
		final ToolItem ti = getToolItem(new WorkbenchShell(), 0, matcher);
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

}
