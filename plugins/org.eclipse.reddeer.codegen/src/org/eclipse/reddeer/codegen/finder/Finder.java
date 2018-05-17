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
package org.eclipse.reddeer.codegen.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.hamcrest.Matcher;

/**
 * Abstract depth-first search implementation for finding objects.
 *
 * @param <T>
 *            object to find
 */
public abstract class Finder<T> {

	/**
	 * Returns all objects discovered from parent and which matches a given matcher.
	 * 
	 * @param parent
	 *            parent node
	 * @param matcher
	 *            matcher
	 * @return all objects discovered from parent and which matches a given matcher
	 */
	public List<T> find(T parent, Matcher<?> matcher) {
		List<T> list = new ArrayList<T>();
		Stack<T> stack = new Stack<T>();
		// Initial push
		stack.push(parent);
		// Depth first search
		while (!stack.isEmpty()) {
			// Pop figure
			T child = stack.pop();
			// If null then continue
			if (child == null) {
				continue;
			}
			// Does it matches?
			if (matcher.matches(child)) {
				list.add(child);
			}
			// Push another children
			for (T t : getChildren(child)) {
				stack.push(t);
			}
		}
		return list;
	}

	/**
	 * Implement how we can get children from a given node.
	 * 
	 * @param child
	 *            node
	 * @return children of a given node
	 */
	public abstract List<T> getChildren(T child);
}
