package org.jboss.reddeer.swt.impl.button;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.handler.WidgetHandler;
/**
 * RadioButton is button implementation that can be selected
 * @author jjankovi
 *
 */
public class RadioButton extends AbstractButton {
	protected final Logger log = Logger.getLogger(this.getClass());
	/**
	 * Creates Radio button
	 */
	public RadioButton() {
		this(0);
	}
	
	/**
	 * Creates Radio button with given text
	 * @param text
	 */
	public RadioButton(String text) {
		this(0, text);
	}
	/**
	 * Creates Radio button with given index
	 * @param text
	 */
	public RadioButton(int index) {
		this(index, null);
	}
	
	/**
	 * Creates Radio button with given index and label
	 * @param index of button
	 * @param label of button
	 */
	public RadioButton(int index, String text) {
		super(index,text,SWT.RADIO);
	}
	/**
	 * Returns true when Radio Button is selected	
	 * @return
	 */
	public boolean isSelected() {
		return WidgetHandler.getInstance().isSelected(swtButton);
	}
	/**
	 * Sets Radio Button to state 'checked'
	 * 
	 * @param checked
	 */
	public void toggle(boolean checked){
		if (checked){
			if (isSelected()) {
				log.debug("Radio Button already checked");
				return;
			}else{
				log.info("Checking Radio Button " + getText());
				click();
			}
		}else{
			if (isSelected()) {
				log.info("Unchecking Radio Button " + getText());
				click();
			}else{
				log.debug("Radio Button already unchecked");
				return;
			}
		}
	}
}