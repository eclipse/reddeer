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
package org.jboss.reddeer.eclipse.condition;

import java.util.List;
import java.util.stream.Collectors;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;

/**
 * Returns true, if there is server with specified name
 * 
 * @author Vlado Pakan, jkopriva@redhat.com
 *
 */
public class ServerExists extends AbstractWaitCondition {

	private ServersView view;
	
	private String name;
	
	/**
	 * Construct the condition with a given name.
	 * 
	 * @param name Name
	 */
	public ServerExists(String name) {
		this.name = name;
		view = new ServersView();
		view.open();
	}

	@Override
	public boolean test() {
		try{
			view.getServer(this.name);
			return true;
		} catch (RedDeerException ele){
			return false;
		}		
	}
	
	@Override
	public String description() {
		return "there is server with name: " + this.name;
	}
	
	@Override
	public String errorMessageWhile() {
		return "server with name: '" + this.name + "' has been found.";
	}

	@Override
	public String errorMessageUntil() {
		List<String> servers = view.getServers().stream().map(it -> it.getLabel().getName()).collect(Collectors.toList());
		return "server with name: '" + this.name + "' has not been found. Existing servers: " + servers.toString();
	}
	
}
