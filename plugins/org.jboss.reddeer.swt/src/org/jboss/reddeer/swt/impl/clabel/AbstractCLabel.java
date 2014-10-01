package org.jboss.reddeer.swt.impl.clabel;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.util.ObjectUtil;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
/**
 * Abstract class for all CLabel implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractCLabel extends AbstractWidget<org.eclipse.swt.custom.CLabel> implements CLabel {
	
	protected AbstractCLabel(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.custom.CLabel.class, refComposite, index, matchers);
	}
	
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	/**
	 * See {@link CLabel}
	 */
	@Override
	public String getTooltipText() {
		return WidgetHandler.getInstance().getToolTipText(swtWidget);
	}
	/**
	 * See {@link CLabel}
	 */
	@Override
	public int getAlignment() {
		return (Integer)ObjectUtil.invokeMethod(swtWidget, "getAlignment");
	}
	/**
	 * See {@link CLabel}
	 */
	@Override
	public boolean hasImage() {
		return ObjectUtil.invokeMethod(swtWidget, "getImage") != null;
	}
}
