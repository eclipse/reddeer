package org.jboss.reddeer.swt.impl.combo;

import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Combo with label implementation
 * @author Vlado Pakan
 *
 */
public class ComboWithLabel extends AbstractCombo implements Combo {

	public ComboWithLabel(String label) {
		combo = Bot.get().comboBoxWithLabel(label);
	}
}
