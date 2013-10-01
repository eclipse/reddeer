package org.jboss.reddeer.workbench.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ObjectUtil;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.WorkbenchPart;
import org.jboss.reddeer.workbench.condition.ActiveFocusControlIsInActiveView;
import org.jboss.reddeer.workbench.exception.ViewNotFoundException;

/**
 * Represents general view with ability to be opened. Subclasses should
 * represent the concrete views
 * 
 * @author jjankovi
 * @author rhopp
 * 
 */
public abstract class View extends WorkbenchPart{

	private static final String SHOW_VIEW = "Show View";

	private String[] path;

	public View(String viewToolTip) {
		super(viewToolTip);
		path = findRegisteredViewPath(viewToolTip);
		setFocusIfViewIsOpened();
	}

	public View(String category, String viewToolTip) {
		super();
		path = new String[2];
		path[0] = category;
		path[1] = viewToolTip;
		setFocusIfViewIsOpened();
	}

	/**
	 * Closes this view. UnsupportedOperationException is thrown, when view
	 * wasn't opened yet.
	 */

	@Override
	public void close() {
		if (workbenchPart == null) {
			throw new UnsupportedOperationException(
					"Cannot close workbench part "
							+ "before initialization provided by open method");
		}
		log.info("Hiding view " + getTitle());
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().hideView(getViewPart());
			}
		});
		workbenchPart = null;
	}

	/**
	 * Opens this view and set focus to it. If it's already open, the view just
	 * gains focus.
	 */

	public void open() {
		log.info("Showing " + viewTitle() + " view");
		workbenchPart = getPartByTitle(viewTitle());
		// view is not opened, it has to be opened via menu
		if (workbenchPart == null) {
			log.info("Opening " + viewTitle() + " view via menu.");
			RegexMatchers m = new RegexMatchers("Window.*", "Show View.*",
					"Other...*");
			Menu menu = new ShellMenu(m.getMatchers());
			menu.select();
			new DefaultShell(SHOW_VIEW);
			new DefaultTreeItem(path).select();
			// Eclipse bug in 4.2.1, fixed in 4.2.2
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=395913
			try {
				new PushButton("OK").click();
			}
			catch (RuntimeException e) {
				new PushButton("ok").click();
			}
			new WaitWhile(new ShellWithTextIsActive(SHOW_VIEW));
			workbenchPart = getActiveWorkbenchPart();
		}
		setFocusIfViewIsOpened();

	}
	
	private void setFocusIfViewIsOpened() {
		workbenchPart = getPartByTitle(viewTitle());
		if (workbenchPart != null) {
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					log.debug("Setting focus to workbench part with title=" + workbenchPart.getTitle());
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().activate(workbenchPart);
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().showView(getViewRefrenceByTitle(viewTitle()).getId());
					} catch (PartInitException pie) {
						throw new SWTLayerException("Unable to show view " + workbenchPart.getTitle(),
							pie);
					}
					workbenchPart.setFocus();
				}
			});
			new WaitUntil(new ActiveFocusControlIsInActiveView(),TimePeriod.NORMAL,false);
			focusChildControl();
		}
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

	private String[] findRegisteredViewPath(String title) {

		IViewDescriptor viewDescriptor = findView(title);
		IViewCategory categoryDescriptor = findViewCategory(viewDescriptor);
		return pathForView(viewDescriptor, categoryDescriptor);

	}

	private IViewDescriptor findView(String title) {
		IViewDescriptor[] views = PlatformUI.getWorkbench().getViewRegistry()
				.getViews();
		for (IViewDescriptor view : views) {
			if (view.getLabel().equals(title)) {
				return view;
			}
		}

		throw new ViewNotFoundException("View \"" + title
				+ "\" is not registered in workbench");
	}

	private IViewCategory findViewCategory(IViewDescriptor viewDescriptor) {
		IViewCategory[] categories = PlatformUI.getWorkbench()
				.getViewRegistry().getCategories();
		for (IViewCategory category : categories) {
			for (IViewDescriptor ivd : category.getViews()) {
				if (ivd.getId().equals(viewDescriptor.getId())) {
					return category;
				}
			}
		}

		throw new ViewNotFoundException("View \"" + viewDescriptor.getLabel()
				+ "\" is not registered in any category");
	}

	private String[] pathForView(IViewDescriptor viewDescriptor,
			IViewCategory categoryDescriptor) {
		String[] path = new String[2];
		path[0] = categoryDescriptor.getLabel();
		path[1] = viewDescriptor.getLabel();
		return path;
	}

	private String viewTitle() {
		return path[path.length - 1];
	}

	protected IViewPart getViewPart() {
		if (workbenchPart == null) {
			throw new RuntimeException("workbenchPart is null");
		}
		if (!(workbenchPart instanceof IViewPart)) {
			throw new RuntimeException(
					"workbenchPart isn't instance of IViewPart");
		}
		return (IViewPart) workbenchPart;
	}

	@Override
	protected IWorkbenchPart getPartByTitle(final String title) {
		return Display.syncExec(new ResultRunnable<IWorkbenchPart>() {

			@Override
			public IWorkbenchPart run() {
				IViewReference[] views = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getViewReferences();
				for (IViewReference iViewReference : views) {
					if (iViewReference.getPartName().equals(title)) {
						return iViewReference.getView(false);
					}
				}
				return null;
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
}
