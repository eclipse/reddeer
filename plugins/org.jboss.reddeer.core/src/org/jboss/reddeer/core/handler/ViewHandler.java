package org.jboss.reddeer.core.handler;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ActiveFocusControlIsInActiveView;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.lookup.WorkbenchPartLookup;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ObjectUtil;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * View handler handles operations for view instances.
 * 
 * @author rawagner
 */
public class ViewHandler {
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	private static ViewHandler instance;
	
	private ViewHandler(){
		
	}
	
	/**
	 * Gets instance of ViewHandler.
	 * 
	 * @return instance of ViewHandler
	 */
	public static ViewHandler getInstance(){
		if(instance == null){
			instance = new ViewHandler();
		}
		return instance;
	}
	
	/**
	 * Sets focus for specified {@link IViewPart}.
	 * @deprecated This method does not work properly due to https://bugs.eclipse.org/bugs/show_bug.cgi?id=468948
	 * @param viewPart view part to set focus on
	 */
	public void setFocus(final IViewPart viewPart) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				log.debug("Setting focus to workbench part with title=" + viewPart.getTitle());
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().activate(viewPart);
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().showView(viewPart.getViewSite().getId());
				} catch (PartInitException pie) {
					throw new CoreLayerException("Unable to show view " + viewPart.getTitle(),
						pie);
				}
				
				viewPart.setFocus();
			}
		});
		new WaitUntil(new ActiveFocusControlIsInActiveView(),TimePeriod.NORMAL,false);
		focusChildControl();
	}
	
	/**
	 * Finds out whether the specified {@link IViewPart} is focused or not.
	 * 
	 * @param viewPart view part to find out its focus
	 * @return true if view is focused, false otherwise
	 */
	public boolean hasFocus(final IViewPart viewPart) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				IWorkbenchPart activeWorkbenchPart = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().getActivePart();
				return activeWorkbenchPart == null ? false : activeWorkbenchPart.equals(viewPart);
			}
		});
	}
	
	/**
	 * Closes view specified by view part.
	 * 
	 * @param viewPart view part to close
	 */	
	public void close(final IViewPart viewPart){
		log.debug("Hiding view " + viewPart.getTitle());
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().hideView(viewPart);
			}
		});
	}
	
	/**
	 * Finds out whether specified {@link IViewPart} is visible on active workbench window or not.
	 * 
	 * @param viewPart view part to handle
	 * @return true if specified view part is visible on active workbench window, false otherwise
	 */
	public boolean isViewVisible(final IViewPart viewPart){
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return viewPart != null 
						&& PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().isPartVisible(viewPart);
			}
		});
				
	}

	public void focusChildControl() {
		final Control workbenchControl = WorkbenchPartLookup.getInstance()
				.getWorkbenchControl(WorkbenchPartLookup.getInstance().findActiveWorkbenchPartReference());
		log.debug("Active workbench control=" 
			+ (workbenchControl == null ? "null" : getControlDesc(workbenchControl)));
		final Control focusedControl = WidgetLookup.getInstance()
				.getFocusControl();
		log.debug("Focused control="
			+ (focusedControl == null ? "null" : getControlDesc(focusedControl)));
		if (hasControlSpecificParent(focusedControl, workbenchControl)) {
			return;
		}
		log.debug("No control in opened view has a focus!");
		log.debug("Setting implicit focus...");
		setFocusOnControlChild(workbenchControl);
	}
	
	private String getControlDesc(Control control) {
		StringBuffer sbDesc = new StringBuffer("Class=");
		sbDesc.append(control.getClass().getName());
		sbDesc.append(" Text=");
		String value;
		try {
			value = (String)ObjectUtil.invokeMethod(control, "getText");
		} catch (RuntimeException re) {
			value = "<unavailable>";
		}
		sbDesc.append(value);
		sbDesc.append(" TooltipText=");
		try {
			value = (String)ObjectUtil.invokeMethod(control, "getToolTipText");
		} catch (RuntimeException re) {
			value = "<unavailable>";
		}
		sbDesc.append(value);
		
		return sbDesc.toString();
	}
	
	private void setFocusOnControlChild(final Control workbenchControl) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Control[] childrenControls= ((Composite) workbenchControl).getChildren();
				if (childrenControls.length > 0) {
					Control firstChildControl = childrenControls[0];
					firstChildControl.setFocus();
				} else {
					log.debug("View with title '" + workbenchControl.getToolTipText() + "' has "
							+ "no children!");
				}
			}
		});
	}
	
	private boolean hasControlSpecificParent(final Control focusedControl, final Control workbenchControl) {
		Control parent = Display.syncExec(new ResultRunnable<Control>() {
			@Override
			public Control run() {
				Control parent = focusedControl;
				while (parent != null && !parent.equals(workbenchControl) && !parent.isDisposed()) {
					parent = parent.getParent();
				}
				return parent; 
			}
		});
		return workbenchControl.equals(parent);
	}
	/**
	 * Finds out whether specified {@link IViewPart} references to valid view.
	 * 
	 * @param viewPart view part to handle
	 * @return true if specified view part is valid, false otherwise
	 */
	public boolean isValid(final IViewPart viewPart) {
		return viewPart != null
				&& Display.syncExec(new ResultRunnable<Boolean>() {
					@Override
					public Boolean run() {
						return PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage()
								.findView(viewPart.getSite().getId()) != null;
					}
				});
	}
		
}
