/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.e4.workbench.test.part;

import static org.junit.Assert.*;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.impl.part.DefaultWorkbenchPart;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WorkbenchPartTest {
	
	@Test
	public void testSampleWorkbenchPart(){
		DefaultWorkbenchPart part = new DefaultWorkbenchPart("Sample Part");
		assertEquals("Sample Part",part.getTitle());
		assertNull(part.getTitleImage());
		assertNull(part.getTitleToolTip());
		assertNull(part.getContextMenu());
		assertNotNull(part.getControl());
		assertFalse(part.isDirty());
		
		DefaultWorkbenchPart part2 = new DefaultWorkbenchPart("Sample Part 2");
		assertEquals("Sample Part 2",part2.getTitle());
		assertNotNull(part2.getTitleImage());
		assertEquals("ToolTip2",part2.getTitleToolTip());
		assertNotNull(part.getControl());
		assertFalse(part.isDirty());
	}
	
	@Test
	public void closeWorkbenchPart() {
		DefaultWorkbenchPart part2 = new DefaultWorkbenchPart("Sample Part 2");
		
		try {
			part2.close();
		} finally {
			new DefaultToolItem(new WorkbenchShell(), new WithTextMatcher(new RegexMatcher("Open SamplePart2"))).click();
			new DefaultWorkbenchPart("Sample Part 2");
		}
	}
	
	@Test
	public void activateWorkbenchParts() {
		DefaultWorkbenchPart part = new DefaultWorkbenchPart("Sample Part");
		DefaultWorkbenchPart part2 = new DefaultWorkbenchPart("Sample Part 2");
		part.activate();
		assertTrue(part.isActive());
		assertFalse(part2.isActive());
		
		part2.activate();
		assertTrue(part2.isActive());
		assertFalse(part.isActive());
	}
	
	@Test
	public void getWidgetFromPartWhenOtherPartIsActive() {
		DefaultWorkbenchPart part = new DefaultWorkbenchPart("Sample Part 2");
		part.activate();
		
		try {
			new DefaultTableItem("Sample item 1");
		} catch (RedDeerException e) {
			//not found which is ok because other workbench part is active
			return;
		}
		fail();
	}
	
	@Test
	public void testPartAsReferencedComposite() {
		DefaultWorkbenchPart part = new DefaultWorkbenchPart("Sample Part");
		part.activate();
		new DefaultTableItem(new DefaultTable(part),"Sample item 1");
	}
	
	@Test
	public void getWidgetFromPartWhenOtherShellIsActive() {
		new ShellMenuItem("Help","About");
		new WaitWhile(new ShellIsAvailable("About"));
		
		try {
			new DefaultTableItem("Sample item 1");
		} catch (RedDeerException e) {
			//not found which is ok because About shell is open
			return;
		}
		fail();
		
	}
	
	
	@Test
	public void testDirtyablePart() {
		DefaultWorkbenchPart part = new DefaultWorkbenchPart("Sample Part");
		assertFalse(part.isDirty());
		new DefaultText(part).setText("makePartDirty");
		assertTrue(part.isDirty());
		part.save();
		assertFalse(part.isDirty());
	}
	
}
