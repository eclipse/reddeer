package org.jboss.reddeer.swt.lookup;

//import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.CoolBarToTrimManager;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Lookup routines for Toolbar implementations mainly running in UI Thread
 * 
 * @author Jiri Peterka
 * 
 */
@SuppressWarnings("restriction")
public class ToolBarLookup {

	/**
	 * Returns active view toolbar, if null, SWTLayerException is thrown
	 * 
	 * @return active workbench toolbar
	 */
	public ToolBar getViewToolbar() {

		ToolBar toolbar = Display.syncExec(new ResultRunnable<ToolBar>() {

			@Override
			public ToolBar run() {
				ToolBar toolBar = null;

				IViewSite site = (IViewSite) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite();
				IToolBarManager toolBarManager = site.getActionBars()
						.getToolBarManager();
				if (toolBarManager instanceof ToolBarManager) {
					toolBar = ((ToolBarManager) toolBarManager).getControl();
				}

				return toolBar;

			}

		});
		if (toolbar == null)
			throw new SWTLayerException("Active workbench toolbar is null");
		return toolbar;
	}
	
	/**
	 * Returns active shell toolbar
	 * 
	 * @return active shell toolbar
	 */
	public ToolBar getShellToolBars() {
		ToolBar toolbar = Display.syncExec(new ResultRunnable<ToolBar>() {
			@Override
			public ToolBar run() {
				ToolBar toolbar = (ToolBar)WidgetLookup.getInstance().activeWidget(ToolBar.class, 0);
				
				return toolbar;
			}
		});
		return toolbar;
	}

	/**
	 * Returns active workbench toolbars, if null, SWTLayerException is thrown.
	 * Note that this method uses Eclipse internal API thay may change soon
	 * 
	 * @return active workbench toolbars
	 */
	public ToolBar[] getWorkbenchToolBars() {

		ToolBar[] toolbars = Display.syncExec(new ResultRunnable<ToolBar[]>() {

			@Override
			public ToolBar[] run() {
				List<ToolBar> toolBars = new ArrayList<ToolBar>();

				IWorkbenchWindow aww = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				Thrower.typeIsWrong(aww, WorkbenchWindow.class);
				WorkbenchWindow wbw = (WorkbenchWindow) aww;

				ICoolBarManager icbm2 = wbw.getCoolBarManager2();
				Thrower.typeIsWrong(icbm2, CoolBarToTrimManager.class);
				CoolBarToTrimManager cbttm = (CoolBarToTrimManager) wbw
						.getCoolBarManager2();

				IContributionItem[] items = cbttm.getItems();
				Thrower.objectIsNull(items, "Contribution item array is null");
				for (IContributionItem i : items) {
					if (i instanceof ToolBarContributionItem) {
						ToolBarContributionItem tbi = (ToolBarContributionItem) i;

						IToolBarManager itbm = tbi.getToolBarManager();
						if (itbm instanceof ToolBarManager) {
							ToolBarManager tbm = (ToolBarManager) itbm;
							ToolBar tb = tbm.getControl();
							
							if (tb == null)
								continue;
							toolBars.add(tb);
						}
					}
				}
				ToolBar[] ret = new ToolBar[toolBars.size()];
				ret = toolBars.toArray(ret);
				return ret;
			}

		});
		if (toolbars.length == 0)
			throw new SWTLayerException("No Workbench Toolbars were found");
		return toolbars;
	}

	/**
	 * Returns ToolItem from given toolbar with given matcher
	 * @param toolBar
	 * @param text
	 * @return
	 */
	public ToolItem getToolItem(final ToolBar toolBar, final Matcher<String> matcher) {
		return getToolItem(toolBar, matcher, 0);
	}
	
	/**
	 * Returns ToolItem from given toolbar with given matcher and index
	 * @param toolBar
	 * @param text
	 * @return
	 */
	public ToolItem getToolItem(final ToolBar toolBar, final Matcher<String> matcher, final int index) {

		ToolItem item = Display.syncExec(new ResultRunnable<ToolItem>() {

			@Override
			public ToolItem run() {
				int counter = 0;
				ToolItem[] items = toolBar.getItems();
				for (ToolItem item : items) {
					if (matcher != null) {
						if ((item == null) || (item.getToolTipText() == null)) continue;
						if (matcher.matches(item.getToolTipText())) {
							if (counter == index) {
								return item;
							}
							counter++;
						}
					}else {
						if (counter == index) {
							return item;
						}
						counter++;
					}
				}
				return null;
			}
		});

		return item;
	}
}
