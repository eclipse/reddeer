package org.jboss.reddeer.swt.test.impl.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.swt.test.utils.LabelTestUtils;
import org.jboss.reddeer.swt.test.utils.LayoutTestUtils;
import org.jboss.reddeer.swt.test.utils.TextTestUtils;
import org.junit.Before;
import org.junit.Test;

public class LabeledTextTest extends SWTLayerTestCase {
	
	private int modifiedCount = 0;
	
	@Before
	public void setupModifiedCount() {
		modifiedCount = 0;
	}
	
	@Override
	protected void createControls(Shell shell){
		LabelTestUtils.createLabel(shell, "Test label");
		LabelTestUtils.createLabel(shell, "", PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		
		Text swtText = TextTestUtils.createText(shell, "Test text");
		final Text swtTextStatus= TextTestUtils.createText(shell, "Status");
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
		
		LabelTestUtils.createLabel(shell, "Test label1");
		LabelTestUtils.createLabel(shell, "", PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		
		Text swtText1 = TextTestUtils.createText(shell, "Test text1");
		swtText1.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				modifiedCount++;
			}
		});
		
		LabelTestUtils.createLabel(shell, "Test label2");
		LabelTestUtils.createLabel(shell, "", PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		LabelTestUtils.createLabel(shell, "", PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK));

		Text swtText2 = TextTestUtils.createText(shell, "Test text2");
		swtText2.setEditable(false);
		LabelTestUtils.createLabel(shell, "Test label3");
		Text swtText3 = TextTestUtils.createText(shell, "");
		swtText3.setMessage("message");

		LabelTestUtils.createCLabel(shell, "Test clabel");
		TextTestUtils.createText(shell, "Test text4");
		
		Composite formContainer = LayoutTestUtils.createFlatFormComposite(shell);

		Text text1 = TextTestUtils.createText(formContainer, "");
		text1.setText("formtext");
		text1.setLayoutData(LayoutTestUtils.entryLayoutData());
		Label label1 = LabelTestUtils.createLabel(formContainer, "");
		label1.setText("FormLabel");
		label1.setLayoutData(LayoutTestUtils.labelLayoutData(text1));

		Text text2 = TextTestUtils.createText(formContainer, "");
		text2.setText("cformtext");
		text2.setLayoutData(LayoutTestUtils.entryLayoutData(text1));
		CLabel label2 = LabelTestUtils.createCLabel(formContainer, "");
		label2.setText("FormCLabel");
		label2.setLayoutData(LayoutTestUtils.labelLayoutData(text2));
	}
	
	@Test
	public void findLabeledTextWithIcon(){
		new DefaultShell(SHELL_TITLE);
		assertTrue(new LabeledText("Test label").getText().equals("Test text"));
	}
	
	@Test
	public void findLabeledTextWithTwoIcons(){
		new DefaultShell(SHELL_TITLE);
		assertTrue(new LabeledText("Test label2").getText().equals("Test text2"));
	}
	
	@Test
	public void findLabeledTextWithFormLabel(){
		new DefaultShell(SHELL_TITLE);
		assertEquals("formtext", new LabeledText("FormLabel").getText());
	}

	@Test
	public void findLabeledTextWithFormCLabel(){
		new DefaultShell(SHELL_TITLE);
		assertEquals("cformtext", new LabeledText("FormCLabel").getText());
	}
	
	@Test
	public void findLabeledTextWithoutIcon(){
		new DefaultShell(SHELL_TITLE);
		assertTrue(new LabeledText("Test label1").getText().equals("Test text1"));
	}
	
	@Test
	public void findLabeledTextWithCLabel(){
		new DefaultShell(SHELL_TITLE);
		assertTrue(new LabeledText("Test clabel").getText().equals("Test text4"));
	}

	@Test
	public void getTextMessage(){
		new DefaultShell(SHELL_TITLE);
		assertTrue(new LabeledText("Test label3").getMessage().equals("message"));
	}
	
	@Test
	public void setFocusTest() {
		new DefaultShell(SHELL_TITLE);
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
		new DefaultShell(SHELL_TITLE);
		new LabeledText("Test label1").setText("funny text");
		assertEquals("funny text", new LabeledText("Test label1").getText());
		assertEquals(1, modifiedCount);
	}
	
	@Test
	public void typeTextTest(){
		new DefaultShell(SHELL_TITLE);
		new LabeledText("Test label1").typeText("not so funny text");
		assertEquals("not so funny text", new LabeledText("Test label1").getText());
		assertEquals(18, modifiedCount);
	}

	@Test(expected = CoreLayerException.class)
	public void setNonEditableTextTest() {
		new DefaultShell(SHELL_TITLE);
		new LabeledText("Test label2").setText("funny text");
	}
}
