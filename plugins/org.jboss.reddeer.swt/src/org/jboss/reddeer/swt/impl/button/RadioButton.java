package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.handler.ButtonHandler;
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
		this(0);
	}
	
	/**
	 * Creates Radio button inside given composite
	 * @param referencedComposite
	 */
	public RadioButton(ReferencedComposite referencedComposite) {
		this(referencedComposite,0);
	}
	
	/**
	 * Creates Radio button with given text
	 * @param text
	 */
	public RadioButton(String text) {
		this(null, 0, text);
	}
	
	/**
	 * Creates Radio button with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public RadioButton(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, text);
	}
	
	/**
	 * Creates Radio button with given index
	 * @param text
	 */
	public RadioButton(int index) {
		this(null, index, null);
	}
	
	/**
	 * Creates Radio button with given index inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public RadioButton(ReferencedComposite referencedComposite, int index) {
		this(referencedComposite, index, null);
	}
	
	/**
	 * Creates Radio button with given index and label
	 * @param index of button
	 * @param label of button
	 */
	public RadioButton(int index, String text) {
		super(null, index,text,SWT.RADIO);
	}
	
	/**
	 * Creates Radio button with given index and label inside given composite
	 * @param referencedComposite
	 * @param index of button
	 * @param label of button
	 */
	public RadioButton(ReferencedComposite referencedComposite, int index, String text) {
		super(referencedComposite, index,text,SWT.RADIO);
	}
	/**
	 * Returns true when Radio Button is selected	
	 * @return
	 */
	public boolean isSelected() {
		return ButtonHandler.getInstance().isSelected(swtButton);
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