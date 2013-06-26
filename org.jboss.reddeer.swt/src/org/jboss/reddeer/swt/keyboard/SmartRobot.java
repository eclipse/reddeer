package org.jboss.reddeer.swt.keyboard;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 * Robot used in {@link Keyboard} to provide keyboard functions
 * 
 * @author Petr Suchy
 * 
 */
public class SmartRobot extends Robot {

	public SmartRobot() throws AWTException {
		super();
	}
	
	/**
	 * Invokes press and release of given key
	 * 
	 * @param key given key
	 */
	public void invokeKey(int key){
		keyPress(key);
		delay(50);
		keyRelease(key);
	}
	
	/**
	 * Types given text
	 * 
	 * @param text given text
	 */
	public void type(String text) {
		writeStringToClipboard(text);
		pasteClipboard();
		cleanClipboard();
	}
	
	/**
	 * Invokes copy (CTRL+C) or cut (CTRL+X)
	 * 
	 * @param cut if param is true, cut is invoked
	 */
	public void writeToClipboard(boolean cut) {
		keyPress(KeyEvent.VK_CONTROL);
		if(cut){
			keyPress(KeyEvent.VK_X);
			delay(50);
			keyRelease(KeyEvent.VK_X);
		}else{
			keyPress(KeyEvent.VK_C);
			delay(50);
			keyRelease(KeyEvent.VK_C);
		}
		keyRelease(KeyEvent.VK_CONTROL);
	}
	
	/**
	 * Invokes paste (CTRL+V)
	 */
	public void pasteClipboard() {
		keyPress(KeyEvent.VK_CONTROL);
		keyPress(KeyEvent.VK_V);
		delay(50);
		keyRelease(KeyEvent.VK_V);
		keyRelease(KeyEvent.VK_CONTROL);
	}
	

	private void writeStringToClipboard(String s) {
		Transferable transferable = new StringSelection(s);
		clipboard().setContents(transferable, null);
	}
	
	private void cleanClipboard() {
		clipboard().setContents(new StringSelection(""), null);
	}
	
	private static Clipboard clipboard(){
		return Toolkit.getDefaultToolkit().getSystemClipboard();
	}

}
