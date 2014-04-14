package org.jboss.reddeer.workbench.impl.view;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.hamcrest.Matcher;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.matcher.WithRegexMatchers;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.api.View;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.handler.ViewHandler;
import org.jboss.reddeer.workbench.handler.WorkbenchPartHandler;
import org.jboss.reddeer.workbench.lookup.WorkbenchPartLookup;

/**
 * Abstract class for all View implementations
 * @author rawagner
 *
 */
public class AbstractView implements View{
	
	protected final Logger log = Logger.getLogger(this.getClass());
	protected IViewPart viewPart;
	private static final String SHOW_VIEW = "Show View";
	protected String[] path;
	
	/**
	 * Initialize view with given viewToolTip. 
	 * If view is opened than it will be focused
	 * 
	 * @param viewToolTip of view to initialize
	 */
	public AbstractView(String viewToolTip){
		ViewPartIsFound viewIsFound = new ViewPartIsFound(viewToolTip);
		new WaitUntil(viewIsFound, TimePeriod.NORMAL, false);
		viewPart = viewIsFound.getPart();
		path = findRegisteredViewPath(new WithTextMatcher(viewToolTip));
		if(viewPart != null){
			ViewHandler.getInstance().setFocus(viewPart, viewTitle());
		}		
	}
	
	/**
	 * Initialize view with given viewToolTip matcher. 
	 * If view is opened than it will be focused
	 * 
	 * @param viewToolTip matcher of view to initialize
	 */
	public AbstractView(Matcher<String> viewToolTip){
		ViewPartIsFound viewIsFound = new ViewPartIsFound(viewToolTip);
		new WaitUntil(viewIsFound, TimePeriod.NORMAL, false);
		viewPart = viewIsFound.getPart();
		path = findRegisteredViewPath(viewToolTip);
		if(viewPart != null){
			ViewHandler.getInstance().setFocus(viewPart, viewTitle());
		}
	}

	@Override
	public void maximize() {
		ViewHandler.getInstance().setFocus(viewPart, viewTitle());
		WorkbenchPartHandler.getInstance().performAction(ActionFactory.MAXIMIZE);
	}
	
	@Override
	public void minimize() {
		ViewHandler.getInstance().setFocus(viewPart, viewTitle());
		WorkbenchPartHandler.getInstance().performAction(ActionFactory.MINIMIZE);
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
		if (viewPart == null) {
			throw new UnsupportedOperationException(
					"Cannot close view "
							+ "before initialization provided by open method");
		}
		ViewHandler.getInstance().close(viewPart);
		viewPart = null;
	}

	@Override
	public void open() {
		log.info("Showing " + viewTitle() + " view");
		viewPart = WorkbenchPartLookup.getInstance().getViewByTitle(new WithTextMatcher(viewTitle()));
		// view is not opened, it has to be opened via menu
		if (viewPart == null) {
			log.info("Opening " + viewTitle() + " view via menu.");
			WithRegexMatchers m = new WithRegexMatchers("Window.*", "Show View.*",
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
			new WaitUntil(new ViewPartIsActive());
			viewPart = (IViewPart)WorkbenchPartLookup.getInstance().getActiveWorkbenchPart();
		}
		ViewHandler.getInstance().setFocus(viewPart, viewTitle());
	}

	class ViewPartIsActive implements WaitCondition{

		@Override
		public boolean test() {
			 return WorkbenchPartLookup.getInstance().getActiveWorkbenchPart() instanceof IViewPart;
		}

		@Override
		public String description() {
			return "View part is active";
		}
		
		
	}
	
	
	class ViewPartIsFound implements WaitCondition{
		
		private String title;
		private Matcher<String> titleMatcher;
		private IViewPart part;
		
		public ViewPartIsFound(String title){
			this.title = title;
		}

		public ViewPartIsFound(Matcher<String> title){
			this.titleMatcher = title;
		}

		@Override
		public boolean test() {
			if(title != null){
				Matcher<String> titleM = new WithTextMatcher(title);
				part = WorkbenchPartLookup.getInstance().getViewByTitle(titleM);
			} else {
				part = WorkbenchPartLookup.getInstance().getViewByTitle(titleMatcher);
			}
			if(part == null){
				return false;
			}
			return true;
		}

		@Override
		public String description() {
			if(title != null){
				return "viewPart with title "+title+" is found";
			}
			return "viewPart with title "+titleMatcher+" is found";
		}
		
		public IViewPart getPart(){
			return part;
		}
		
	}
}
