package org.jboss.reddeer.swt.impl.scale;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Scale;
import org.jboss.reddeer.core.handler.ScaleHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for each Scale implementation
 * @author Vlado Pakan
 *
 */
public abstract class AbstractScale extends AbstractWidget<org.eclipse.swt.widgets.Scale> implements Scale {

	protected AbstractScale(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.Scale.class, refComposite, index, matchers);
	}
	
	/**
	 * See {@link Scale}.
	 *
	 * @return the minimum
	 */
	@Override
	public int getMinimum() {
		return ScaleHandler.getInstance().getMinimum(this.getSWTWidget());
	}
	
	/**
	 * See {@link Scale}.
	 *
	 * @return the maximum
	 */
	@Override
	public int getMaximum() {
		return ScaleHandler.getInstance().getMaximum(this.getSWTWidget());
	}
	
	/**
	 * See {@link Scale}.
	 *
	 * @return the selection
	 */
	@Override
	public int getSelection() {
		return ScaleHandler.getInstance().getSelection(this.getSWTWidget());
	}
	
	/**
	 * See {@link Scale}.
	 *
	 * @param value the new selection
	 */
	@Override
	public void setSelection(int value) {
		ScaleHandler.getInstance().setSelection(this.getSWTWidget(), value);		
	}
	
	/**
	 * See {@link Scale}.
	 */
	@Override
	public void setFocus() {
		WidgetHandler.getInstance().setFocus(getSWTWidget());		
	}
}
