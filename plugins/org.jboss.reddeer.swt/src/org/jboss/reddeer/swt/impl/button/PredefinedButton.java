package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.matcher.WithStyleMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Parent for all all prescribed button implementations like OK, Cancel, Next, etc.
 * @author Jiri Peterka
 *
 */
public abstract class PredefinedButton extends AbstractButton {

	protected PredefinedButton(ReferencedComposite refComposite, int index,
			String text, int style) {
		
		super(refComposite, index, SWT.PUSH,new WithMnemonicTextMatcher(text),new WithStyleMatcher(style));

	}

}
