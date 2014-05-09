package org.jboss.reddeer.swt.util;

import java.util.ArrayList;

import org.eclipse.swt.SWTException;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.exception.RedDeerException;
import org.jboss.reddeer.swt.exception.SWTLayerException;

/**
 * RedDeer display provider
 * @author Jiri Peterka
 *
 */
public class Display {
	
	private static org.eclipse.swt.widgets.Display display;
	private static Logger log = Logger.getLogger(Display.class);
	
	public static void syncExec(Runnable runnable) {
		try{
			if (!isUIThread()) {
				getDisplay().syncExec(runnable);
			} else {
				log.warn("UI Call chaining attempt");
				runnable.run();				
			}
		}catch(SWTException ex){
			if(ex.getCause() instanceof RedDeerException){
				throw (RedDeerException) ex.getCause();
			} else {
				throw ex;
			}
		}
	}

	public static void asyncExec(Runnable runnable) {
		try {
			getDisplay().asyncExec(runnable);

		} catch (SWTException ex) {
			if (ex.getCause() instanceof RedDeerException) {
				throw (RedDeerException) ex.getCause();
			} else {
				throw ex;
			}
		}
	}

	private static boolean isUIThread() {
		return getDisplay().getThread() == Thread.currentThread();			
	}

	/**
	 * Returns org.eclipse.swt.widgets.Display instance. If now know it tries to get in available threads.   
	 * @return current Display instance or SWTLayerException if not found
	 */
	public static org.eclipse.swt.widgets.Display getDisplay() {
		if ((display == null) || display.isDisposed()) {
			display = null;
			Thread[] allThreads = allThreads();
			for (Thread thread : allThreads) {
				org.eclipse.swt.widgets.Display d = org.eclipse.swt.widgets.Display.findDisplay(thread);
				if (d != null && !d.isDisposed())
					display = d;
			}
			if (display == null)
				throw new SWTLayerException("Could not find a display");
		}
		return display;
	}

	/**
	 * Run sync in UI thread with ability to return result 
	 * @param runnable
	 * @return 
	 */	
	public static <T> T syncExec(final ResultRunnable<T> runnable) {
		final ArrayList<T> list = new ArrayList<T>();	
		try {
			if (!isUIThread()) {

				Display.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						T res = runnable.run();
						list.add(res);

					}
				});
			} else {
				log.warn("UI Call chaining attempt");
				list.add(runnable.run());
			}
		}catch(SWTException ex){
			if(ex.getCause() instanceof RedDeerException){
				throw (RedDeerException) ex.getCause();
			} else {
				throw ex;
			}
		}
		return list.get(0);
		
	}
		
	private static Thread[] allThreads() {
		ThreadGroup threadGroup = primaryThreadGroup();

		Thread[] threads = new Thread[64];
		int enumerate = threadGroup.enumerate(threads, true);

		Thread[] result = new Thread[enumerate];
		System.arraycopy(threads, 0, result, 0, enumerate);

		return result;
	}

	private static ThreadGroup primaryThreadGroup() {
		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		while (threadGroup.getParent() != null)
			threadGroup = threadGroup.getParent();
		return threadGroup;
	}
	
}

