package org.jboss.reddeer.swt.impl.scale;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Scale;
import org.jboss.reddeer.swt.handler.ScaleHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
/**
 * Abstract class for each Scale implementation
 * @author Vlado Pakan
 *
 */
public abstract class AbstractScale implements Scale {
	
	private static final Logger log = Logger.getLogger(AbstractScale.class);

	protected org.eclipse.swt.widgets.Scale swtScale;
	
	protected AbstractScale(org.eclipse.swt.widgets.Scale scale) {
		this.swtScale = scale;
	}
	/**
	 * See {@link Scale}
	 */
	@Override
	public int getMinimum() {
		return ScaleHandler.getInstance().getMinimum(this.getSWTWidget());
	}
	/**
	 * See {@link Scale}
	 */

	@Override
	public int getMaximum() {
		return ScaleHandler.getInstance().getMaximum(this.getSWTWidget());
	}
	/**
	 * See {@link Scale}
	 */
	@Override
	public int getSelection() {
		return ScaleHandler.getInstance().getSelection(this.getSWTWidget());
	}
	/**
	 * See {@link Scale}
	 */
	@Override
	public void setSelection(int value) {
		log.info("Set scale selection to " + value);
		ScaleHandler.getInstance().setSelection(this.getSWTWidget(), value);		
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
		return WidgetHandler.getInstance().isEnabled(swtScale);
	}
}
