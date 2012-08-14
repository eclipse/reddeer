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
	SWTBotCombo combo;
	
	@Override
	public void setText(String str) {
		log.debug("Set text of Combo widget to: " + str);
		combo.setText(str);
		
	}

  @Override
  public boolean isEnabled() {
    return combo.isEnabled();
  }
}
