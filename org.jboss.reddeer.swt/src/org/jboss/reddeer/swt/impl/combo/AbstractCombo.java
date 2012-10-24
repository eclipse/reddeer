package org.jboss.reddeer.swt.impl.combo;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.jboss.reddeer.swt.api.Combo;
/**
 * Abstract class for all Combo implementations
 * @author Vlado Pakan
 *
 */
public abstract class AbstractCombo implements Combo {
	protected final Logger log = Logger.getLogger(this.getClass());
	protected SWTBotCombo combo;
	
	@Override
	public void setText(String str) {
		log.info("Set text of Combo " + combo.getText() + " to: " + str);
		combo.setText(str);
	}
	
	@Override
	public void setSelection(int index) {
		log.info("Set selection of Combo " + combo.getText() + " to index: " + index);
		combo.setSelection(index);
	}
	
	@Override
	public void setSelection(String selection) {
		log.info("Set selection of Combo " + combo.getText() + " to selection: " + selection);
		combo.setSelection(selection);
	}


  @Override
  public boolean isEnabled() {
    return combo.isEnabled();
  }
}
