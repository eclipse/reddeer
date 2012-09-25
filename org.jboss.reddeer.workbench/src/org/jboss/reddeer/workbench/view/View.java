package org.jboss.reddeer.workbench.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotWorkbenchPart;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.ListResult;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarDropDownButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarPushButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarRadioButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarSeparatorButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarToggleButton;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.jboss.reddeer.swt.util.Bot;
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
			viewObject = Bot.get().activeView();
		}
		viewObject.setFocus();
		viewObject.show();
	}
	
	public String getTitle(){
		return viewObject.getTitle();
	}
	
	public SWTBotToolbarButton getToolBar(String toolbarToolTip) {
		List<SWTBotToolbarButton> toolbarButtons = getToolbarButtons();
		for (SWTBotToolbarButton button : toolbarButtons) {
			if (button.getToolTipText().equals(toolbarToolTip)) {
				return button;
			}
		}
		throw new WidgetNotFoundException("Toolbar button '" + toolbarToolTip + "' was not " +
				"found or enabled");
	}

	/**
	 * Get toolbar buttons contained within view It's workaround for Eclipse
	 * Juno where SWTBot is not able to find toolbar buttons of view
	 * 
	 * @return
	 */
	private List<SWTBotToolbarButton> getToolbarButtons() {
		return UIThreadRunnable.syncExec(new ListResult<SWTBotToolbarButton>() {

			public List<SWTBotToolbarButton> run() {
				IWorkbenchPart obj = ((WorkbenchPartReference) viewObject
						.getReference()).getPart(false);
				ToolBar toolbar = null;
				IToolBarManager t = ((IViewSite) obj.getSite()).getActionBars()
						.getToolBarManager();
				if (t instanceof ToolBarManager) {
					toolbar = ((ToolBarManager) t).getControl();
				}
				final List<SWTBotToolbarButton> l = new ArrayList<SWTBotToolbarButton>();

				if (toolbar == null)
					return l;

				ToolItem[] items = toolbar.getItems();
				for (int i = 0; i < items.length; i++) {
					try {
						if (SWTUtils.hasStyle(items[i], SWT.PUSH))
							l.add(new SWTBotToolbarPushButton(items[i]));
						else if (SWTUtils.hasStyle(items[i], SWT.CHECK))
							l.add(new SWTBotToolbarToggleButton(items[i]));
						else if (SWTUtils.hasStyle(items[i], SWT.RADIO))
							l.add(new SWTBotToolbarRadioButton(items[i]));
						else if (SWTUtils.hasStyle(items[i], SWT.DROP_DOWN))
							l.add(new SWTBotToolbarDropDownButton(items[i]));
						else if (SWTUtils.hasStyle(items[i], SWT.SEPARATOR))
							l.add(new SWTBotToolbarSeparatorButton(items[i]));
					} catch (WidgetNotFoundException e) {
						e.printStackTrace();
					}
				}

				return l;

			}
		});
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
