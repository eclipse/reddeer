/*******************************************************************************
 * Copyright (c) 2019 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.test.condition;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.reddeer.common.condition.WaitCondition;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.condition.TreeContainsItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.test.impl.tree.AbstractTreeTest;
import org.junit.Before;
import org.junit.Test;

public class TreeContainsItemTest extends AbstractTreeTest {

	protected org.eclipse.reddeer.swt.api.Tree tree;
	
	@Before
	public void initTree(){
		tree = new DefaultTree();
		createTreeItems(tree.getSWTWidget());
	}
	
	@Test
	public void testTreeContainsItem() {
		WaitCondition cond = new TreeContainsItem(tree, "A");
		new WaitUntil(cond, TimePeriod.SHORT, false);
		assertNotNull(cond.getResult());
		cond = new TreeContainsItem("A");
		new WaitUntil(cond, TimePeriod.SHORT, false);
		assertNotNull(cond.getResult());
	}
	
	@Test
	public void testTreeContainsItemNested() {
		WaitCondition cond = new TreeContainsItem(tree, "A", "AA", "AAA");
		new WaitUntil(cond, TimePeriod.SHORT, false);
		assertNotNull(cond.getResult());
		cond = new TreeContainsItem("A", "AA", "AAA");
		new WaitUntil(cond, TimePeriod.SHORT, false);
		assertNotNull(cond.getResult());
	}
	
	@Test
	public void testTreeContainsItemInvalid() {
		WaitCondition cond = new TreeContainsItem(tree, "XXX");
		new WaitUntil(cond, TimePeriod.SHORT, false);
		assertNull(cond.getResult());
		cond = new TreeContainsItem("XXX");
		new WaitUntil(cond, TimePeriod.SHORT, false);
		assertNull(cond.getResult());
	}
	
	@Test
	public void testTreeContainsItemSecondLevel() {
		WaitCondition cond = new TreeContainsItem(tree, "AA");
		new WaitUntil(cond, TimePeriod.SHORT, false);
		assertNull(cond.getResult());
	}
	
	@Test(expected=WaitTimeoutExpiredException.class)
	public void testTreeContainsItemFailNested() {
		WaitCondition cond = new TreeContainsItem(tree, "A", "XXX");
		new WaitUntil(cond, TimePeriod.SHORT);
	}
	
	@Test(expected=WaitTimeoutExpiredException.class)
	public void testTreeContainsItemNullTree() {
		WaitCondition cond = new TreeContainsItem((DefaultTree)null, "A", "AA", "AAA");
		new WaitUntil(cond, TimePeriod.SHORT);
	}
	
	@Test(expected=WaitTimeoutExpiredException.class)
	public void testTreeContainsItemNullItem() {
		WaitCondition cond = new TreeContainsItem(tree, (String [])null);
		new WaitUntil(cond, TimePeriod.SHORT);
	}
	
}
