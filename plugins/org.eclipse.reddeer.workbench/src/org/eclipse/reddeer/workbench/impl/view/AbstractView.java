/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.impl.view;

import static org.eclipse.reddeer.common.wait.WaitProvider.waitUntil;
import static org.eclipse.reddeer.common.wait.WaitProvider.waitWhile;

import java.util.List;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.handler.ItemHandler;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatchers;
import org.eclipse.reddeer.swt.api.CTabItem;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.OpenButton;
import org.eclipse.reddeer.swt.impl.ctab.DefaultCTabItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenu;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.api.View;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.reddeer.workbench.handler.ViewHandler;
import org.eclipse.reddeer.workbench.handler.WorkbenchPartHandler;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * Abstract class for all View implementations
 * 
 * @author rawagner
 * 
 */
public class AbstractView implements View {

	private static final String SHOW_VIEW = "Show View";
	
	protected static final Logger log = Logger.getLogger(AbstractView.class);
	
	protected String[] path;

	protected Matcher<String> viewNameMatcher;
	
	protected CTabItem cTabItem;
	
	/**
	 * Initialize view with given viewToolTip. If view is opened than it will be
	 * focused
	 * 
	 * @param viewToolTip
	 *            of view to initialize
	 */
	public AbstractView(String viewToolTip) {
		this(new WithTextMatcher(new RegexMatcher("\\*?" + viewToolTip)));
	}

	/**
	 * Initialize view with given viewToolTip matcher. If view is opened than it
	 * will be focused
	 * 
	 * @param viewToolTip
	 *            matcher of view to initialize
	 */
	public AbstractView(Matcher<String> viewToolTip) {
		this.viewNameMatcher = viewToolTip;
		path = findRegisteredViewPath(viewToolTip);
		cTabItem = getViewCTabItem();
	}

	@Override
	public void maximize() {
		activate();
		log.info("Maximize view");
		WorkbenchPartHandler.getInstance().performAction(ActionFactory.MAXIMIZE);
	}

	@Override
	public void minimize() {
		activate();
		log.info("Minimize view");
		WorkbenchPartHandler.getInstance().performAction(ActionFactory.MINIMIZE);
	}

	@Override
	public void restore() {
		activate();
		log.info("Restore view");
		// in order to restore maximized window maximized action has to be
		// called
		WorkbenchPartHandler.getInstance().performAction(ActionFactory.MAXIMIZE);
	}

	@Override
	public void activate() {
		log.info("Activate view " + getTitle());
		cTabItemIsNotNull();
		getViewCTabItem().activate();
		ViewHandler.getInstance().focusChildControl();
	}

	/**
	 * Gets the view c tab item.
	 *
	 * @return the view c tab item
	 */
	protected CTabItem getViewCTabItem(){
		if (cTabItem != null && cTabItem.isDisposed()){
			cTabItem = null;
		}
		if (cTabItem == null) {
			if (!isOpened()){
				return cTabItem;
			}
			log.debug("Looking up CTabItem with text " + getTitle());
			cTabItem = new DefaultCTabItem(new WorkbenchShell(), viewNameMatcher);
		}
		return cTabItem; 
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

		throw new WorkbenchLayerException("View \"" + title
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

		throw new WorkbenchLayerException("View \"" + viewDescriptor.getLabel()
				+ "\" is not registered in any category");
	}

	private String[] pathForView(IViewDescriptor viewDescriptor,
			IViewCategory categoryDescriptor) {
		String[] path = new String[2];
		path[0] = categoryDescriptor.getLabel();
		path[1] = viewDescriptor.getLabel();
		return path;
	}

	@Override
	public void close() {
		activate();
		log.info("Close view");
		cTabItem.close();
		cTabItem = null;
	}

	@Override
	public void open() {
		log.info("Open view " + getTitle());
		// view is not opened, it has to be opened via menu
		if (getViewCTabItem() == null){
			log.info("Open " + getTitle() + " view via menu.");
			openViaMenu();
		}
		activate();
	}

	private void openViaMenu() {
		WithTextMatchers m = new WithTextMatchers(new RegexMatcher[] {
				new RegexMatcher("Window.*"),
				new RegexMatcher("Show View.*"),
				new RegexMatcher("Other...*") });
		Menu menu = new ShellMenu(m.getMatchers());
		menu.select();
		new DefaultShell(SHOW_VIEW);
		new DefaultTreeItem(path).select();
		try {
			new OpenButton().click(); // Oxygen
		} catch (CoreLayerException cle) {
			new OkButton().click(); // Neon
		}
		new GroupWait(waitWhile(new ShellIsAvailable(SHOW_VIEW)),
				waitUntil(new ViewCTabIsAvailable()));
	}

	private class ViewCTabIsAvailable extends AbstractWaitCondition {

		@Override
		public boolean test() {
			try {
				return getViewCTabItem() != null;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public String description() {
			return "view's CTabItem is available";
		}
	}
	
	private void cTabItemIsNotNull() {
		log.debug("View's cTabItem is found: " 
				+ (cTabItem != null ? true : false));
		if (cTabItem == null) {
			throw new WorkbenchLayerException("Cannot perform the specified "
					+ "operation before initialization "
					+ "provided by open method");
		}
	}

	/**
	 * Returns the title of the view.
	 *
	 * @return Title of the view
	 */
	public String getTitle() {
		return path[path.length - 1];
	}

	@Override
	public boolean isVisible() {
		return getViewCTabItem().isShowing();
	}
	
	@Override
	public boolean isOpened() {
		List<org.eclipse.swt.custom.CTabItem> tabs = WidgetLookup.getInstance().activeWidgets(new WorkbenchShell(), org.eclipse.swt.custom.CTabItem.class);
		for (org.eclipse.swt.custom.CTabItem tab : tabs){
			String text = ItemHandler.getInstance().getText(tab);
			if (viewNameMatcher.matches(text)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the view is active. This method is not supported due to a bug.
	 * See https://bugs.eclipse.org/bugs/show_bug.cgi?id=468948 for details.
	 *
	 * @return true, if is active
	 * @throws UnsupportedOperationException the unsupported operation exception
	 */
	public boolean isActive(){
		throw new UnsupportedOperationException("Method isActive is not supported due to the bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=468948");
	}
}
