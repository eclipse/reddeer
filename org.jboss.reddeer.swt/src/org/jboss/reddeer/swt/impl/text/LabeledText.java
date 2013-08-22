package org.jboss.reddeer.swt.impl.text;

import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.lookup.TextLookup;
import org.jboss.reddeer.swt.matcher.GroupMatcher;
import org.jboss.reddeer.swt.matcher.LabelMatcher;

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
		
		LabelMatcher lm = new LabelMatcher(label);
		w = TextLookup.getInstance().getText(0, lm);
	}
	
	/**
	 * Text with given label in given Group
	 * @param label of text
	 * @param group in group
	 */
	public LabeledText(String label, String group){
		GroupMatcher gm = new GroupMatcher(group);
		LabelMatcher lm = new LabelMatcher(label);
		w = TextLookup.getInstance().getText(0, gm, lm);
	}
	
}
