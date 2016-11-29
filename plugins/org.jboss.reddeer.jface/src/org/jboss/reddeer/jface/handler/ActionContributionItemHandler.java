/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.jface.handler;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods for handling UI operations on {@link ActionContributionItem}
 * widgets.
 * 
 * @author rawagner
 *
 */
public class ActionContributionItemHandler {
	
	private static final Logger log = Logger.getLogger(ActionContributionItemHandler.class);

	private static ActionContributionItemHandler handler;

	private ActionContributionItemHandler() {

	}

	/**
	 * Gets instance of ActionContributionHandler.
	 * 
	 * @return instance of ActionContributionHandler
	 */
	public static ActionContributionItemHandler getInstance() {
		if (handler == null) {
			handler = new ActionContributionItemHandler();
		}
		return handler;
	}

	/**
	 * Finds out whether specified {@link ActionContributionItem} is enabled or
	 * not.
	 * 
	 * @param item item to handle
	 * @return true if specified item is enabled, false otherwise
	 */
	public boolean isEnabled(final ActionContributionItem item) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return item.isEnabled();
			}
		});
	}
	
	/**
	 * Selects (click) for ActionContributionItem.
	 * @param item to click
	 */
	public void select(final ActionContributionItem item){
		String actionNormalized = item.getAction().getText().replace("&", "");
		if(!item.isEnabled()){
			throw new CoreLayerException("Menu item " +actionNormalized+" is not enabled");
		} else if(!item.isVisible()){
			throw new CoreLayerException("Menu item " +actionNormalized+" is not visible");
		} else if ((item.getAction().getStyle() == IAction.AS_CHECK_BOX) || (item.getAction().getStyle() == IAction.AS_RADIO_BUTTON)) {
			log.info("Click on styled contribution item: " + actionNormalized);
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					item.getAction().setChecked(!item.getAction().isChecked());
					item.getAction().run();
				}
			});
		} else {
			log.info("Click on contribution item: " + actionNormalized);
			Display.asyncExec(new Runnable() {
				@Override
				public void run() {
					item.getAction().run();
				}
			});
		}
	}
}
