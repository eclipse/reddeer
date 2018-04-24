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
package org.eclipse.reddeer.eclipse.ui.perspectives;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatchers;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.YesButton;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;

/**
 * Abstract parent for each Perspective implementation
 * 
 * @author vlado pakan
 * 
 */
public abstract class AbstractPerspective {

	protected final Logger log = Logger.getLogger(this.getClass());

	private String perspectiveLabel;

	/**
	 * Constructs the perspective with a given label.
	 * 
	 * @param perspectiveLabel
	 *            Perspective label
	 */
	public AbstractPerspective(String perspectiveLabel) {
		super();
		this.perspectiveLabel = perspectiveLabel;
		if (!isPerspectiveAvailable()) {
			throw new EclipseLayerException("Perspective " + perspectiveLabel + " isn't available");
		}
	}

	/**
	 * Opens the perspective.
	 */
	public void open() {
		log.info("Open perspective: '" + getPerspectiveLabel() + "'");
		if (isOpened()) {
			log.debug("Perspective '" + getPerspectiveLabel() + "' is already opened.");
		} else {
			log.debug("Trying to open perspective: '" + getPerspectiveLabel() + "'");
			new DefaultToolItem(new DefaultShell(), "Open Perspective").click();
			Shell perspectiveShell = new DefaultShell("Open Perspective");
			DefaultTable table = new DefaultTable();
			try {
				// Try to select perspective label within available perspectives
				table.select(getPerspectiveLabel());
			} catch (CoreLayerException swtLayerException) {
				// Try to select perspective label within available perspectives with
				// "(default)" suffix
				table.select(getPerspectiveLabel() + " (default)");
			}

			WidgetIsFound openButton = new WidgetIsFound(org.eclipse.swt.widgets.Button.class,
					new WithMnemonicTextMatcher("Open"));

			Button button;
			if (openButton.test()) {
				button = new PushButton("Open"); // oxygen changed button text
			} else {
				button = new OkButton();
			}
			button.click();
			new WaitWhile(new ShellIsAvailable(perspectiveShell));
		}
	}

	/**
	 * Reset.
	 */
	public void reset() {
		if (!isOpened()) {
			throw new EclipseLayerException("Trying to reset perspective that is not open");
		}
		MenuItem menu = getResetMenuItem();
		menu.select();
		new DefaultShell("Reset Perspective");
		WidgetIsFound resetButton = new WidgetIsFound(org.eclipse.swt.widgets.Button.class,
				new WithMnemonicTextMatcher("Reset Perspective"));

		Button button;
		if (resetButton.test()) {
			button = new PushButton("Reset Perspective"); // photon changed button text
		} else {
			button = new YesButton();
		}
		button.click();
	}

	/**
	 * Returns true if reset perspective menu item is enabled, false otherwise
	 * @return true if reset perspective menu item is enabled, false otherwise
	 */
	public boolean isResetEnabled() {
		return getResetMenuItem().isEnabled();
	}

	private MenuItem getResetMenuItem() {
		// Prior to Eclipse Mars path to Reset Perspective menu
		WithTextMatchers m = new WithTextMatchers(
				new RegexMatcher[] { new RegexMatcher("Window.*"), new RegexMatcher("Reset Perspective...") });
		MenuItem menu;
		try {
			menu = new ShellMenuItem(m.getMatchers());
		} catch (CoreLayerException swtle) {
			// Try menu path for Mars and higher versions
			m = new WithTextMatchers(new RegexMatcher[] { new RegexMatcher("Window.*"),
					new RegexMatcher("Perspective.*"), new RegexMatcher("Reset Perspective...") });
			menu = new ShellMenuItem(m.getMatchers());
		}
		return menu;
	}

	/**
	 * Returns perspective label.
	 * 
	 * @return Perspective label
	 */
	public String getPerspectiveLabel() {
		return perspectiveLabel;
	}

	/**
	 * Returns whether the perspective is open.
	 * 
	 * @return Whether the perspective is open
	 */
	public boolean isOpened() {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return getActivePerspective().getLabel().equals(perspectiveLabel);
			}
		});
	}

	private boolean isPerspectiveAvailable() {
		IPerspectiveDescriptor perspective = PlatformUI.getWorkbench().getPerspectiveRegistry()
				.findPerspectiveWithLabel(perspectiveLabel);
		if (perspective == null) {
			return false;
		}
		return true;
	}

	private IPerspectiveDescriptor getActivePerspective() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective();
	}
}
