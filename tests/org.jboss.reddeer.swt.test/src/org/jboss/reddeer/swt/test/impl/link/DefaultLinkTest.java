package org.jboss.reddeer.swt.test.impl.link;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.link.DefaultLink;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefaultLinkTest extends RedDeerTest {
	
	@Before
	public void setup(){
		super.setUp();
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell(org.eclipse.swt.widgets.Display.getDefault());
				shell.setText("Testing shell");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});
	}
	
	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.swt.
						util.Display.getDisplay().getShells()) {
					if (shell.getText().equals("Testing shell")) {
						shell.dispose();
						break;
					}
				}
			}
		});
		new WaitWhile(new ShellWithTextIsActive("Testing shell"));
	}
	
	private void createControls(Shell parent){
		final Text text = new Text(parent, SWT.BORDER);
		text.setSize(100,30);
		text.setLocation(100,50);
		text.setText("");
		Link link1 = new Link(parent, SWT.BORDER);
		link1.setSize(100, 30);
		link1.setLocation(100, 100);
		link1.setText("This is a <a href=\"test1\">link1</a>");
		link1.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(e.text);
			}

		});
		Link link2 = new Link(parent, SWT.BORDER);
		link2.setSize(100, 30);
		link2.setLocation(100, 150);
		link2.setText("This is another <A>link2</A> with two <a href=\"test2\">links</a>");
		link2.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(e.text);
			}

		});
		Link link3 = new Link(parent, SWT.BORDER);
		link3.setSize(100, 30);
		link3.setLocation(100,200);
		link3.setText("Link with same texts <a href=\"same1\">same</a><a href=\"same2\">same</a>");
		link3.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(e.text);
			}

		});
	}
	
	
	@Test
	public void stringConstructorTest(){
		org.jboss.reddeer.swt.api.Link link1 = new DefaultLink("This is a link1");
		org.jboss.reddeer.swt.api.Link link2 = new DefaultLink("This is another link2 with two links");
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
		org.jboss.reddeer.swt.api.Text text = new DefaultText(0);
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
	
	@Test(expected=SWTLayerException.class)
	public void clickWrongTextTest(){
		new DefaultLink().click("wrongText");
	}
	
	@Test(expected=SWTLayerException.class)
	public void clickWrongIndexTest(){
		new DefaultLink().click(1);
	}
	
	@Test
	public void sameAnchorTextTest(){
		org.jboss.reddeer.swt.api.Text text = new DefaultText(0);
		DefaultLink link = new DefaultLink(2);
		link.click("same", 0);
		assertEquals("same1", text.getText());
		link.click("same", 1);
		assertEquals("same2", text.getText());
	}

}
