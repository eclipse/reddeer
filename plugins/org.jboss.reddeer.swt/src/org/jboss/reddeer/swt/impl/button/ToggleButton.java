package org.jboss.reddeer.swt.impl.button;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.handler.WidgetHandler;
/**
 * Toggle Button implementation
 * @author Vlado Pakan
 *
 */
public class ToggleButton extends AbstractButton {

	private static final Logger log = Logger.getLogger(ToggleButton.class);
		
	/**
	 * Creates Toggle button
	 */
	public ToggleButton() {
		this(0);
	}
	
	/**
	 * Creates Toggle button with given text
	 * @param text
	 */
	public ToggleButton(String text) {
		this(0, text);
	}
	/**
	 * Creates Toggle button with given index
	 * @param text
	 */
	public ToggleButton(int index) {
		this(index, null);
	}
	
	/**
	 * Creates Toggle button with given index and label
	 * @param index of button
	 * @param label of button
	 */
	public ToggleButton(int index, String text) {
		super(index,text, SWT.TOGGLE);
	}
    /**
     * Returns true when Toggle Button is selected
     * @return
     */
	public boolean isSelected() {
		return WidgetHandler.getInstance().isSelected(swtButton);
	}
	/**
	 * Sets Toggle Button to state 'checked'
	 * 
	 * @param checked
	 */
	public void toggle(boolean checked){
		if (checked){
			if (isSelected()) {
				log.debug("Toggle Button already checked");
				return;
			}else{
				log.info("Checking Toggle Button " + getText());
				click();
			}
		}else{
			if (isSelected()) {
				log.info("Unchecking Toggle Button " + getText());
				click();
			}else{
				log.debug("Toggle Button already unchecked");
				return;
			}
		}
	}
}