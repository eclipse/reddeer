package org.jboss.reddeer.workbench.handler;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.junit.logging.Logger;
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
	
	public void setFocus(final IViewPart viewPart, final String viewTitle) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				log.debug("Setting focus to workbench part with title=" + viewPart.getTitle());
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().activate(viewPart);
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().showView(getViewRefrenceByTitle(viewTitle).getId());
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
	
	public void close(final IViewPart viewPart){
		log.info("Hiding view " + viewPart.getTitle());
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().hideView(viewPart);
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
		log.error("No control in opened view has a focus!");
		log.error("Setting implicit focus...");
		setFocusOnControlChild(workbenchControl);
	}
	
	private IViewReference getViewRefrenceByTitle(final String title) {
		return Display.syncExec(new ResultRunnable<IViewReference>() {
			@Override
			public IViewReference run() {
				IViewReference[] views = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getViewReferences();
				for (IViewReference iViewReference : views) {
					if (iViewReference.getPartName().equals(title)) {
						return iViewReference;
					}
				}
				return null;
			}
		});
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
					log.warn("View with title '" + workbenchControl.getToolTipText() + "' has "
							+ "no children!");
				}
			}
		});
	}
	
	private boolean hasControlSpecificParent(final Control focusedControl,
			final Control workbenchControl) {
		Control parent = Display.syncExec(new ResultRunnable<Control>() {
			@Override
			public Control run() {
				Control parent = focusedControl;
				while (parent != workbenchControl && parent != null) {
					parent = parent.getParent();
				}
				return parent; 
			}
		});
		return parent != null;
	}

}
