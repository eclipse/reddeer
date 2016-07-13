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
package org.jboss.reddeer.eclipse.test.ui.search;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.core.handler.WorkbenchPartHandler;
import org.jboss.reddeer.eclipse.ui.search.SearchView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class SearchViewTest {
	
	private SearchView searchView;
	
	@Before
	public void setUp() {
		searchView = new SearchView();
	}
	
	@After
	public void tearDown() {
		if (searchView != null) {
			if (searchView.isOpened()){
				searchView.close();
			}
		}
	}
	
	@Test
	public void testOpen() {
		searchView.open();
		String activeViewTitle = WorkbenchPartHandler.getInstance().getActiveViewTitle();
		assertTrue("Active view has to be Search but is"
				+ activeViewTitle, activeViewTitle.equals("Search"));
	}
	
	@Test
	public void testIsEmpty() {
		searchView.open();
		searchView.activate();
		assertTrue("SearchView results are empty when nothing is foudn. ", searchView.isSearchEmpty());
	}
	
	@Test
	public void testReturnResults() {
		searchView.open();
		searchView.activate();
		assertTrue("Empty search results should return empty list.", searchView.getSearchResults().isEmpty());
	}
}
