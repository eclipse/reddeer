package org.jboss.reddeer.swt.util;

import java.util.ArrayList;

import org.eclipse.swt.SWTException;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.jboss.reddeer.swt.exception.SWTLayerException;

/**
 * Reddeer display provider
 * @author Jiri Peterka
 *
 */
public class Display {
	
	public static void syncExec(Runnable runnable) {
		try{
			SWTUtils.display().syncExec(runnable);
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
			SWTUtils.display().asyncExec(runnable);
		}catch(SWTException ex){
			if(ex.getCause() instanceof SWTLayerException){
				throw (SWTLayerException) ex.getCause();
			} else {
				throw ex;
			}
		}
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
	
}

