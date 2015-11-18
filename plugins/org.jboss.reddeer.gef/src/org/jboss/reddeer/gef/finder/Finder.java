package org.jboss.reddeer.gef.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.hamcrest.Matcher;

/**
 * 
 * @author apodhrad
 * 
 * @param T
 *            object to find
 */
public abstract class Finder<T> {

	/**
	 * Find.
	 *
	 * @param parent the parent
	 * @param matcher the matcher
	 * @return the list
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
	 * Gets the children.
	 *
	 * @param child the child
	 * @return the children
	 */
	public abstract List<T> getChildren(T child);
}
