package org.jboss.reddeer.eclipse.condition;

import java.lang.reflect.Method;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.views.markers.ExtendedMarkersView;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Returns true if marker based view is updating its UI.
 * 
 * @author Jiri Peterka
 * 
 */
@SuppressWarnings("restriction")
public class MarkerIsUpdating implements WaitCondition {

	/**
	 * Construct the condition.
	 */
	public MarkerIsUpdating() {
	}

	@Override
	public boolean test() {
		boolean res = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				boolean result = false;
				ExtendedMarkersView markersView = (ExtendedMarkersView) PlatformUI
						.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActivePart();
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
