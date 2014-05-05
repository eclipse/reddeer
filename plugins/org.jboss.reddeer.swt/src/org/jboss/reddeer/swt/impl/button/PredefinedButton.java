package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.swt.matcher.WithStyleMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Parent for all all prescribed button implementations like OK, Cancel, Next, etc.
 * @author Jiri Peterka
 *
 */
public abstract class PredefinedButton extends AbstractButton {

	protected PredefinedButton(ReferencedComposite refComposite, int index,
			String text, int style) {
		
		super(refComposite, index, text, SWT.PUSH,new WithMnemonicTextMatcher(text),new WithStyleMatcher(style));

	}

}
