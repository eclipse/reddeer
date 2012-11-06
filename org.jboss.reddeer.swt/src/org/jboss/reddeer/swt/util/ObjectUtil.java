package org.jboss.reddeer.swt.util;

import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.jboss.reddeer.swt.exception.SWTLayerException;

public class ObjectUtil {

	/**
	 * Invokes method
	 * @param object
	 * @param methodName
	 * @return
	 */
	public static Object invokeMethod(final Object object, String methodName) {
			
		final Method method;
		try {
			method = object.getClass().getMethod(methodName,
					new Class[0]);
		} catch (Exception e) {
			throw new RuntimeException("Cannot get method : " + e.getMessage());
		}
		Widget widget = null;
		final Object result;
		if (object instanceof Widget) {
			widget = (Widget) object;
			result = UIThreadRunnable.syncExec(widget.getDisplay(),
					new Result<Object>() {
						public Object run() {
							Object o = null;
							try {
								o = method.invoke(object, new Object[0]);
							} catch (Exception e) {
								try {
									throw new RuntimeException("Cannot invoke method : " + e.getMessage());
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							return o;
						}
					});
		} else
			try {
				result = method.invoke(object, new Object[0]);
			} catch (Exception e) {
				throw new RuntimeException("Cannot invoke method : " + e.getMessage());
			}

		return result;
	}

}
