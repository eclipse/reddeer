package org.jboss.reddeer.swt.impl.text;

import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.matcher.WithLabelMatcher;
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
		this(null, label);
	}
	
	/**
	 * Default text with a label inside given composite
	 * @param referencedComposite
	 * @param label
	 */
	public LabeledText(ReferencedComposite referencedComposite, String label) {
		super(referencedComposite, 0, new WithLabelMatcher(label));
	}
}
