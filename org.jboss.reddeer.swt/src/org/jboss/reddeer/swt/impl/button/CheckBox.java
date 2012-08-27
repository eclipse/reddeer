package org.jboss.reddeer.swt.impl.button;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Class represents Button with type Toggle (Checkbox)
 * 
 * @author rhopp
 * 
 */

public class CheckBox implements Button {

	protected final Logger log = Logger.getLogger(this.getClass());
	private SWTBotCheckBox checkBox;

	/**
	 * Default constructor
	 */

	public CheckBox() {
		checkBox = Bot.get().checkBox();
	}
	
	/**
	 * Checkbox with given index
	 * 
	 * @param index
	 */
	
	public CheckBox(int index){
		checkBox = Bot.get().checkBox(index);
	}

	/**
	 * Checkbox with given label
	 * 
	 * @param label
	 */

	public CheckBox(String label) {
		checkBox = Bot.get().checkBoxWithLabel(label);
	}

	public boolean isChecked() {
		return checkBox.isChecked();
	}
	
	/**
	 * Sets checkbox to state 'checked'
	 * 
	 * @param checked
	 */
	
	public void toggle(boolean checked){
		if (checked){
			if (checkBox.isChecked()) {
				log.debug("Checkbox already checked");
				return;
			}else{
				click();
			}
		}else{
			if (checkBox.isChecked()) {
				click();
			}else{
				log.debug("Checkbox already unchecked");
				return;
			}
		}
	}

	@Override
	public void click() {
		log.debug("Clicking on checkbox");
		checkBox.click();
	}

	@Override
	public String getText() {
		return checkBox.getText();
	}

}
