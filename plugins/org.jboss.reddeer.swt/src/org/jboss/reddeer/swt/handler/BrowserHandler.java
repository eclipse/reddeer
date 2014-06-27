package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.browser.ProgressListener;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.condition.PageIsLoaded;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Contains methods for handling UI operations on
 * {@link org.eclipse.swt.browser.Browser} widgets.
 * 
 * @author Vlado Pakan
 *
 */
public class BrowserHandler {

	private static BrowserHandler instance;

	private BrowserHandler() {

	}

	/**
	 * Gets instance of BrowserHandler.
	 * 
	 * @return instance of BrowserHandler
	 */
	public static BrowserHandler getInstance() {
		if (instance == null) {
			instance = new BrowserHandler();
		}
		return instance;
	}

	/**
	 * Gets URL from specified {@link Browser}.
	 * 
	 * @deprecated Use non static method instead. Will be removed in version
	 *             0.6.
	 * @param browser browser to handle
	 * @return URL of current site in specified browser
	 */
	public static String getURL(final Browser browser) {
		new WaitUntil(new PageIsLoaded(browser));
		return Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return browser.getSWTWidget().getUrl();
			}
		});
	}

	/**
	 * Gets text from a page in specified {@link Browser}.
	 * 
	 * @deprecated Use non static method instead. Will be removed in version
	 *             0.6.
	 * @param browser browser to handle
	 * @return text in a page of specified browser
	 */
	public static String getText(final Browser browser) {
		new WaitUntil(new PageIsLoaded(browser));
		return WidgetHandler.getInstance().getText(browser.getSWTWidget());
	}

	/**
	 * Refreshes loaded page in specified {@link Browser}.
	 * 
	 * @deprecated Use non static method instead
	 * @param browser browser to handle
	 */
	public static void refresh(final Browser browser) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.getSWTWidget().refresh();
			}
		});
	}

	/**
	 * Presses back on specified {@link Browser}.
	 * 
	 * @deprecated Use non static method instead. Will be removed in version 0.6.
	 * @param browser to handle
	 * @return true if the operation was successful, false otherwise
	 */
	public static boolean back(final Browser browser) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.getSWTWidget().back();
			}
		});
	}

	/**
	 * Presses forward on specified {@link Browser}.
	 * 
	 * @deprecated Use non static method instead. Will be removed in version 0.6.
	 * @param browser to handle
	 * @return true if the operation was successful, false otherwise
	 */
	public static boolean forward(final Browser browser) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.getSWTWidget().forward();
			}
		});
	}

	/**
	 * Adds progress listener to specified {@link Browser}.
	 * 
	 * @deprecated Use non static method instead. Will be removed in version 0.6.
	 * @param browser to handle
	 * @param progressListener progress listener to add
	 */
	public static void addProgressListener(final Browser browser,
			final ProgressListener progressListener) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.getSWTWidget().addProgressListener(progressListener);
			}
		});
	}

	/**
	 * Removes progress listener from specified {@link Browser}.
	 * 
	 * @deprecated Use non static method instead. Will be removed in version 0.6.
	 * @param browser to handle
	 * @param progressListener progress listener to remove
	 */
	public static void removeProgressListener(final Browser browser,
			final ProgressListener progressListener) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.getSWTWidget().removeProgressListener(progressListener);
			}
		});
	}

	/**
	 * Sets given URL in specified {@link Browser}.
	 * 
	 * @deprecated Use non static method instead. Will be removed in version 0.6.
	 * @param browser to handle
	 * @param url url to set
	 * @return true if the operation was successful, false otherwise. 
	 */
	public static boolean setURL(final Browser browser, final String url) {
		boolean result = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.getSWTWidget().setUrl(url);
			}
		});

		return result;
	}

	/**
	 * Gets URL from specified {@link org.eclipse.swt.browser.Browser}.
	 * 
	 * @param browser browser to handle
	 * @return URL of current site in specified browser
	 */
	public String getURL(final org.eclipse.swt.browser.Browser browser) {
		return Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return browser.getUrl();
			}
		});
	}

	/**
	 * Gets text from a page in specified {@link org.eclipse.swt.browser.Browser}.
	 * 
	 * @param browser browser to handle
	 * @return text in a page of specified browser
	 */
	public String getText(final org.eclipse.swt.browser.Browser browser) {
		return WidgetHandler.getInstance().getText(browser);
	}

	/**
	 * Refreshes loaded page in specified {@link org.eclipse.swt.browser.Browser}.
	 * 
	 * @param browser browser to handle
	 */
	public void refresh(final org.eclipse.swt.browser.Browser browser) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.refresh();
			}
		});
	}

	/**
	 * Presses back on specified {@link org.eclipse.swt.browser.Browser}.
	 * 
	 * @param browser to handle
	 * @return true if the operation was successful, false otherwise
	 */
	public boolean back(final org.eclipse.swt.browser.Browser browser) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.back();
			}
		});
	}

	/**
	 * Presses forward on specified {@link org.eclipse.swt.browser.Browser}.
	 * 
	 * @param browser to handle
	 * @return true if the operation was successful, false otherwise
	 */
	public boolean forward(final org.eclipse.swt.browser.Browser browser) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.forward();
			}
		});
	}

	/**
	 * Adds progress listener to specified {@link org.eclipse.swt.browser.Browser}.
	 * 
	 * @param browser to handle
	 * @param progressListener progress listener to add
	 */
	public void addProgressListener(
			final org.eclipse.swt.browser.Browser browser,
			final ProgressListener progressListener) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.addProgressListener(progressListener);
			}
		});
	}

	/**
	 * Removes progress listener from specified {@link org.eclipse.swt.browser.Browser}.
	 * 
	 * @param browser to handle
	 * @param progressListener progress listener to remove
	 */
	public void removeProgressListener(
			final org.eclipse.swt.browser.Browser browser,
			final ProgressListener progressListener) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.removeProgressListener(progressListener);
			}
		});
	}

	/**
	 * Sets given URL in specified {@link org.eclipse.swt.browser.Browser}.
	 * 
	 * @param browser to handle
	 * @param url URL to set
	 * @return true if the operation was successful, false otherwise. 
	 */
	public boolean setURL(final org.eclipse.swt.browser.Browser browser,
			final String url) {
		boolean result = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.setUrl(url);
			}
		});

		return result;
	}
}
