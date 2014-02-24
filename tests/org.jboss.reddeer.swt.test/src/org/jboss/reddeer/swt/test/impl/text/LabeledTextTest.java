package org.jboss.reddeer.swt.test.impl.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Test;

public class LabeledTextTest extends RedDeerTest{
	
	
	private int modifiedCount = 0;
	
	@Override
	public void setUp() {
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
		modifiedCount = 0;
	}
	
	private void createControls(Shell shell){
		Label swtLabel= new Label(shell, SWT.BORDER);
		swtLabel.setText("Test label");
		swtLabel.setSize(100,30);
		swtLabel.setLocation(100, 50);
		Label swtLabelIcon= new Label(shell, SWT.BORDER);
		swtLabelIcon.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		swtLabelIcon.setSize(100,30);
		swtLabelIcon.setLocation(100, 100);
		Text swtText = new Text(shell, SWT.LEFT);
		swtText.setText("Test text");
		swtText.setSize(100,30);
		swtText.setLocation(100,150);
		
		final Text swtTextStatus= new Text(shell, SWT.BORDER);
		swtTextStatus.setText("Status");
		swtTextStatus.setSize(100,30);
		swtTextStatus.setLocation(100, 200);

		swtText.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				swtTextStatus.setText("focusLost");
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				swtTextStatus.setText("focusGained");
			}
		});
		
		Label swtLabel1= new Label(shell, SWT.BORDER);
		swtLabel1.setText("Test label1");
		swtLabel1.setSize(100,30);
		swtLabel1.setLocation(250, 50);
		Label swtLabelIcon1= new Label(shell, SWT.BORDER);
		swtLabelIcon1.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		swtLabelIcon1.setSize(100,30);
		swtLabelIcon1.setLocation(250, 100);
		Text swtText1 = new Text(shell, SWT.LEFT);
		swtText1.setText("Test text1");
		swtText1.setSize(100,30);
		swtText1.setLocation(250,150);
		swtText1.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				modifiedCount++;
			}
		});
		
		Label swtLabel2= new Label(shell, SWT.BORDER);
		swtLabel2.setText("Test label2");
		swtLabel2.setSize(100,30);
		swtLabel2.setLocation(400, 50);
		Label swtLabelIcon2= new Label(shell, SWT.BORDER);
		swtLabelIcon2.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		swtLabelIcon2.setSize(100,30);
		swtLabelIcon2.setLocation(400, 100);
		Label swtLabelIcon3= new Label(shell, SWT.BORDER);
		swtLabelIcon3.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
		swtLabelIcon3.setSize(100,30);
		swtLabelIcon3.setLocation(400, 150);
		Text swtText2 = new Text(shell, SWT.LEFT);
		swtText2.setText("Test text2");
		swtText2.setSize(100,30);
		swtText2.setLocation(400,200);
		swtText2.setEditable(false);
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
	}
	
	@Test
	public void findLabeledTextWithIcon(){
		new DefaultShell("Testing shell");
		assertTrue(new LabeledText("Test label").getText().equals("Test text"));
	}
	
	@Test
	public void findLabeledTextWithTwoIcons(){
		new DefaultShell("Testing shell");
		assertTrue(new LabeledText("Test label2").getText().equals("Test text2"));
	}
	
	@Test
	public void findLabeledTextWithoutIcon(){
		new DefaultShell("Testing shell");
		assertTrue(new LabeledText("Test label1").getText().equals("Test text1"));
	}
	
	@Test
	public void setFocusTest() {
		new DefaultShell("Testing shell");
		DefaultText text = new DefaultText(1);
		new LabeledText("Test label");
		assertEquals("focusGained", text.getText());
		new LabeledText("Test label1");
		assertEquals("focusLost", text.getText());
		new LabeledText("Test label");
		assertEquals("focusGained", text.getText());
	}
	
	@Test
	public void setTextTest() {
		new DefaultShell("Testing shell");
		new LabeledText("Test label1").setText("funny text");
		assertEquals("funny text", new LabeledText("Test label1").getText());
		assertEquals(1, modifiedCount);
	}
	
	@Test
	public void typeTextTest(){
		new DefaultShell("Testing shell");
		new LabeledText("Test label1").typeText("not so funny text");
		assertEquals("not so funny text", new LabeledText("Test label1").getText());
		assertEquals(18, modifiedCount);
	}

	@Test(expected = SWTLayerException.class)
	public void setNonEditableTextTest() {
		new DefaultShell("Testing shell");
		new LabeledText("Test label2").setText("funny text");
	}
}
