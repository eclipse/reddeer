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
package org.eclipse.reddeer.eclipse.search2.ui;

import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Class wraps basic search result item represented by swt layer object of TreeItem
 * @author odockal
 *
 */
public class SearchResult {
	
	private TreeItem searchResult;
	
	/**
	 * Constructor of search result
	 * @param item search result tree item
	 */
	public SearchResult(TreeItem item) {
		this.searchResult = item;
	}
	
	/**
	 * Returns search result as a text
	 * @return String representation of search result
	 */
	public String getSearchResultText() {
		return searchResult.getText();
	}
	
	/**
	 * Returns path of the search result item
	 * @return path in string array
	 */
	public String[] getSearchResultPath() {
		return searchResult.getPath();
	}
}
