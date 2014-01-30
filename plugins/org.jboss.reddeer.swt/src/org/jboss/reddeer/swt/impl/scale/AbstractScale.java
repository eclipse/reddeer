package org.jboss.reddeer.swt.impl.scale;

import org.jboss.reddeer.swt.api.Scale;
import org.jboss.reddeer.swt.handler.ScaleHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
/**
 * Abstract class for each Scale implementation
 * @author Vlado Pakan
 *
 */
public abstract class AbstractScale implements Scale {

	protected org.eclipse.swt.widgets.Scale swtScale;
	
	protected AbstractScale(org.eclipse.swt.widgets.Scale scale) {
		this.swtScale = scale;
	}
	/**
	 * See {@link Scale}
	 */
	@Override
	public int getMinimum() {
		return ScaleHandler.getMinimum(this);
	}
	/**
	 * See {@link Scale}
	 */

	@Override
	public int getMaximum() {
		return ScaleHandler.getMaximum(this);
	}
	/**
	 * See {@link Scale}
	 */
	@Override
	public int getSelection() {
		return ScaleHandler.getSelection(this);
	}
	/**
	 * See {@link Scale}
	 */
	@Override
	public void setSelection(int value) {
		ScaleHandler.setSelection(this, value);		
	}
	/**
	 * See {@link Scale}
	 */
	@Override
	public void setFocus() {
		WidgetHandler.getInstance().setFocus(swtScale);		
	}
	@Override
	public org.eclipse.swt.widgets.Scale getSWTWidget() {
		return swtScale;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetLookup.getInstance().isEnabled(swtScale);
	}
}
