package org.jboss.reddeer.workbench.handler;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ObjectUtil;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.condition.ActiveFocusControlIsInActiveView;

/**
 * View handler handles operations for View instances
 * @author rawagner
 *
 */
public class ViewHandler {
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	private static ViewHandler instance;
	
	private ViewHandler(){
		
	}
	
	public static ViewHandler getInstance(){
		if(instance == null){
			instance = new ViewHandler();
		}
		return instance;
	}
	
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
					throw new SWTLayerException("Unable to show view " + viewPart.getTitle(),
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
	 * Closes view specified by view part
	 * @param viewPart
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
	 * Returns true if view specified by view part is visible on workbench active page
	 * @param viewPart
	 * @return
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

	private void focusChildControl() {
		final Control workbenchControl = WorkbenchLookup
				.getWorkbenchControl(WorkbenchLookup.findActiveWorkbenchPart());
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

}
