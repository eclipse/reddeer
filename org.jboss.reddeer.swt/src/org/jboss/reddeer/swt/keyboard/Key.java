package org.jboss.reddeer.swt.keyboard;

import java.awt.event.KeyEvent;

/**
 * Keys that can be invoked by calling {@link Keyboard} methods
 * 
 * @author Petr Suchy
 * 
 */
public enum Key {

	CTRL			(KeyEvent.VK_CONTROL),
	ALT				(KeyEvent.VK_ALT),
	SHIFT			(KeyEvent.VK_SHIFT),
	TAB				(KeyEvent.VK_TAB),
	
	ENTER			(KeyEvent.VK_ENTER),
	SPACE			(KeyEvent.VK_SPACE),
	BACKSPACE		(KeyEvent.VK_BACK_SPACE),
	ESCAPE			(KeyEvent.VK_ESCAPE),
	
	UP				(KeyEvent.VK_UP),
	DOWN			(KeyEvent.VK_DOWN),
	RIGHT			(KeyEvent.VK_RIGHT),
	LEFT			(KeyEvent.VK_LEFT),
	
	INSERT			(KeyEvent.VK_INSERT),
	DELETE			(KeyEvent.VK_DELETE),
	HOME			(KeyEvent.VK_HOME),
	END				(KeyEvent.VK_END),
	PAGE_UP			(KeyEvent.VK_PAGE_UP),
	PAGE_DOWN		(KeyEvent.VK_PAGE_DOWN),
	
	WINDOWS			(KeyEvent.VK_WINDOWS),
	CONTEXT_MENU	(KeyEvent.VK_CONTEXT_MENU),
	
	SCROLL_LOCK		(KeyEvent.VK_SCROLL_LOCK),
	CAPS_LOCK		(KeyEvent.VK_CAPS_LOCK),
	NUM_LOCK		(KeyEvent.VK_NUM_LOCK),
	PRINTSCREEN		(KeyEvent.VK_PRINTSCREEN),
	
	F1				(KeyEvent.VK_F1),
	F2				(KeyEvent.VK_F2),
	F3				(KeyEvent.VK_F3),
	F4				(KeyEvent.VK_F4),
	F5				(KeyEvent.VK_F5),
	F6				(KeyEvent.VK_F6),
	F7				(KeyEvent.VK_F7),
	F8				(KeyEvent.VK_F8),
	F9				(KeyEvent.VK_F9),
	F10				(KeyEvent.VK_F10),
	F11				(KeyEvent.VK_F11),
	F12				(KeyEvent.VK_F12),
	
	;

	
	private final int keyCode;

	Key(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}

}
