package org.jboss.reddeer.swt.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.impl.shell.AbstractShell;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.util.Utils;

/**
 * Condition is fulfilled when shell with text is active
 * 
 * @author Vlado Pakan
 * @author jniederm
 * 
 */
public class ShellWithTextIsActive implements WaitCondition {

	private final Logger log = Logger.getLogger(this.getClass());
	private org.hamcrest.Matcher<String> matcher;

	/**
	 * @throws IllegalArgumentException if {@code text} is {@code null}
	 */
	public ShellWithTextIsActive(String text) {
		Utils.checkNotNull(text, "text");
		this.matcher = new IsEqual<String>(text);
	}
	
	/**
	 * @throws IllegalArgumentException if {@code matcher} is {@code null}
	 */
	public ShellWithTextIsActive(org.hamcrest.Matcher<String> matcher) {
		Utils.checkNotNull(matcher, "matcher");
		this.matcher = matcher;
	}

	@Override
	public boolean test() {
		Shell swtShell = ShellLookup.getInstance().getCurrentActiveShell();
		if (swtShell == null) {
			return false;
		}
		BasicShell activeShell = new BasicShell(swtShell);
		String activeText = activeShell.getText();
		log.debug("Active shell: " + activeText + " / Expected shell: " + matcher);
		boolean matches = matcher.matches(activeText);
		return matches;
	}

	@Override
	public String description() {
		return "Shell with text matching" + matcher.toString() + " is active";
	}

	private class BasicShell extends AbstractShell {

		public BasicShell(Shell swtShell) {
			this.swtShell = swtShell;
		}
	}
}
