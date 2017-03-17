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

import org.jboss.reddeer.swt.api.TreeItem;

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
