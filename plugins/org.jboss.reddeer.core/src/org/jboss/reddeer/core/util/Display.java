/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.core.util;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.interceptor.SyncInterceptorManager;
import org.jboss.reddeer.core.exception.CoreLayerException;


/**
 * RedDeer display provider.
 * 
 * @author Jiri Peterka
 * @author Lucia Jelinkova
 */
public class Display {

	private static final Logger log = Logger.getLogger(Display.class);

	private static org.eclipse.swt.widgets.Display display;
	private static SyncInterceptorManager sim = SyncInterceptorManager.getInstance();

	private Display(){
		super();
	}
	
	/**
	 * Returns {@link org.eclipse.swt.widgets.Display} instance.
	 *    
	 * @return current Display instance or throws CoreLayerException if there is no display
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
				throw new CoreLayerException("Could not find a display");
		}
		return display;
	}

	/**
	 * Run sync in UI thread without returning any result.
	 * 
	 * @param runnable runnable
	 */
	public static void syncExec(Runnable runnable) {
		syncExec(new VoidResultRunnable(runnable));
	}

	/**
	 * Run sync in UI thread with ability to return result.
	 *
	 * @param <T> the generic type
	 * @param runnable runnable
	 * @return result of runnable
	 */	
	@SuppressWarnings("unchecked")
	public static <T> T syncExec(final ResultRunnable<T> runnable) {
		
		if (!sim.isIntercepted()) {
			sim.performBeforeSync();
		}
		
		ErrorHandlingRunnable<T> errorHandlingRunnable = new ErrorHandlingRunnable<T>(runnable);

		if (!isUIThread()) {
			Display.getDisplay().syncExec(errorHandlingRunnable);
		} else {
			if (runnable instanceof ErrorHandlingRunnable){
				errorHandlingRunnable = (ErrorHandlingRunnable<T>) runnable;
			}
			errorHandlingRunnable.run();
		}
		
		if (errorHandlingRunnable.exceptionOccurred()){
			throw new CoreLayerException("Exception during sync execution in UI thread", errorHandlingRunnable.getException());
		}

		if (!sim.isIntercepted()) {
			sim.performAfterSync();
		}
		
		return errorHandlingRunnable.getResult();

	}

	/**
	 * Run async in UI thread without returning any result.
	 * 
	 * @param runnable runnable
	 */
	public static void asyncExec(Runnable runnable) {
		ErrorHandlingRunnable<Void> errorHandlingRunnable = new ErrorHandlingRunnable<Void>(new VoidResultRunnable(runnable));

		getDisplay().asyncExec(errorHandlingRunnable);

		if (errorHandlingRunnable.exceptionOccurred()){
			throw new CoreLayerException("Exception during async execution in UI thread", errorHandlingRunnable.getException());
		}
	}

	private static boolean isUIThread() {
		return getDisplay().getThread() == Thread.currentThread();			
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

	/**
	 * Decorator around the {@link ResultRunnable} classes. Its purpose is to catch any exception from UI thread and store
	 * it so it will be thrown in non  UI thread. 
	 * 
	 * @author Lucia Jelinkova
	 *
	 * @param <T>
	 */
	private static class ErrorHandlingRunnable<T> implements Runnable {

		private ResultRunnable<T> runnable;

		private T result;

		private Exception exception;

		private ErrorHandlingRunnable(ResultRunnable<T> runnable) {
			super();
			this.runnable = runnable;
		}

		@Override
		public void run() {
			try {
				result = runnable.run();
			} catch (Exception e) {
				exception = e;
			}
		}

		public boolean exceptionOccurred(){
			return getException() != null;
		}

		public Exception getException() {
			return exception;
		}

		public T getResult() {
			return result;
		}
	}

	/**
	 * Wrapper class that converts {@link Runnable} to {@link ResultRunnable}
	 * @author Lucia Jelinkova
	 *
	 */
	private static class VoidResultRunnable implements ResultRunnable<Void> {

		private Runnable runnable;

		public VoidResultRunnable(Runnable runnable) {
			this.runnable = runnable;
		}

		@Override
		public Void run() {
			runnable.run();
			return null;
		}
	}
}

