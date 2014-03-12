package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.browser.ProgressListener;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.condition.PageIsLoaded;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Contains methods that handle UI operations on {@link org.eclipse.swt.browser.Browser} widgets. 
 * @author Vlado Pakan
 *
 */
public class BrowserHandler {

	private static BrowserHandler instance;

	private BrowserHandler() {

	}

	/**
	 * Creates and returns instance of TreeHandler class
	 * 
	 * @return
	 */
	public static BrowserHandler getInstance() {
		if (instance == null) {
			instance = new BrowserHandler();
		}
		return instance;
	}
	
	/**
	 * See {@link Browser}
	 * @deprecated Use non static method instead
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
	 * @deprecated Use non static method instead
	 * @param browser
	 * @return
	 */
	public static String getText (final Browser browser) {
		new WaitUntil(new PageIsLoaded(browser));
		return WidgetHandler.getInstance().getText(browser.getSWTWidget());
	}
	/**
	 * See {@link Browser}
	 * @deprecated Use non static method instead
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
	 * @deprecated Use non static method instead
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
	 * @deprecated Use non static method instead
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
	 * @deprecated Use non static method instead
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
	 * @deprecated Use non static method instead
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
	 * @deprecated Use non static method instead
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
	
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public String getURL (final org.eclipse.swt.browser.Browser browser) {
		return Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return browser.getUrl();
			}
		});
	}

	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public String getText (final org.eclipse.swt.browser.Browser browser) {
		return WidgetHandler.getInstance().getText(browser);
	}
	
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public void refresh (final org.eclipse.swt.browser.Browser browser){
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.refresh();
			}
		});
	}
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public boolean back (final org.eclipse.swt.browser.Browser browser){
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.back();
			}
		});
	}
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public boolean forward (final org.eclipse.swt.browser.Browser browser){
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.forward();
			}
		});
	}
	/**
	 * Adds progress listener to browser
	 * @param browser
	 * @param progressListener
	 */
	public void addProgressListener (final org.eclipse.swt.browser.Browser browser , final ProgressListener progressListener){
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.addProgressListener(progressListener);
			}
		});
	}
	/**
	 * Removes progress listener from browser
	 * @param browser
	 * @param progressListener
	 */
	public void removeProgressListener (final org.eclipse.swt.browser.Browser browser , final ProgressListener progressListener){
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				browser.removeProgressListener(progressListener);
			}
		});
	}
	/**
	 * See {@link Browser}
	 * @param browser
	 * @return
	 */
	public boolean setURL (final org.eclipse.swt.browser.Browser browser , final String url) {
		boolean result = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return browser.setUrl(url);
			}
		});
		
		return result;
	}
}
