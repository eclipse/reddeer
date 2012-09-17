package org.jboss.reddeer.swt.impl.label;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.jboss.reddeer.swt.api.Label;

public abstract class AbstractLabel implements Label {

	protected SWTBotLabel label; 
	
	public AbstractLabel() {
	}

	@Override
	public String getText() {
		return label.getText();
	}
}
