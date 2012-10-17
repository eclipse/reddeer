package org.jboss.reddeer.swt.impl.text;

import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Text with label implementation
 * @author Jiri Peterka
 *
 */
public class LabeledText extends AbstractText implements Text {

	/**
	 * Default text with a label
	 * @param label
	 */
	public LabeledText(String label) {
		botText = Bot.get().textWithLabel(label);
	}
	
	/**
	 * Text with given index in given Group
	 * @param index of text
	 * @param inGroup in group
	 */
	public LabeledText(String inGroup, int index){
		botText = Bot.get().textInGroup(inGroup, index);
	}
	
}
