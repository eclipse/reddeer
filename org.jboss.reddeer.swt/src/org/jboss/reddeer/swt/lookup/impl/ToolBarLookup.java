package org.jboss.reddeer.swt.lookup.impl;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Lookup routines for Toolbar implementations mainly running in UI Thread
 * @author Jiri Peterka
 * 
 */
public class ToolBarLookup {

	/**
	 * Returns active view toolbar, if null, SWTLayerException is thrown
	 * @return active workbench toolbar
	 */
	public ToolBar getViewToolbar() {
		
		ToolBar toolbar = Display.syncExec(new ResultRunnable<ToolBar>() {
	
			@Override
			public ToolBar run() {
				ToolBar toolBar = null;
				
				IViewSite site = (IViewSite)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getSite();
				IToolBarManager toolBarManager = site.getActionBars().getToolBarManager();
				if (toolBarManager instanceof ToolBarManager) {
					toolBar = ((ToolBarManager)toolBarManager).getControl();
				}
				
				return toolBar;
				
				
			}			
				
		});
		if (toolbar == null) throw new SWTLayerException("Active workbench toolbar is null");
		return toolbar;
	}
	
	public ToolItem getToolItem(final ToolBar toolBar, final String text) {
		
		ToolItem item = Display.syncExec(new ResultRunnable<ToolItem>() {
			
			@Override
			public ToolItem run() {
		
			ToolItem[] items = toolBar.getItems();
			for (ToolItem item : items) {
				if (item.getToolTipText().equals(text)) {
					
					return item;
				}
			}
			return null;
			}});
			
			if (item == null ) {
				throw new SWTLayerException("ToolItem with text cannot be found");	
			}			
			return item;
	}	
}
