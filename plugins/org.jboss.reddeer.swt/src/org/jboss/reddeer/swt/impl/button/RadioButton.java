package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.handler.ButtonHandler;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * RadioButton is button implementation that can be selected
 * @author jjankovi
 *
 */
public class RadioButton extends AbstractButton {
	
	private static final Logger log = Logger.getLogger(RadioButton.class);
	
	/**
	 * Creates Radio button
	 */
	public RadioButton() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * Creates Radio button inside given composite
	 * @param referencedComposite
	 */
	public RadioButton(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Creates Radio button with given text
	 * @param text
	 */
	public RadioButton(String text) {
		this(null, text);
	}
	
	/**
	 * Creates Radio button with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public RadioButton(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}
	
	/**
	 * Radio button that matches given matchers
	 * @param matchers
	 */
	public RadioButton(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Radio button that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public RadioButton(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Creates Radio button with given index that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public RadioButton(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Creates Radio button with given index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public RadioButton(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, SWT.RADIO, matchers);
	}
	
	/**
	 * Creates Radio button with given index and label
	 * @param index of button
	 * @param label of button
	 * @deprecated Since 1.0.0. This is not a standard widget constructor.
	 */
	public RadioButton(int index, String text) {
		super(null, index,text,SWT.RADIO);
	}
	
	/**
	 * Creates Radio button with given index and label inside given composite
	 * @param referencedComposite
	 * @param index of button
	 * @param label of button
	 * @deprecated Since 1.0.0. This is not a standard widget constructor.
	 */
	public RadioButton(ReferencedComposite referencedComposite, int index, String text) {
		super(referencedComposite, index,text,SWT.RADIO);
	}
	/**
	 * Returns true when Radio Button is selected	
	 * @return
	 */
	public boolean isSelected() {
		return ButtonHandler.getInstance().isSelected(swtWidget);
	}
	/**
	 * Sets Radio Button to state 'checked'
	 * 
	 * @param checked
	 */
	public void toggle(boolean checked){
		if (checked){
			if (isSelected()) {
				log.debug("Radio Button " + getDescriptiveText() + " already checked, no action performed");
				return;
			}else{
				log.info("Select radio button " + getDescriptiveText());
				click();
			}
		}else{
			if (isSelected()) {
				log.info("Unchecking Radio Button " + getDescriptiveText());
				click();
			}else{
				log.debug("Radio button " + getDescriptiveText() + " already unselected, no action performed");
				return;
			}
		}
	}
}