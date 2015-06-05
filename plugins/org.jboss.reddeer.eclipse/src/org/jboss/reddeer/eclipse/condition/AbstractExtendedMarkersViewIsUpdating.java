package org.jboss.reddeer.eclipse.condition;

import java.lang.reflect.Method;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.internal.views.markers.ExtendedMarkersView;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.core.lookup.WorkbenchPartLookup;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.workbench.impl.view.AbstractView;

/**
 * Returns true if marker based view is updating its UI. This is an abstract class and 
 * its subclasses should specify the concrete view. 
 * 
 * @author Jiri Peterka
 * 
 */
@SuppressWarnings("restriction")
public abstract class AbstractExtendedMarkersViewIsUpdating implements WaitCondition {

	private ExtendedMarkersView markersView;

	/**
	 * Construct the condition.
	 * @param abstractView 
	 * @param class1 
	 */
	public AbstractExtendedMarkersViewIsUpdating(AbstractView abstractView, final Class<? extends ExtendedMarkersView> viewClass) {
		abstractView.open();
		for (IViewPart part : WorkbenchPartLookup.getInstance().getOpenViews()){
			if (part.getClass().equals(viewClass)){
				markersView = (ExtendedMarkersView) part;
				return;
			}
		}
		throw new EclipseLayerException("Cannot find view with the specified class " + viewClass);				
	}

	@Override
	public boolean test() {
		boolean res = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				boolean result = false;
				try {
					Method m = ExtendedMarkersView.class.getDeclaredMethod(
							"isUIUpdating", new Class<?>[0]);
					m.setAccessible(true);
					result = (Boolean) m.invoke(markersView, new Object[0]);
				} catch (Exception e) {
					throw new EclipseLayerException("Cannot invoke isUIUpdating", e);
				}
				return result;
			}
		});
		return res;
	}

	@Override
	public String description() {
		return "marker view is updating";
	}
}
