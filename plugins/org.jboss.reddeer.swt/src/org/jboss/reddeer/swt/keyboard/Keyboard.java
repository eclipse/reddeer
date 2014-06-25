package org.jboss.reddeer.swt.keyboard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;

/**
 * Class for operating with keyboard
 * 
 * @author rhopp
 * 
 */

abstract public class Keyboard {
	
	protected static final Logger log = Logger.getLogger(Keyboard.class);
	
	private static final int DELAY = 150;
	
	/**
	 * Invokes given key combination. Accepts chars or {@link org.eclipse.swt.SWT} constants. For example: invokeKeyCombination(SWT.CONTROL, SWT.SHIFT, 't');
	 * 
	 * @param keys either chars or values from SWT.KeyCombination
	 * @see org.eclipse.swt.SWT
	 */
	
	public void invokeKeyCombination(int... keys){
		final Widget w = WidgetLookup.getInstance().getFocusControl();
		log.debug("Invoking keys: ");
		for (int i=0; i<keys.length; i++){
			delay(DELAY);
			log.debug("    As char:"+(char)keys[i]+", as int:"+keys[i]);
			Display.getDisplay().post(keyEvent(keys[i], SWT.KeyDown, w));
		}
		for (int i=keys.length-1; i>=0; i--){
			delay(DELAY);
			Display.getDisplay().post(keyEvent(keys[i], SWT.KeyUp, w));
		}
	}
	
	/**
	 * Types given text
	 * 
	 * @param text
	 */
	
	public void type(String text){
		log.debug("Typing text \""+text+"\"");
		for (char c : text.toCharArray()) {
			invokeKeyCombination(DefaultKeyboardLayout.getInstance().getKeyCombination(c));
		}
	}
	
	/**
	 *  Types given character
	 * @param c
	 */
	
	public void type(int c){
		press(c);
		release(c);
	}
	
	/**
	 * Selects `shift` characters to the side of cursor specified by `toLeft`
	 * 
	 * @param shift
	 * @param toLeft
	 */
	
	public void select(int shift, boolean toLeft){
		if (toLeft){
			log.debug("Selecting "+shift+" characters to the left");
		}else{
			log.debug("Selecting "+shift+" characters to the right");
		}
		press(SWT.SHIFT);
		moveCursor(shift, toLeft);
		release(SWT.SHIFT);
	}
	
	/**
	 * Moves cursor by `shift` characters to the side of cursor specified by `toLeft`
	 * @param shift
	 * @param toLeft
	 */
	
	public void moveCursor(int shift, boolean toLeft){
		for (int i=0; i<shift; i++){
			if (toLeft){
				type(SWT.ARROW_LEFT);
			}else{
				type(SWT.ARROW_RIGHT);
			}
			delay(DELAY);
		}
	}
	
	/**
	 * Either cuts or copies selected text to clipboard
	 * 
	 * @param cut cuts the text if true, copies otherwise
	 */
	
	abstract public void writeToClipboard(boolean cut);
	
	/**
	 * Pastes text stored in clipboard
	 */
	
	abstract public void pasteFromClipboard();
	
	
	protected void press(final int key){
		final Widget w = WidgetLookup.getInstance().getFocusControl();
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Display.getDisplay().post(keyEvent(key, SWT.KeyDown, w));
				delay(DELAY);;
			}
		});
	}
	
	protected void release(final int key){
		final Widget w = WidgetLookup.getInstance().getFocusControl();
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Display.getDisplay().post(keyEvent(key, SWT.KeyUp, w));
				delay(DELAY);;
			}
		});
	}
	
	private Event keyEvent(int key, int eventType, Widget w){
		Event e = new Event();
		e.keyCode = key;
		e.character = (char) key;
		e.type = eventType;
		e.widget = w;
		return e;
	}
	
	private void delay(int delay){
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
