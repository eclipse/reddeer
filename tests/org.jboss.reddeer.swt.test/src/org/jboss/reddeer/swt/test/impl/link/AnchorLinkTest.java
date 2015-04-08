package org.jboss.reddeer.swt.test.impl.link;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.swt.impl.link.AnchorLink;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.swt.test.utils.TextTestUtils;
import org.junit.Test;

public class AnchorLinkTest extends SWTLayerTestCase {
	
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
		org.jboss.reddeer.swt.api.Link link1 = new AnchorLink("link1");
		org.jboss.reddeer.swt.api.Link link2 = new AnchorLink("link2");
		assertNotNull(link1);
		assertNotNull(link2);
	}
	
	@Test
	public void getTextTest(){
		String text1 = new AnchorLink("link1").getText();
		String text2 = new AnchorLink("link2").getText();
		assertEquals("This is a link1", text1);
		assertEquals("This is another link2 with two links", text2);
	}
	
	@Test
	public void getHrefTextsTest(){
		List<String> text1 = new AnchorLink("link1").getAnchorTexts();
		List<String> text2 = new AnchorLink("link2").getAnchorTexts();
		assertEquals(1, text1.size());
		assertEquals(2, text2.size());
		assertEquals("link1", text1.get(0));
		assertEquals("link2", text2.get(0));
		assertEquals("links", text2.get(1));
	}
	
	@Test
	public void clickTest(){
		org.jboss.reddeer.swt.api.Text text = new DefaultText(0);
		assertEquals("", text.getText());
		new AnchorLink("link1").click("link1");
		assertEquals("test1", text.getText());
		new AnchorLink("link2").click("link2");
		assertEquals("link2", text.getText());
		new AnchorLink("link2").click("links");
		assertEquals("test2", text.getText());
		new AnchorLink("link1").click();
		assertEquals("test1", text.getText());
		new AnchorLink("links").click();
		assertEquals("link2", text.getText());
		new AnchorLink("links").click(1);
		assertEquals("test2", text.getText());
	}
	
	@Test(expected=CoreLayerException.class)
	public void clickWrongTextTest(){
		new AnchorLink("link1").click("wrongText");
	}
	
	@Test(expected=CoreLayerException.class)
	public void clickWrongIndexTest(){
		new AnchorLink("link1").click(1);
	}
	
	@Test
	public void sameAnchorTextTest(){
		org.jboss.reddeer.swt.api.Text text = new DefaultText(0);
		AnchorLink link = new AnchorLink("same");
		link.click("same", 0);
		assertEquals("same1", text.getText());
		link.click("same", 1);
		assertEquals("same2", text.getText());
	}

}
