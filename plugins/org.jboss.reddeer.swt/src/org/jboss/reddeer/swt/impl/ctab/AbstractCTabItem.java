package org.jboss.reddeer.swt.impl.ctab;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.CTabItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.core.handler.CTabItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all CTabItem implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractCTabItem extends AbstractWidget<org.eclipse.swt.custom.CTabItem> implements CTabItem {

	private static final Logger logger = Logger.getLogger(AbstractCTabItem.class);

	protected org.eclipse.swt.custom.CTabFolder swtParent;

	private CTabItemHandler cTabItemHandler = CTabItemHandler.getInstance();

	protected AbstractCTabItem(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.custom.CTabItem.class, referencedComposite, index, matchers);
		this.swtParent = cTabItemHandler.getCTabFolder(swtWidget);
	}
	
	/**
	 * See {@link CTabItem}
	 */
	@Override
	public void activate() {
		logger.info("Activate " + this.getText());
		cTabItemHandler.select(swtWidget);
		cTabItemHandler.notifyCTabFolder(
			swtWidget,
			cTabItemHandler.createEventForCTabItem(swtWidget,SWT.Selection));
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public String getToolTipText() {
		return WidgetHandler.getInstance().getToolTipText(swtWidget);
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public void close() {
		logger.info("Close CTabItem " + getText());
		if (isShowClose()) {
			activate();
			cTabItemHandler.clickCloseButton(swtWidget);
		} else {
			throw new SWTLayerException("CTabItem with label " + getText()
					+ " has no close button");
		}
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public boolean isShowClose() {
		return cTabItemHandler.isShowClose(swtWidget);
	}
}
