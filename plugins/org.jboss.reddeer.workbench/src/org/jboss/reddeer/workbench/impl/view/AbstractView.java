package org.jboss.reddeer.workbench.impl.view;

import java.util.Arrays;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatchers;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.api.View;
import org.jboss.reddeer.workbench.api.WorkbenchPart;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.handler.ViewHandler;
import org.jboss.reddeer.workbench.handler.WorkbenchPartHandler;
import org.jboss.reddeer.workbench.lookup.WorkbenchPartLookup;

/**
 * Abstract class for all View implementations
 * 
 * @author rawagner
 * 
 */
public class AbstractView implements View {

	protected static final Logger log = Logger.getLogger(AbstractView.class);
	protected IViewPart viewPart;
	private static final String SHOW_VIEW = "Show View";
	protected String[] path;

	/**
	 * Initialize view with given viewToolTip. If view is opened than it will be
	 * focused
	 * 
	 * @param viewToolTip
	 *            of view to initialize
	 */
	public AbstractView(String viewToolTip) {
		ViewPartIsFound viewIsFound = new ViewPartIsFound(viewToolTip);
		new WaitUntil(viewIsFound, TimePeriod.NORMAL, false);
		viewPart = viewIsFound.getPart();
		path = findRegisteredViewPath(new WithTextMatcher(viewToolTip));
		if (viewPart != null) {
			activate();
		}
	}

	/**
	 * Initialize view with given viewToolTip matcher. If view is opened than it
	 * will be focused
	 * 
	 * @param viewToolTip
	 *            matcher of view to initialize
	 */
	public AbstractView(Matcher<String> viewToolTip) {
		ViewPartIsFound viewIsFound = new ViewPartIsFound(viewToolTip);
		new WaitUntil(viewIsFound, TimePeriod.NORMAL, false);
		viewPart = viewIsFound.getPart();
		path = findRegisteredViewPath(viewToolTip);
		if (viewPart != null) {
			activate();
		}
	}

	@Override
	public void maximize() {
		activate();
		log.info("Maximize view");
		WorkbenchPartHandler.getInstance()
				.performAction(ActionFactory.MAXIMIZE);
	}

	@Override
	public void minimize() {
		activate();
		log.info("Minimize view");
		WorkbenchPartHandler.getInstance()
				.performAction(ActionFactory.MINIMIZE);
	}

	/**
	 * {@link WorkbenchPart.restore}
	 */
	@Override
	public void restore() {
		activate();
		log.info("Restore view");
		// in order to restore maximized window maximized action has to be
		// called
		WorkbenchPartHandler.getInstance()
				.performAction(ActionFactory.MAXIMIZE);
	}

	/**
	 * {@link WorkbenchPart.activate}
	 */
	@Override
	public void activate() {
		log.info("Activate view " + viewTitle());
		viewPartIsNotNull();
		ViewHandler.getInstance().setFocus(viewPart, viewTitle());
	}

	private String[] findRegisteredViewPath(Matcher<String> title) {

		IViewDescriptor viewDescriptor = findView(title);
		IViewCategory categoryDescriptor = findViewCategory(viewDescriptor);
		return pathForView(viewDescriptor, categoryDescriptor);

	}

	private IViewDescriptor findView(Matcher<String> title) {
		IViewDescriptor[] views = PlatformUI.getWorkbench().getViewRegistry()
				.getViews();
		for (IViewDescriptor view : views) {
			if (title.matches(view.getLabel())) {
				return view;
			}
		}

		throw new WorkbenchPartNotFound("View \"" + title
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

		throw new WorkbenchPartNotFound("View \"" + viewDescriptor.getLabel()
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

	@Override
	public void close() {
		activate();
		log.info("Close view");
		viewPartIsNotNull();
		ViewHandler.getInstance().close(viewPart);
		viewPart = null;
	}

	@Override
	public void open() {
		log.info("Open view " + viewTitle());
		viewPart = WorkbenchPartLookup.getInstance().getViewByTitle(
				new WithTextMatcher(viewTitle()));
		// view is not opened, it has to be opened via menu
		if (viewPart == null) {
			log.info("Open " + viewTitle() + " view via menu.");
			WithTextMatchers m = new WithTextMatchers(new RegexMatcher[] {
					new RegexMatcher("Window.*"),
					new RegexMatcher("Show View.*"),
					new RegexMatcher("Other...*") });
			Menu menu = new ShellMenu(m.getMatchers());
			menu.select();
			new DefaultShell(SHOW_VIEW);
			new DefaultTreeItem(path).select();
			new OkButton().click();
			new WaitWhile(new ShellWithTextIsActive(SHOW_VIEW));
			try {
				new WaitUntil(new ViewPartIsActive());
				viewPart = (IViewPart) WorkbenchPartLookup.getInstance()
						.getActiveWorkbenchPart();
			} catch (WaitTimeoutExpiredException w) {
				log.debug("View " + Arrays.toString(path) + " is not active");
				viewPart = WorkbenchPartLookup.getInstance().getViewByTitle(
						new WithTextMatcher(viewTitle()));
			}
		} else {
			log.info("View is open, just activate it");
		}
		activate();
	}

	class ViewPartIsActive implements WaitCondition {

		@Override
		public boolean test() {
			return WorkbenchPartLookup.getInstance().getActiveWorkbenchPart() instanceof IViewPart;
		}

		@Override
		public String description() {
			return "View part is active";
		}

	}

	class ViewPartIsFound implements WaitCondition {

		private String title;
		private Matcher<String> titleMatcher;
		private IViewPart part;

		public ViewPartIsFound(String title) {
			this.title = title;
		}

		public ViewPartIsFound(Matcher<String> title) {
			this.titleMatcher = title;
		}

		@Override
		public boolean test() {
			if (title != null) {
				Matcher<String> titleM = new WithTextMatcher(title);
				part = WorkbenchPartLookup.getInstance().getViewByTitle(titleM);
			} else {
				part = WorkbenchPartLookup.getInstance().getViewByTitle(
						titleMatcher);
			}
			if (part == null) {
				return false;
			}
			return true;
		}

		@Override
		public String description() {
			if (title != null) {
				return "viewPart with title " + title + " is found";
			}
			return "viewPart with title " + titleMatcher + " is found";
		}

		public IViewPart getPart() {
			return part;
		}

	}

	private void viewPartIsNotNull() {
		if (viewPart == null) {
			throw new WorkbenchLayerException("Cannot perform the specified "
					+ "operation before initialization "
					+ "provided by open method");
		}
	}

	public String getTitle() {
		return WorkbenchPartHandler.getInstance().getTitle(viewPart);
	}
}
