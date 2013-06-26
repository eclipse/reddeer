package org.jboss.reddeer.swt.keyboard;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides Keyboard functions
 * 
 * @author Petr Suchy
 * 
 */
public class Keyboard {

	private static SmartRobot robot = null;
	private static Set<Key> heldKeys = null;

	static {
		try {
			robot = new SmartRobot();
			robot.setAutoWaitForIdle(true);
		} catch (AWTException e) {
			throw new IllegalStateException(
					"Keyboard robot could not be initialized.");
		}
		heldKeys = new HashSet<Key>();
	}

	/**
	 * Invokes keystrokes (keys are stroked all at once)
	 * 
	 * @param keys given keys
	 * @see Key
	 */
	public static void invoke(Key... keys) {
		for (Key key : keys) {
			hold(key);
		}
		releaseAll();
	}

	/**
	 * Invokes KeyEvent (keys are stroked all at once) 
	 * (Only for keys not defined in {@link Key} class yet, rather use {@link #invoke(Key...)}
	 * method with Key parameters)
	 * 
	 * @param keyEvents given KeyEvents
	 * @see KeyEvent
	 */
	public static void invokeKeyEvent(Integer... keyEvents) {
		for (int i = 0; i < keyEvents.length; i++) {
			robot.keyPress(keyEvents[i]);
		}
		for (int i = keyEvents.length-1; i >= 0; i--) {
			robot.keyRelease(keyEvents[i]);
		}
	}

	/**
	 * Invokes text typing of given text
	 * 
	 * @param text given String text
	 */
	public static void invoke(String text) {
		robot.type(text);
	}

	/**
	 * Invokes given Action
	 * 
	 * @param action given Action
	 * @see Action
	 */
	public static void invoke(Action action) {
		switch (action) {
		case CUT:
			robot.writeToClipboard(true);
			break;
		case COPY:
			robot.writeToClipboard(false);
			break;
		case PASTE:
			robot.pasteClipboard();
			break;
		default:
			break;
		}
	}

	/**
	 * Selects a specified number of characters (SHIFT + [LEFT/RIGHT])
	 * 
	 * @param shift count of selected characters
	 * @param toLeft if true, text is selecting to the left
	 */
	public static void select(int shift, boolean toLeft) {
		hold(Key.SHIFT);
		moveCursor(shift, toLeft);
		release(Key.SHIFT);
	}

	/**
	 * Moves cursor a specified number of characters to the left or to the right
	 * 
	 * @param shift given number of the shift
	 * @param toLeft if true, cursor moves to the left
	 */
	public static void moveCursor(int shift, boolean toLeft) {
		if (toLeft) {
			for (int i = 0; i < shift; i++) {
				invoke(Key.LEFT);
			}
		} else {
			for (int i = 0; i < shift; i++) {
				invoke(Key.RIGHT);
			}
		}
	}

	/**
	 * Holds given keys
	 * 
	 * @param keys given keys
	 * @see Key
	 */
	public static void hold(Key... keys) {
		for (Key key : keys) {
			robot.keyPress(key.getKeyCode());
			heldKeys.add(key);
		}
	}

	/**
	 * Releases given keys
	 * 
	 * @param keys given keys
	 * @see Key
	 */
	public static void release(Key... keys) {
		for (Key key : keys) {
			robot.keyRelease(key.getKeyCode());
			heldKeys.remove(key);
		}
	}

	/**
	 * Releases all currently held keys
	 */
	public static void releaseAll() {
		for (Key key : heldKeys) {
			robot.keyRelease(key.getKeyCode());
		}
		heldKeys.clear();
	}

}
