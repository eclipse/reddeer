package org.jboss.reddeer.swt.test.impl.button;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.impl.button.BackButton;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.button.NextButton;
import org.jboss.reddeer.swt.impl.button.NoButton;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.YesButton;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.util.Display;
import org.junit.Test;

public class PredefinedButtonTest extends SWTLayerTestCase {
	private SelectionListener selectionListener;
	private Text selectionText = null;
	private static int ROW_COUNT = 4;
	private String[] OK_BUTTON = { "&OK", "Ok", "ok", "oK" };
	private String[] CANCEL_BUTTON = { "&Cancel", "CANCEL", "cancel", "cANCEL" };
	private String[] YES_BUTTON = { "&Yes", "YES", "yes", "yES" };
	private String[] NO_BUTTON = { "&No", "NO", "no", "no" };
	private String[] NEXT_BUTTON = { "&Next >", "NEXT", "next", "nEXT" };
	private String[] BACK_BUTTON = { "< &Back", "BACK", "back", "bACK" };
	private String[] FINISH_BUTTON = { "&Finish", "FINISH", "finish", "fINISH" };

	private List<org.eclipse.swt.widgets.Button> buttons = new ArrayList<org.eclipse.swt.widgets.Button>();;

	@Override
	protected void createControls(Shell shell) {

		shell.setLayout(new GridLayout(ROW_COUNT, true));

		this.selectionListener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				org.eclipse.swt.widgets.Button selectedButton = (org.eclipse.swt.widgets.Button) arg0.widget;
				getSelectionText().setText(selectedButton.getData().toString());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
			}
		};

		createButtons(OK_BUTTON, SWT.PUSH, shell);
		createButtons(CANCEL_BUTTON, SWT.PUSH, shell);
		createButtons(YES_BUTTON, SWT.PUSH, shell);
		createButtons(NO_BUTTON, SWT.PUSH, shell);
		createButtons(NEXT_BUTTON, SWT.PUSH, shell);
		createButtons(BACK_BUTTON, SWT.PUSH, shell);
		createButtons(FINISH_BUTTON, SWT.PUSH, shell);

		org.eclipse.swt.widgets.Text txSelection = new org.eclipse.swt.widgets.Text(
				shell, SWT.BORDER);
		txSelection.setText("<text of selected button>");
	}

	private Text getSelectionText() {
		if (this.selectionText == null) {
			this.selectionText = new DefaultText(0);
		}
		return this.selectionText;
	}

	private void createButtons(String[] text, int style, Shell shell) {
		for (int i = 0; i < ROW_COUNT; i++) {
			org.eclipse.swt.widgets.Button button = new org.eclipse.swt.widgets.Button(
					shell, style);

			button.setText(text[i]);
			button.setToolTipText(text[i]);
			button.setData(text[i]);
			button.addSelectionListener(selectionListener);

			buttons.add(button);
		}
	}

	/**
	 * Checks tooltip text and selection listener invocation for button
	 * 
	 * @param expectedText
	 * @param button
	 */
	@Test(expected = CoreLayerException.class)
	public void testPrescribedButtonsNotFound() {
		visibleButtons(false);
		new OkButton();
	}

	@Test
	public void testPresribedButtonsFound() {
		visibleButtons(true);

		OkButton okButton = new OkButton();
		okButton.click();
		assertEquals(getSelectionText().getText(), OK_BUTTON[0]);

		CancelButton cancelButton = new CancelButton();
		cancelButton.click();
		assertEquals(getSelectionText().getText(), CANCEL_BUTTON[0]);

		NextButton nextButton = new NextButton();
		nextButton.click();
		assertEquals(getSelectionText().getText(), NEXT_BUTTON[0]);

		BackButton backButton = new BackButton();
		backButton.click();
		assertEquals(getSelectionText().getText(), BACK_BUTTON[0]);

		YesButton yesButton = new YesButton();
		yesButton.click();
		assertEquals(getSelectionText().getText(), YES_BUTTON[0]);

		NoButton noButton = new NoButton();
		noButton.click();
		assertEquals(getSelectionText().getText(), NO_BUTTON[0]);
		
		FinishButton finishButton = new FinishButton();
		finishButton.click();
		assertEquals(getSelectionText().getText(), FINISH_BUTTON[0]);

	}

	private void visibleButtons(final boolean enable) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				for (org.eclipse.swt.widgets.Button b : buttons) {
					b.setVisible(enable);
				}

			}
		});

	}

}
