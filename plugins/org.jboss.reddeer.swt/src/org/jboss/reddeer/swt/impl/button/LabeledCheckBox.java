package org.jboss.reddeer.swt.impl.button;

import org.jboss.reddeer.core.matcher.WithLabelMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Labeled Check Box implementation. Do not replace label with text.
 * 
 * @author apodhrad
 *
 */
public class LabeledCheckBox extends CheckBox {

	/**
	 * Finds a check box with a given label.
	 * 
	 * @param label
	 *            label
	 */
	public LabeledCheckBox(String label) {
		this(label, 0);
	}

	/**
	 * Finds a check box with a given label at the specified index.
	 * 
	 * @param label
	 *            label
	 * @param index
	 *            index
	 */
	public LabeledCheckBox(String label, int index) {
		this(null, label, index);
	}

	/**
	 * Finds a check box with a given label inside a given reference.
	 * 
	 * @param ref
	 *            reference
	 * @param label
	 *            label
	 */
	public LabeledCheckBox(ReferencedComposite ref, String label) {
		this(ref, label, 0);
	}

	/**
	 * Finds a check box with a given label inside a given reference at the specified index.
	 * 
	 * @param ref
	 *            reference
	 * @param label
	 *            label
	 * @param index
	 *            index
	 */
	public LabeledCheckBox(ReferencedComposite ref, String label, int index) {
		super(ref, index, new WithLabelMatcher(label));
	}
}
