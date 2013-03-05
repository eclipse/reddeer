package org.jboss.reddeer.swt.impl.text;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.exception.SWTLayerException;
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
		try{
			botText = Bot.get().textWithLabel(label);
		} catch (WidgetNotFoundException e){
			throw new SWTLayerException("Text with label " + label + " was not found");
		}
	}
	
	/**
	 * Text with given label in given Group
	 * @param label of text
	 * @param group in group
	 */
	public LabeledText(String label, String group){
		try{
			botText = Bot.get().textWithLabelInGroup(label, group);
		} catch (WidgetNotFoundException e){
			throw new SWTLayerException("Text with label " + label + " in group "+group+" was not found");
		}
	}
	
}
