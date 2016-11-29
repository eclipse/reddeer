package org.jboss.reddeer.gef.handler;

import org.eclipse.draw2d.IFigure;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.ObjectUtil;

/**
 * Handler for {@link org.eclipse.draw2d.IFigure}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class FigureHandler {

	protected final Logger log = Logger.getLogger(this.getClass());

	private static FigureHandler instance;

	private FigureHandler() {

	}

	/**
	 * Gets the single instance of FigureHandler.
	 *
	 * @return single instance of FigureHandler
	 */
	public static FigureHandler getInstance() {
		if (instance == null) {
			instance = new FigureHandler();
		}
		return instance;
	}

	/**
	 * Gets text of the specified figure.
	 *
	 * @param <T>
	 *            the generic type
	 * @param figure
	 *            figure to handle
	 * @return text of the specified figure or null if method 'getText()' is missing
	 */
	public <T extends IFigure> String getText(final T figure) {
		if (figure == null) {
			return null;
		}

		Object o = ObjectUtil.invokeMethod(figure, "getText");
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return (String) o;
		}

		return null;
	}
}
