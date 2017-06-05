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

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatchers;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.ctab.DefaultCTabItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenu;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.api.View;
import org.eclipse.reddeer.workbench.condition.ViewIsOpen;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchPartLookup;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchShellLookup;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.reddeer.workbench.handler.WorkbenchPartHandler;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.workbench.lookup.ViewLookup;
import org.eclipse.reddeer.workbench.part.AbstractWorkbenchPart;

/**
 * Abstract class for all View implementations
 * 
 * @author rawagner
 * 
 */
public abstract class AbstractView extends AbstractWorkbenchPart implements View {

	private static final String SHOW_VIEW = "Show View";

	protected static final Logger log = Logger.getLogger(AbstractView.class);

	protected String[] path;

	protected Matcher<String> viewTitle;

	/**
	 * Initialize view with given title. If view is opened than it will be
	 * focused
	 * 
	 * @param viewTitle
	 *            title of view to initialize
	 */
	public AbstractView(String viewTitle) {
		this(new WithTextMatcher(new RegexMatcher("\\*?" + viewTitle)));
	}

	/**
	 * Initialize view with given view title matcher. If view is open than it
	 * will be focused
	 * 
	 * @param viewTitle
	 *            matcher of view to initialize
	 */
	public AbstractView(Matcher<String> viewTitle) {
		super(null);
		this.viewTitle = viewTitle;
		this.path = ViewLookup.getInstance().findRegisteredViewPath(viewTitle);
	}

	@Override
	public void activate() {
		checkOpen();
		log.info("Activate view " + getTitle());
		cTabItem.activate();
		WorkbenchPartHandler.getInstance().focusChildControl(WorkbenchPartLookup.getInstance().getActiveWorkbenchPart());
	}

	@Override
	public String getTitle() {
		checkOpen();
		return super.getTitle();
	}

	@Override
	public String getTitleToolTip() {
		checkOpen();
		return super.getTitleToolTip();
	}

	@Override
	public Image getTitleImage() {
		checkOpen();
		return super.getTitleImage();
	}
	
	@Override
	public boolean isActive() {
		checkOpen();
		return super.isActive();
	}

	@Override
	public void close() {
		checkOpen();
		activate();
		log.info("Close view '"+getTitle()+"'");
		cTabItem.close();
	}

	@Override
	public void open() {
		if(isOpen()){
			log.info("View '" +viewTitle+ "' is already open. Activate.");
		} else {
			log.info("Open '" +viewTitle+ "' view via menu.");
			openViaMenu();
		}
		activate();
	}

	private void openViaMenu() {
		WithTextMatchers m = new WithTextMatchers(new RegexMatcher[] { new RegexMatcher("Window.*"),
				new RegexMatcher("Show View.*"), new RegexMatcher("Other...*") });
		// set focus to workbench shell
		Menu menu = new ShellMenu(new WorkbenchShell(), m.getMatchers());
		menu.select();
		org.eclipse.reddeer.swt.api.Shell showView = new DefaultShell(SHOW_VIEW);
		new DefaultTreeItem(path).select();
		
		WidgetIsFound openButton = new WidgetIsFound(org.eclipse.swt.widgets.Button.class,
				new WithMnemonicTextMatcher("Open"));
		
		
		Button button;
		if(openButton.test()){
			button = new PushButton("Open"); //oxygen changed button text
		} else {
			button = new OkButton();	
		}
		button.click();
		
		new GroupWait(waitWhile(new ShellIsAvailable(showView)),
				waitUntil(new ViewIsOpen(this)));
	}

	@Override
	public boolean isOpen() {
		if(cTabItem != null && !cTabItem.isDisposed()){
			return true;
		}
		Shell workbenchShell = WorkbenchShellLookup.getInstance().getWorkbenchShell();
		Class<org.eclipse.swt.custom.CTabItem> clazz = org.eclipse.swt.custom.CTabItem.class;
		
		WidgetIsFound found = new WidgetIsFound(clazz, workbenchShell, new WithTextMatcher(viewTitle));
		return found.test();
	}
	
	protected void checkOpen(){
		if(!isOpen()){
			throw new WorkbenchLayerException("View '"+viewTitle+"' is not open");
		}
		if(cTabItem == null || cTabItem.isDisposed()){
			this.cTabItem = new DefaultCTabItem(new WorkbenchShell(),viewTitle);
		}
	}

}