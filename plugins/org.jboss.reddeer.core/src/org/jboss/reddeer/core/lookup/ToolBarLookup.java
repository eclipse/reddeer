package org.jboss.reddeer.core.lookup;

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
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.exception.Thrower;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.core.lookup.WidgetLookup;

/**
 * Tool bar lookup provides methods for looking up various tool bars. 
 * Methods should be executed in UI Thread.
 * 
 * @author Jiri Peterka
 * 
 */
@SuppressWarnings("restriction")
public class ToolBarLookup {

	private static ToolBarLookup instance;

	private ToolBarLookup() { }
	
	/**
	 * Gets instance of ToolBarLookup.
	 * 
	 * @return ToolBarLookup instance
	 */
	public static ToolBarLookup getInstance() {
		if (instance == null) {
			instance = new ToolBarLookup();
		}
		return instance;
	}

	/**
	 * Gets ToolBar of currently active View.
	 * 
	 * @return tool bar of active view
	 * @throws CoreLayerException if there is no active view
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
					throw new CoreLayerException("Active part is not View.");
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
	 * Gets first tool bar of currently active referenced composite (shell, view, etc.).
	 * 
	 * @return first tool bar of currently active referenced composite
	 */
	public ToolBar getToolBar() {
		return getToolBar(null, 0);
	}

	/**
	 * Gets tool bar with specified index within specified referenced composite.
	 * 
	 * @param rc referenced composite where to look for tool bar
	 * @param index index of tool bar
	 * @return tool bar within specified referenced composite with with specified index
	 */
	public ToolBar getToolBar(ReferencedComposite rc, int index) {
		return WidgetLookup.getInstance()
				.activeWidget(rc, ToolBar.class, index);
	}

	/**
	 * Gets workbench tool bar with specified index.
	 * 
	 * @param index index of tool bar
	 * @return workbench tool bar with specified index
	 * @throws CoreLayerException if there is no workbench tool bar with specified index
	 */
	public ToolBar getWorkbenchToolBar(int index) {
		try {
			return getWorkbenchToolBars().get(index);
		} catch (ArrayIndexOutOfBoundsException cause) {
			throw new CoreLayerException(
					"There is no workbench toolbar with index " + index, cause);
		}
	}

	/**
	 * Gets active workbench tool bars.
	 * Note: this method uses Eclipse internal API that may change soon
	 * 
	 * @return active workbench tool bars
	 * @throws CoreLayerException if there is no active workbench tool bar
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
			throw new CoreLayerException("No Workbench Toolbars were found");
		return Arrays.asList(toolbars);
	}

	/**
	 * Gets all tool bars located within specified referenced composite.
	 * 
	 * @param rc referenced composite to search for tool bar
	 * @return list of tool bars contained in specified referenced composite, or empty list
	 * if there is no tool bar in specified referenced composite at all
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
			} catch (CoreLayerException ex) {
				found = false;
			}
		} while (found);
		return list;
	}
}
