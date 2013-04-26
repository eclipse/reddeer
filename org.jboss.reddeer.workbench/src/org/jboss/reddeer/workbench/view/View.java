package org.jboss.reddeer.workbench.view;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.jboss.reddeer.swt.reference.ReferenceComposite;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.workbench.WorkbenchPart;
import org.jboss.reddeer.workbench.exception.ViewNotFoundException;

/**
 * Represents general view with ability to be opened. Subclasses should
 * represent the concrete views
 * 
 * @author jjankovi
 * @author rhopp
 * 
 */
public abstract class View extends WorkbenchPart implements ReferencedComposite {

	private static final String SHOW_VIEW = "Show View";

	private String[] path;

	public View(String viewToolTip) {
		super(viewToolTip);
		path = findRegisteredViewPath(viewToolTip);
	}

	public View(String category, String viewToolTip) {
		super();
		path = new String[2];
		path[0] = category;
		path[1] = viewToolTip;
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
	}

	/**
	 * Opens this view and set focus to it. If it's already open, the view just
	 * gains focus.
	 */

	public void open() {
		log.debug("Showing " + viewTitle() + " view");
		workbenchPart = getPartByTitle(viewTitle());
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
			workbenchPart = getActiveWorkbenchPart();
		}
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().activate(workbenchPart);
				workbenchPart.setFocus();
			}
		});
		setAsReference();
	}
	
	/**
	 * @return Title of this view
	 */

	public String getTitle() {
		return workbenchPart.getTitle();
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
					if (iViewReference.getTitle().matches(title + ".*")) {
						return iViewReference.getView(false);
					}
				}
				return null;
			}
		});
	}
	
	@Override
	public void setAsReference() {
		ReferenceComposite.setComposite(null);
	}
}
