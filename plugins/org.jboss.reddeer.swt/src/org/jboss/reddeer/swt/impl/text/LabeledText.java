package org.jboss.reddeer.swt.impl.text;

import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.core.matcher.WithLabelMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Text with label implementation
 * @author Jiri Peterka
 *
 */
public class LabeledText extends AbstractText implements Text {

	/**
	 * Default text with a label.
	 *
	 * @param label the label
	 */
	public LabeledText(String label) {
		this(null, label);
	}
	
	/**
	 * Default text with a label inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param label the label
	 */
	public LabeledText(ReferencedComposite referencedComposite, String label) {
		super(referencedComposite, 0, new WithLabelMatcher(label));
	}
}
