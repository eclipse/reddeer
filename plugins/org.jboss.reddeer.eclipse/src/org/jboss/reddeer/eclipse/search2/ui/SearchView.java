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
package org.jboss.reddeer.eclipse.search2.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.link.DefaultLink;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Represents Search view 
 * 
 * @author odockal
 *
 */
public class SearchView extends WorkbenchView {

	/**
	 * Constructs Search view object
	 */
	public SearchView() {
		super("Search");
	}
	
	/**
	 * Says whether searching of referenced item was successful or not.
	 * @return true if results are not present in Search view, false otherwise
	 */
	public boolean isSearchEmpty() {
		activate();
		try {
			new DefaultLink(new WithTextMatcher(
					"No search results available. Start a search from the <a>search dialog</a>..."));
			return true;
		} catch (CoreLayerException e){
			return false;
		}
	}

	/**
	 * Returns all search results from Search view as a list of SearchResult objects with default search option setup
	 * @return List of SearchResult objects
	 */
	public List<SearchResult> getSearchResults() {
		List<SearchResult> results = new ArrayList<SearchResult>();
		activate();
		if (!isSearchEmpty()){
			DefaultTree tree = new DefaultTree();
			for (TreeItem item : tree.getAllItems()) {
				results.add(new SearchResult(item));
			}
		}
		return results;
	}
}
