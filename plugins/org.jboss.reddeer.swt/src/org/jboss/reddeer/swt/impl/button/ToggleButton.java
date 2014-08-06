package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.handler.ButtonHandler;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
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
	 * Creates Toggle button inside given composite
	 * @param referencedComposite
	 */
	public ToggleButton(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Creates Toggle button with given text
	 * @param text
	 */
	public ToggleButton(String text) {
		this(null, 0, text);
	}
	
	/**
	 * Creates Toggle button with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public ToggleButton(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, text);
	}
	
	/**
	 * Creates Toggle button with given index
	 * @param text
	 */
	public ToggleButton(int index) {
		this(null, index, null);
	}
	
	/**
	 * Creates Toggle button with given index inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public ToggleButton(ReferencedComposite referencedComposite, int index) {
		this(referencedComposite, index, null);
	}
	
	/**
	 * Creates Toggle button with given index and label
	 * @param index of button
	 * @param label of button
	 */
	public ToggleButton(int index, String text) {
		super(null, index,text, SWT.TOGGLE);
	}
	
	/**
	 * Creates Toggle button with given index and label inside given composite
	 * @param referencedComposite
	 * @param index of button
	 * @param label of button
	 */
	public ToggleButton(ReferencedComposite referencedComposite, int index, String text) {
		super(referencedComposite, index,text, SWT.TOGGLE);
	}
    /**
     * Returns true when Toggle Button is selected
     * @return
     */
	public boolean isSelected() {
		return ButtonHandler.getInstance().isSelected(swtButton);
	}
	/**
	 * Sets Toggle Button to state 'checked'
	 * 
	 * @param checked
	 */
	public void toggle(boolean checked){
		if (checked){
			if (isSelected()) {
				log.debug("Toggle Button " + getDescriptiveText() + " already checked, no action performed");
				return;
			}else{
				log.info("Checking Toggle Button " + getDescriptiveText());
				click();
			}
		}else{
			if (isSelected()) {
				log.info("Unchecking Toggle Button " + getDescriptiveText());
				click();
			}else{
				log.debug("Toggle Button " + getDescriptiveText() + " already unchecked, no action performed");
				return;
			}
		}
	}
}