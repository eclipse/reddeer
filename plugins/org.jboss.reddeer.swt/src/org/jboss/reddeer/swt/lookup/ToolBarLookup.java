package org.jboss.reddeer.swt.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.CoolBarToTrimManager;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
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

	private static ToolBarLookup instance;

	/**
	 * Returns instance of this {@link ToolBarLookup}.
	 * 
	 * @return instance
	 */

	public static ToolBarLookup getInstance() {
		if (instance == null) {
			instance = new ToolBarLookup();
		}
		return instance;
	}

	/**
	 * Returns ToolBar of currently active View. If no view is active,
	 * {@link SWTLayerException} is thrown.
	 * 
	 * @return found ToolBar.
	 * @throws SWTLayerException
	 */

	public ToolBar getViewToolBar() {
		ToolBar toolbar = Display.syncExec(new ResultRunnable<ToolBar>() {
			@Override
			public ToolBar run() {
				ToolBar toolBar = null;
				IWorkbenchPartSite site = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite();
				if (!(site instanceof IViewSite)) {
					throw new SWTLayerException("Active part is not View.");
				}
				IViewSite viewSite = (IViewSite) site;
				IToolBarManager toolBarManager = viewSite.getActionBars()
						.getToolBarManager();
				if (toolBarManager instanceof ToolBarManager) {
					toolBar = ((ToolBarManager) toolBarManager).getControl();
				}
				return toolBar;
			}
		});
		return toolbar;
	}

	/**
	 * Searches for first toolbar within currently active control (shell, view,
	 * ...).
	 * 
	 * @return first toolbar within currently active control (shell, view, ...).
	 */

	public ToolBar getToolBar() {
		return getToolBar(null, 0);
	}

	/**
	 * Searches for nth toolbar within given {@link ReferencedComposite}.
	 * 
	 * @param rc
	 *            {@link ReferencedComposite} to look into for ToolBar.
	 * @param index
	 *            index of found ToolBar.
	 * @return desired ToolBar.
	 */
	public ToolBar getToolBar(ReferencedComposite rc, int index) {
		return WidgetLookup.getInstance()
				.activeWidget(rc, ToolBar.class, index);
	}

	/**
	 * Searches for nth ToolBar within WorkbenchToolBars.
	 * 
	 * @param index
	 *            index of desired ToolBar.
	 * @return ToolBar.
	 * @throws SWTLayerException
	 *             when there is no ToolBar with given index in workbench tool
	 *             bars.
	 */
	public ToolBar getWorkbenchToolBar(int index) {
		try {
			return getWorkbenchToolBars().get(index);
		} catch (ArrayIndexOutOfBoundsException cause) {
			throw new SWTLayerException(
					"There is no workbench toolbar with index " + index, cause);
		}
	}

	/**
	 * Returns active workbench toolbars, if null, SWTLayerException is thrown.
	 * Note that this method uses Eclipse internal API thay may change soon
	 * 
	 * @return active workbench toolbars
	 */
	public List<ToolBar> getWorkbenchToolBars() {

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
		return Arrays.asList(toolbars);
	}

	/**
	 * Searches for all ToolBars within given {@link ReferencedComposite}.
	 * 
	 * @param rc {@link ReferencedComposite} to look into.
	 * @return array of found ToolBars.
	 */
	
	public List<ToolBar> getToolbars(ReferencedComposite rc) {
		List<ToolBar> list = new ArrayList<ToolBar>();
		boolean found = true;
		int index = 0;
		do {
			try {
				list.add(WidgetLookup.getInstance().activeWidget(rc,
						ToolBar.class, index));
				index++;
			} catch (SWTLayerException ex) {
				found = false;
			}
		} while (found);
		return list;
	}
}
