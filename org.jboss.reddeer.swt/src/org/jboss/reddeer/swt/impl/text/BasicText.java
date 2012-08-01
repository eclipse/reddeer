package org.jboss.reddeer.swt.impl.text;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.jboss.reddeer.swt.api.Text;

/**
 * Abstract class for all Text implementations
 * @author Jiri Peterka
 *
 */
public abstract class BasicText implements Text {
	protected final Log logger = LogFactory.getLog(this.getClass());
	SWTBotText text;
	
	@Override
	public void setText(String str) {
		logger.debug("Set text of Text widget to: " + str);
		text.setText(str);
		
	}

}
