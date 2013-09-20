package org.jboss.reddeer.swt.condition;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.impl.shell.AbstractShell;
import org.jboss.reddeer.swt.lookup.ShellLookup;

/**
 * Condition is fulfilled when shell with text is active
 * 
 * @author Vlado Pakan
 * 
 */
public class ShellWithTextIsActive implements WaitCondition {

	private String text;
	private final Logger log = Logger.getLogger(this.getClass());

	public ShellWithTextIsActive(String text) {
		this.text = text;
	}

	@Override
	public boolean test() {
		Shell swtShell = ShellLookup.getInstance().getCurrentActiveShell();
		if (swtShell == null) {
			return false;
		}
		BasicShell activeShell = new BasicShell(swtShell);
		String activeText = activeShell.getText();
		log.debug("Active shell: " + activeText + " / Expected shell: " + text);
		return activeText != null && activeText.equals(text);
	}

	@Override
	public String description() {
		return "Shell with text " + text + " is active";
	}

	private class BasicShell extends AbstractShell {

		public BasicShell(Shell swtShell) {
			this.swtShell = swtShell;
		}
	}
}
