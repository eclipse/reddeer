package org.jboss.reddeer.swt.util;

import java.util.ArrayList;

import org.eclipse.swtbot.swt.finder.utils.SWTUtils;

/**
 * Reddeer display provider
 * @author Jiri Peterka
 *
 */
public class Display {
	
	public static void syncExec(Runnable runnable) {
		SWTUtils.display().syncExec(runnable);
	}

	public static void asyncExec(Runnable runnable) {
		SWTUtils.display().asyncExec(runnable);
	}
	
	public static org.eclipse.swt.widgets.Display getDisplay() {
		return SWTUtils.display();
	}

	/**
	 * Run sync in UI thread with ability to return result 
	 * @param runnable
	 * @return 
	 */	
	public static <T> T syncExec(final ResultRunnable<T> runnable) {
		final ArrayList<T> list = new ArrayList<T>();		
		Display.getDisplay().syncExec(new Runnable()  {

			@Override
			public void run() {
				T res = runnable.run();
				list.add(res);
				
			}
		});
		return list.get(0);
		
	}
	
	/**
	 * Run asynchronously in UI Thread 
	 * @param runnable
	 * @return 
	 */
	public static <T> T asynExec(final ResultRunnable<T> runnable) {
		final ArrayList<T> list = new ArrayList<T>();		
		Display.getDisplay().asyncExec(new Runnable()  {

			@Override
			public void run() {
				T res = runnable.run();
				list.add(res);
				
			}
		});
		return list.get(0);
		
	}
	
}

