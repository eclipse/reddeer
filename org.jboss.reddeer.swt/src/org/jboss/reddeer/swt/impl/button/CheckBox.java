package org.jboss.reddeer.swt.impl.button;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.handler.WidgetHandler;
/**
 * Class represents Button with type Toggle (Checkbox)
 * 
 * @author rhopp
 * 
 */

public class CheckBox extends AbstractButton {

	protected final Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * Default constructor
	 */

	public CheckBox() {
		this(0);
	}
	
	/**
	 * Checkbox with given index
	 * 
	 * @param index
	 */
	
	public CheckBox(int index){
		this(index,"");
	}
	/**
	 * Checkbox with given text
	 * 
	 * @param text
	 */
	public CheckBox(String text) {
		this(0,text);
	}
	/**
	 * Check Box with given index and text
	 * @param index
	 * @param text
	 */
	public CheckBox (int index , String text){
		super(index,text,SWT.CHECK);
	}
	/**
	 * Returns true when Check Box is checked
	 * @return
	 */
	public boolean isChecked() {
		return WidgetHandler.getInstance().isSelected(swtButton);
	}
	
	/**
	 * Sets checkbox to state 'checked'
	 * 
	 * @param checked
	 */
	public void toggle(boolean checked){
		if (checked){
			if (isChecked()) {
				log.debug("Checkbox already checked");
				return;
			}else{
				log.info("Checking checkbox " + getText());
				click();
			}
		}else{
			if (isChecked()) {
				log.info("Unchecking checkbox " + getText());
				click();
			}else{
				log.debug("Checkbox already unchecked");
				return;
			}
		}
	}
}
