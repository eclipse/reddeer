package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.browser.ProgressListener;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.condition.PageIsLoaded;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Handles Browser UI actions
 * @author Vlado Pakan
 *
 */
public class BrowserHandler {
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public static String getURL (final Browser browser) {
		new WaitUntil(new PageIsLoaded(browser));
		return Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return browser.getSWTWidget().getUrl();
			}
		});
	}
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public static String getText (final Browser browser) {
		new WaitUntil(new PageIsLoaded(browser));
		return WidgetHandler.getInstance().getText(browser.getSWTWidget());
	}
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public static void refresh (final Browser browser){
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.getSWTWidget().refresh();
			}
		});
	}
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public static boolean back (final Browser browser){
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.getSWTWidget().back();
			}
		});
	}
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public static boolean forward (final Browser browser){
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.getSWTWidget().forward();
			}
		});
	}
	/**
	 * Adds progress listener to browser
	 * @param browser
	 * @param progressListener
	 */
	public static void addProgressListener (final Browser browser , final ProgressListener progressListener){
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.getSWTWidget().addProgressListener(progressListener);
			}
		});
	}
	/**
	 * Removes progress listener from browser
	 * @param browser
	 * @param progressListener
	 */
	public static void removeProgressListener (final Browser browser , final ProgressListener progressListener){
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.getSWTWidget().removeProgressListener(progressListener);
			}
		});
	}
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public static boolean setURL (final Browser browser , final String url) {
		boolean result = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.getSWTWidget().setUrl(url);
			}
		});
		
		return result;
	}
}
