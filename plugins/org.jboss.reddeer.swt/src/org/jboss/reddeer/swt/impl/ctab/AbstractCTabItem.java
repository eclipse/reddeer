package org.jboss.reddeer.swt.impl.ctab;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.CTabItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.CTabItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Abstract class for all CTabItem implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractCTabItem implements CTabItem {

	private static final Logger logger = Logger.getLogger(AbstractCTabItem.class);

	protected org.eclipse.swt.custom.CTabItem swtCTabItem;
	protected org.eclipse.swt.custom.CTabFolder swtParent;
	private CTabItemHandler cTabItemHandler = CTabItemHandler.getInstance();
	/**
	 * See {@link CTabItem}
	 */
	@Override
	public void activate() {
		logger.info("Activate " + this.getText());
		cTabItemHandler.select(swtCTabItem);
		cTabItemHandler.notifyCTabFolder(
			swtCTabItem,
			cTabItemHandler.createEventForCTabItem(swtCTabItem,SWT.Selection));
	}

	protected AbstractCTabItem(final org.eclipse.swt.custom.CTabItem swtCTabItem) {
		if (swtCTabItem != null) {
			this.swtCTabItem = swtCTabItem;
			this.swtParent = cTabItemHandler.getCTabFolder(swtCTabItem);
		} else {
			throw new SWTLayerException(
					"SWT Tree passed to constructor is null");
		}
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtCTabItem);
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public String getToolTipText() {
		return WidgetHandler.getInstance().getToolTipText(swtCTabItem);
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public void close() {
		logger.info("Close CTabItem " + getText());
		if (isShowClose()) {
			activate();
			cTabItemHandler.clickCloseButton(swtCTabItem);
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
		return cTabItemHandler.isShowClose(swtCTabItem);
	}
	
	public org.eclipse.swt.custom.CTabItem getSWTWidget(){
		return swtCTabItem;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtParent);
	}
	
}
