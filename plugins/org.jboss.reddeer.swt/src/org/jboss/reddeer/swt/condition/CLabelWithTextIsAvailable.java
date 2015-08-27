package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;

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
		} catch (CoreLayerException ex) {
			return false;
		}
	}

	@Override
	public String description() {
		return "    CLabel with text '" + cLabelText + "' is available.";
	}
}
