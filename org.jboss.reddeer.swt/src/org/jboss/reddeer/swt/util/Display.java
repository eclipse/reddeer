package org.jboss.reddeer.swt.util;

import java.util.ArrayList;

import org.eclipse.swt.SWTException;
import org.jboss.reddeer.swt.exception.SWTLayerException;

/**
 * Reddeer display provider
 * @author Jiri Peterka
 *
 */
public class Display {
	
	private static org.eclipse.swt.widgets.Display display;
	
	public static void syncExec(Runnable runnable) {
		try{
			getDisplay().syncExec(runnable);
		}catch(SWTException ex){
			if(ex.getCause() instanceof SWTLayerException){
				throw (SWTLayerException) ex.getCause();
			} else {
				throw ex;
			}
		}
	}

	public static void asyncExec(Runnable runnable) {
		try{
			getDisplay().asyncExec(runnable);
		}catch(SWTException ex){
			if(ex.getCause() instanceof SWTLayerException){
				throw (SWTLayerException) ex.getCause();
			} else {
				throw ex;
			}
		}
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
				if (d != null)
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
		try{
			Display.getDisplay().syncExec(new Runnable()  {
				
				@Override
				public void run() {
					T res = runnable.run();
					list.add(res);
				
				}
			});
		}catch(SWTException ex){
			if(ex.getCause() instanceof SWTLayerException){
				throw (SWTLayerException) ex.getCause();
			} else {
				throw ex;
			}
		}
		return list.get(0);
		
	}
	
	/**
	 * Run asynchronously in UI Thread 
	 * @param runnable
	 * @return 
	 */
	public static <T> T asynExec(final ResultRunnable<T> runnable) {
		final ArrayList<T> list = new ArrayList<T>();		
		try{
			Display.getDisplay().asyncExec(new Runnable()  {

				@Override
				public void run() {
					T res = runnable.run();
					list.add(res);
				
				}
			});
		}catch(SWTException ex){
			if(ex.getCause() instanceof SWTLayerException){
				throw (SWTLayerException) ex.getCause();
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

