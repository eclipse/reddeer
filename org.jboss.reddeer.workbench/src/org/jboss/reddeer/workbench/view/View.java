package org.jboss.reddeer.workbench.view;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.workbench.exception.ViewNotFoundException;

/**
 * Represents general view with ability to be opened. Subclasses should represent the concrete views 
 * 
 * @author jjankovi
 *
 */
public abstract class View {

	protected final Logger log = Logger.getLogger(this.getClass());
	
	private static final String SHOW_VIEW = "Show View";
	
	private SWTBotView viewObject;
	
	private String[] path;
	
	public View(String viewToolTip) {
		viewObject = viewByTitle(viewToolTip);
		if (viewObject == null) {
			path = findRegisteredViewPath(viewToolTip);
		}
	}
	
	public void open() {
		log.info("Open " + viewTooltip() + " view");
		if (viewObject == null) {
			log.debug(viewTooltip() + " view was not already opened. Opening via menu.");
			RegexMatchers m = new RegexMatchers("Window.*", "Show View.*", "Other...*");
			Menu menu = new ShellMenu(m.getMatchers());
			menu.select();
			new ActiveShell(SHOW_VIEW);
			new DefaultTreeItem(path).select();
			new PushButton("OK").click();
			viewObject = Bot.get().activeView();
		}
		viewObject.setFocus();
		viewObject.show();
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
		IViewDescriptor[] views = PlatformUI.getWorkbench().getViewRegistry().getViews();
		for (IViewDescriptor view : views) {
			if (view.getLabel().equals(viewTooltip)) {
				return view;
			}
		}
		
		throw new ViewNotFoundException("View \"" + viewTooltip + 
				"\" is not registered in workbench");
	}
	
	private IViewCategory findViewCategory(IViewDescriptor viewDescriptor) {
		IViewCategory[] categories = PlatformUI.getWorkbench().getViewRegistry().getCategories();
		for (IViewCategory category : categories) {
			for (IViewDescriptor ivd : category.getViews()) {
				if (ivd.getId().equals(viewDescriptor.getId())) {
					return category;
				}
			}
		}
		
		throw new ViewNotFoundException("View \"" + viewDescriptor.getLabel() + 
				"\" is not registered in any category");
	}
	
	private String[] pathForView(IViewDescriptor viewDescriptor,
			IViewCategory categoryDescriptor) {
		String[] path = new String[2];
		path[0] = categoryDescriptor.getLabel();
		path[1] = viewDescriptor.getLabel();
		return path;
	}
	
	private String viewTooltip() {
		if (viewObject == null) {
			return path[path.length-1];
		}
		return viewObject.getTitle();
	}
	
}
