package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.ButtonHandler;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
/**
 * Toggle Button implementation
 * @author Vlado Pakan
 *
 */
public class ToggleButton extends AbstractButton {

	private static final Logger log = Logger.getLogger(ToggleButton.class);
		
	/**
	 * Toggle button with index 0
	 */
	public ToggleButton() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * Creates Toggle button inside given composite
	 * @param referencedComposite
	 */
	public ToggleButton(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Creates Toggle button with given text
	 * @param text
	 */
	public ToggleButton(String text) {
		this(null, text);
	}
	
	/**
	 * Creates Toggle button with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public ToggleButton(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}
	
	/**
	 * Toggle button that matches given matchers
	 * @param matchers
	 */
	public ToggleButton(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Toggle button that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public ToggleButton(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Creates Toggle button with given index
	 * @param index
	 * @param matchers
	 */
	public ToggleButton(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Creates Toggle button with given index inside given composite
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public ToggleButton(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, SWT.TOGGLE, matchers);
	}
	
	/**
	 * Creates Toggle button with given index and label
	 * @param index of button
	 * @param label of button
	 * @deprecated Since 1.0.0. This is not a standard widget constructor.
	 */
	public ToggleButton(int index, String text) {
		super(null, index,text, SWT.TOGGLE);
	}
	
	/**
	 * Creates Toggle button with given index and label inside given composite
	 * @param referencedComposite
	 * @param index of button
	 * @param label of button
	 * @deprecated Since 1.0.0. This is not a standard widget constructor.
	 */
	public ToggleButton(ReferencedComposite referencedComposite, int index, String text) {
		super(referencedComposite, index,text, SWT.TOGGLE);
	}
    /**
     * Returns true when Toggle Button is selected
     * @return
     */
	public boolean isSelected() {
		return ButtonHandler.getInstance().isSelected(swtWidget);
	}
	/**
	 * Sets Toggle Button to state 'checked'
	 * 
	 * @param checked
	 */
	public void toggle(boolean checked){
		if (checked){
			if (isSelected()) {
				log.debug("Toggle Button " + getDescriptiveText() + " already checked, no action performed");
				return;
			}else{
				log.info("Checking Toggle Button " + getDescriptiveText());
				click();
			}
		}else{
			if (isSelected()) {
				log.info("Unchecking Toggle Button " + getDescriptiveText());
				click();
			}else{
				log.debug("Toggle Button " + getDescriptiveText() + " already unchecked, no action performed");
				return;
			}
		}
	}
}