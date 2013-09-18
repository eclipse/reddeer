package org.jboss.reddeer.swt.test.impl.clabel;

import static org.junit.Assert.assertTrue;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Test;
/**
 * Tests CLabel implementation
 * @author vlado pakan
 *
 */
public class CLabelTest extends RedDeerTest{
	private static final String CLABEL_PREFIX = "CLabel:";
	private static final String CLABEL_TOOLTIP_PREFIX = "CLabel Tooltip Text:";
	private static final int[] CLABEL_ALIGNMENT = new int[]{SWT.LEFT,SWT.CENTER,SWT.RIGHT};
	private Image[] cLabelImages;
	@Override
	public void setUp() {
		super.setUp();
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell( Display.getDisplay());
				shell.setText("Testing shell");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});
	}
	
	private void createControls(Shell shell){
		shell.setLayout(new FillLayout());
		cLabelImages = new Image[]{null, Display.getDisplay().getSystemImage(SWT.ICON_QUESTION),null};
		for (int index = 0 ; index < CLabelTest.CLABEL_ALIGNMENT.length ; index++){
			org.eclipse.swt.custom.CLabel cLabel = new org.eclipse.swt.custom.CLabel(shell, SWT.SHADOW_IN);
			cLabel.setText(CLabelTest.CLABEL_PREFIX + index);
			cLabel.setToolTipText(CLabelTest.CLABEL_TOOLTIP_PREFIX + index);
			cLabel.setAlignment(CLabelTest.CLABEL_ALIGNMENT[index]);
			cLabel.setImage(cLabelImages[index]);
		}
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
	public void findCLabelByIndex(){
		int index = 1;
		CLabel cLabel = new DefaultCLabel(index);
		assertTrue("Wrong cLabel widget was found",
			cLabel.getText().equals(CLabelTest.CLABEL_PREFIX + index));
	}
	@Test
	public void findCLabelByText(){
		int index = 1;
		CLabel cLabel = new DefaultCLabel(CLabelTest.CLABEL_PREFIX + index);
		assertTrue("Wrong cLabel widget was found",
			cLabel.getText().equals(CLabelTest.CLABEL_PREFIX + index));
	}
	@Test(expected=SWTLayerException.class)
	public void findNonExistingCLabelByIndex(){
		new DefaultCLabel(11);
	}
	@Test
	public void getTooltipText(){
		int index = 1;
		String tooltip = new DefaultCLabel(index).getTooltipText();
		assertTrue("CLabel has wrong tooltip: " + tooltip +
				" expected was " + CLabelTest.CLABEL_TOOLTIP_PREFIX + index,
			tooltip.equals(CLabelTest.CLABEL_TOOLTIP_PREFIX + index));
	}
	@Test
	public void getAlignment(){
		int index = 0;
		int alignmemt = new DefaultCLabel().getAlignment();
		assertTrue("CLabel has wrong Alignment: " + alignmemt +
				" but expected is " + CLabelTest.CLABEL_ALIGNMENT[index],
			alignmemt == CLabelTest.CLABEL_ALIGNMENT[index]);
	}
	@Test
	public void hasImage(){
		for (int index = 0 ; index < CLabelTest.CLABEL_ALIGNMENT.length ; index++){
			CLabel cLabel = new DefaultCLabel(index);
			boolean hasImage = cLabel.hasImage();
			assertTrue("Method hasImage() for cLabel " + cLabel.getText() +
					" returned " + hasImage +
					" but has to return " + !hasImage,
				hasImage == (cLabelImages[index] != null));
		}
	}
	@Test(expected=SWTLayerException.class)
	public void findNonExistingCLabelByText(){
		new DefaultCLabel("NON_ESITING_@##$_TEXT");
	}
}
