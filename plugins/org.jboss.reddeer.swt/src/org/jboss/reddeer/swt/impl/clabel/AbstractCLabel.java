package org.jboss.reddeer.swt.impl.clabel;

import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.util.ObjectUtil;
/**
 * Abstract class for all CLabel implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractCLabel implements CLabel {
	protected org.eclipse.swt.custom.CLabel cLabel;
	
	public AbstractCLabel (org.eclipse.swt.custom.CLabel cLabel){
		this.cLabel = cLabel;
	}
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(cLabel);
	}
	/**
	 * See {@link CLabel}
	 */
	@Override
	public String getTooltipText() {
		return WidgetHandler.getInstance().getToolTipText(cLabel);
	}
	/**
	 * See {@link CLabel}
	 */
	@Override
	public int getAlignment() {
		return (Integer)ObjectUtil.invokeMethod(cLabel, "getAlignment");
	}
	/**
	 * See {@link CLabel}
	 */
	@Override
	public boolean hasImage() {
		return ObjectUtil.invokeMethod(cLabel, "getImage") != null;
	}

	public org.eclipse.swt.custom.CLabel getSWTWidget() {
		return cLabel;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(cLabel);
	}
}
