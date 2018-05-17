/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.rse.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.rse.ui.view.System;
import org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection.RSENewConnectionWizardSelectionPage.SystemType;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
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