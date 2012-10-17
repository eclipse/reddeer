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
	
	/**
	 * Combo with given index in given Group
	 * @param index of combo
	 * @param inGroup in group
	 */
	public DefaultCombo(String inGroup, int index){
		combo = Bot.get().comboBoxInGroup(inGroup, index);
	}
	/**
	 * Combo with given text in given Group
	 * @param label of combo
	 * @param inGroup in group
	 */
	public DefaultCombo(String inGroup, String label){
		combo = Bot.get().comboBoxInGroup(inGroup, label);
	}
}
