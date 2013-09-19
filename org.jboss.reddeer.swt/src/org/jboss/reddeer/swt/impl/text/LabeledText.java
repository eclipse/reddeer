package org.jboss.reddeer.swt.impl.text;

import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.lookup.TextLookup;
import org.jboss.reddeer.swt.matcher.LabelMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

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
		w = TextLookup.getInstance().getText(null, 0, lm);
	}
	
	/**
	 * Default text with a label inside given composite
	 * @param referencedComposite
	 * @param label
	 */
	public LabeledText(ReferencedComposite referencedComposite, String label) {
		LabelMatcher lm = new LabelMatcher(label);
		w = TextLookup.getInstance().getText(referencedComposite, 0, lm);
	}
	
}
