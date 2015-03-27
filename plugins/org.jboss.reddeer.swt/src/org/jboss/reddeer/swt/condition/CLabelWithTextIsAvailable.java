package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Wait condition to wait for a CLabel widget with specified text is available.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class CLabelWithTextIsAvailable implements WaitCondition {

	private String cLabelText;

	public CLabelWithTextIsAvailable(String cLabelText) {
		this.cLabelText = cLabelText;
	}

	@Override
	public boolean test() {
		try {
			new DefaultCLabel(cLabelText);
			return true;
		} catch (SWTLayerException ex) {
			return false;
		}
	}

	@Override
	public String description() {
		return "    CLabel with text " + cLabelText + " is available.";
	}
}
