package org.jboss.reddeer.swt.impl.ctab;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.CTabItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.CTabFolder;
import org.jboss.reddeer.swt.api.CTabItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
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
	
	protected AbstractCTabItem(org.eclipse.swt.custom.CTabItem swtWidget) {
		super(swtWidget);
	}
	
	/**
	 * See {@link CTabItem}
	 */
	@Override
	public void activate() {
		logger.info("Activate " + this.getText());
		cTabItemHandler.activate(swtWidget);
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
	
	@Override
	public boolean isShowing() {
		return cTabItemHandler.isShowing(swtWidget);
	}
	
	@Override
	public CTabFolder getFolder() {
		return new DefaultCTabFolder(swtParent);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((swtWidget == null) ? 0 : swtWidget.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractCTabItem other = (AbstractCTabItem) obj;
		if (swtWidget == null) {
			if (other.swtWidget != null)
				return false;
		} else if (!swtWidget.equals(other.swtWidget))
			return false;
		return true;
	}
}
