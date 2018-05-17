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
package org.eclipse.reddeer.swt.test.impl.link;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.swt.impl.link.DefaultLink;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.eclipse.reddeer.swt.test.utils.TextTestUtils;
import org.junit.Test;

public class DefaultLinkTest extends SWTLayerTestCase {
	
	@Override
	protected void createControls(Shell parent){
		final Text text = TextTestUtils.createText(parent, "");

		Link link1 = LinkTestUtils.createLink(parent, "This is a <a href=\"test1\">link1</a>");
		link1.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(e.text);
			}

		});
		
		Link link2 =  LinkTestUtils.createLink(parent, "This is another <A>link2</A> with two <a href=\"test2\">links</a>");
		link2.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(e.text);
			}

		});
		Link link3 = LinkTestUtils.createLink(parent, "Link with same texts <a href=\"same1\">same</a><a href=\"same2\">same</a>");
		link3.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(e.text);
			}
		});
	}
	
	
	@Test
	public void stringConstructorTest(){
		org.eclipse.reddeer.swt.api.Link link1 = new DefaultLink("This is a link1");
		org.eclipse.reddeer.swt.api.Link link2 = new DefaultLink("This is another link2 with two links");
		assertNotNull(link1);
		assertNotNull(link2);
	}
	
	@Test
	public void getTextTest(){
		String text1 = new DefaultLink().getText();
		String text2 = new DefaultLink(1).getText();
		assertEquals("This is a link1", text1);
		assertEquals("This is another link2 with two links", text2);
	}
	
	@Test
	public void getHrefTextsTest(){
		List<String> text1 = new DefaultLink().getAnchorTexts();
		List<String> text2 = new DefaultLink(1).getAnchorTexts();
		assertEquals(1, text1.size());
		assertEquals(2, text2.size());
		assertEquals("link1", text1.get(0));
		assertEquals("link2", text2.get(0));
		assertEquals("links", text2.get(1));
	}
	
	@Test
	public void clickTest(){
		org.eclipse.reddeer.swt.api.Text text = new DefaultText(0);
		assertEquals("", text.getText());
		new DefaultLink().click("link1");
		assertEquals("test1", text.getText());
		new DefaultLink(1).click("link2");
		assertEquals("link2", text.getText());
		new DefaultLink(1).click("links");
		assertEquals("test2", text.getText());
		new DefaultLink().click();
		assertEquals("test1", text.getText());
		new DefaultLink(1).click();
		assertEquals("link2", text.getText());
		new DefaultLink(1).click(1);
		assertEquals("test2", text.getText());
	}
	
	@Test(expected=CoreLayerException.class)
	public void clickWrongTextTest(){
		new DefaultLink().click("wrongText");
	}
	
	@Test(expected=CoreLayerException.class)
	public void clickWrongIndexTest(){
		new DefaultLink().click(1);
	}
	
	@Test
	public void sameAnchorTextTest(){
		org.eclipse.reddeer.swt.api.Text text = new DefaultText(0);
		DefaultLink link = new DefaultLink(2);
		link.click("same", 0);
		assertEquals("same1", text.getText());
		link.click("same", 1);
		assertEquals("same2", text.getText());
	}

}
