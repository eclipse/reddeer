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
package org.jboss.reddeer.eclipse.test.rse.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.eclipse.rse.ui.view.System;
import org.jboss.reddeer.eclipse.rse.ui.wizards.newconnection.RSENewConnectionWizardSelectionPage.SystemType;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.Before;
import org.junit.Test;

@OpenPerspective(JavaPerspective.class)
public class SystemTest extends SystemViewTestCase {
	
	private static final String SYSTEM_1 = "system1";
	private static final String SYSTEM_2 = "system2";
	
	private System system1;
	
	@Before
	public void setUp() {
		createSystem("127.0.0.1", SYSTEM_1, SystemType.SSH_ONLY);
		createSystem("localhost", SYSTEM_2, SystemType.SSH_ONLY);
		remoteSystemView.open();
		system1 = remoteSystemView.getSystem(SYSTEM_1);
	}

	@Test(expected=CoreLayerException.class)
	public void disconnect_disconnected(){
		system1.disconnect();
	}
	
	@Test
	public void isConnected_false(){
		assertFalse(system1.isConnected());
	}
	
	@Test
	public void delete(){
		List<System> systems = remoteSystemView.getSystems();
		assertThat(systems.size(), is(3)); //local + 2 defined in setUp
		system1.delete();
		
		systems = remoteSystemView.getSystems();
		assertThat(systems.size(), is(2));
		assertThat(systems.get(1).getLabel(), is(SYSTEM_2));
	}
	
}