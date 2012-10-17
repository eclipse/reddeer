package org.jboss.reddeer.swt.impl.combo;

import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Combo with label implementation
 * @author Vlado Pakan
 *
 */
public class DefaultCombo extends AbstractCombo implements Combo {

	public DefaultCombo(String label) {
		combo = Bot.get().comboBoxWithLabel(label);
	}
	
	public DefaultCombo(int index) {
		combo = Bot.get().comboBox(index);
	}
}
