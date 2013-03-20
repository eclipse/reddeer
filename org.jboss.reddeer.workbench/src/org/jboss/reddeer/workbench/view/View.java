package org.jboss.reddeer.workbench.view;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotWorkbenchPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.locate.CompositeWidgetLocator;
import org.jboss.reddeer.swt.lookup.impl.WorkbenchLookup;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.condition.ViewWithToolTipIsActive;
import org.jboss.reddeer.workbench.exception.ViewNotFoundException;

/**
 * Represents general view with ability to be opened. Subclasses should
 * represent the concrete views
 * 
 * @author jjankovi
 * 
 */
public abstract class View extends WorkbenchPart {

	private static final String SHOW_VIEW = "Show View";

	protected SWTBotView viewObject;

	private String[] path;

	public View(String viewToolTip) {
		super();
		path = findRegisteredViewPath(viewToolTip);
	}

	public View(String category, String viewToolTip) {
		super();
		path = new String[2];
		path[0] = category;
		path[1] = viewToolTip;
	}

	public void open() {
		log.debug("Showing " + viewTooltip() + " view");
		viewObject = viewByTitle(viewTooltip());
		if (viewObject == null) {
			log.info("Opening " + viewTooltip()
					+ " view via menu.");
			RegexMatchers m = new RegexMatchers("Window.*", "Show View.*",
					"Other...*");
			Menu menu = new ShellMenu(m.getMatchers());
			menu.select();
			new DefaultShell(SHOW_VIEW);
			new DefaultTreeItem(path).select();
			new PushButton("OK").click();
			new WaitUntil(new ViewWithToolTipIsActive(viewTooltip()));
			viewObject = Bot.get().activeView();
		}
		viewObject.setFocus();
		viewObject.show();
		setCompositeWidget();
	}
	
	public String getTitle(){
		return viewObject.getTitle();
	}

	@Override
	protected SWTBotWorkbenchPart<IViewReference> workbenchPart() {
		return viewObject;
	}

	private SWTBotView viewByTitle(String viewTitle) {
		for (SWTBotView view : Bot.get().views()) {
			if (view.getTitle().equals(viewTitle)) {
				return view;
			}
		}
		return null;
	}

	private String[] findRegisteredViewPath(String viewTooltip) {

		IViewDescriptor viewDescriptor = findView(viewTooltip);
		IViewCategory categoryDescriptor = findViewCategory(viewDescriptor);
		return pathForView(viewDescriptor, categoryDescriptor);

	}

	private IViewDescriptor findView(String viewTooltip) {
		IViewDescriptor[] views = PlatformUI.getWorkbench().getViewRegistry()
				.getViews();
		for (IViewDescriptor view : views) {
			if (view.getLabel().equals(viewTooltip)) {
				return view;
			}
		}

		throw new ViewNotFoundException("View \"" + viewTooltip
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

	private String viewTooltip() {
		return path[path.length - 1];
	}
	
}