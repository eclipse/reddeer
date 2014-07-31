package org.jboss.reddeer.swt.lookup;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchSite;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.direct.platform.RunningPlatform;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.matcher.AndMatcher;
import org.jboss.reddeer.swt.matcher.ClassMatcher;
import org.jboss.reddeer.swt.matcher.MatcherBuilder;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitUntil;
/**
 * Widget Lookup methods contains core lookup and resolving widgets
 * @author Jiri Peterka
 * @author Jaroslav Jankovic
 *
 */
public class WidgetLookup {

	private static WidgetLookup instance = null;
	private static final Logger logger = Logger.getLogger(WidgetLookup.class);

	private WidgetLookup() {
	}

	/**
	 * Returns WidgetLookup instance
	 * @return widgetLookup instance
	 */
	public static WidgetLookup getInstance() {
		if (instance == null) instance = new WidgetLookup();
		return instance;
	}

	/**
	 * Send click notification to a widget
	 * @deprecated Use WidgetHandler instead
	 * @param widget
	 */
	public void sendClickNotifications(Widget widget) {
		WidgetHandler.getInstance().notify(SWT.Selection,widget);
	}

	/**
	 * Notifies widget with given event type
	 * @deprecated Use WidgetHandler instead
	 * @param eventType given event type
	 * @param widget target widget
	 */
	public void notify(int eventType, Widget widget) {
		Event event = createEvent(widget);
		notify(eventType, event, widget);

	}
	
	/**
	 * @deprecated Use HyperlinkHandler instead
	 * @param eventType
	 * @param widget
	 */
	public void notifyHyperlink(int eventType, Widget widget) {
		Event event = createHyperlinkEvent(widget);
		notify(eventType, event, widget);

	}
	
	/**
	 * @deprecated Use widget handler instead
	 * @param eventType
	 * @param detail
	 * @param widget
	 * @param widgetItem
	 */
	public void notifyItem(int eventType, int detail, Widget widget, Widget widgetItem) {
		Event event = createEventItem(eventType, detail, widget, widgetItem);
		notify(eventType, event, widget);	
	}
	
	/**
	 * @deprecated Use WidgetHandler instead
	 * @param eventType
	 * @param detail
	 * @param widget
	 * @param widgetItem
	 * @param x
	 * @param y
	 * @param button
	 */
	public void notifyItemMouse(int eventType, int detail, Widget widget, Widget widgetItem, int x, int y, int button) {
		Event event = createMouseItemEvent(eventType, detail, widget, widgetItem, x, y, button);
		notify(eventType, event, widget);	
	}

	private Event createEvent(Widget widget) {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = Display.getDisplay();
		return event;
	}

	private Event createHyperlinkEvent(Widget widget){
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = Display.getDisplay();
		event.button=1;
		event.x=0;
		event.y=0;
		return event;
	}

	private Event createMouseItemEvent(int eventType, int detail, Widget widget, Widget widgetItem, int x, int y, int button){
		Event event = new Event();
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = widgetItem;
		event.widget = widget;
		event.detail = detail;
		event.type = eventType;
		event.button=button;
		event.x=x;
		event.y=y;
		return event;
	}

	private Event createEventItem(int eventType, int detail, Widget widget, Widget widgetItem) {
		Event event = new Event();
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = widgetItem;
		event.widget = widget;
		event.detail = detail;
		event.type = eventType;
		return event;
	}



	private void notify(final int eventType, final Event createEvent, final Widget widget) {
		createEvent.type = eventType;

		Display.asyncExec(new Runnable() {
			public void run() {
				if ((widget == null) || widget.isDisposed()) {
					return;
				}

				widget.notifyListeners(eventType, createEvent);
			}
		});

		// Wait for synchronization
		Display.syncExec(new Runnable() {
			public void run() {
				// do nothing here
			}
		});
	}


	/**
	 * Method looks for active widget matching given criteria like reference composite, class, etc.
	 * @param refComposite reference composite within lookup will be performed
	 * @param clazz given class for a lookup
	 * @param index widget index for a lookup
	 * @param matchers additional matchers
	 * @return returns matching widget
	 */
	@SuppressWarnings({ "rawtypes","unchecked" })
	public <T extends Widget> T activeWidget(ReferencedComposite refComposite, Class<T> clazz, int index, Matcher... matchers) {				
		logger.debug("Looking up active widget with class type " + clazz.getName() +  ", index " + index + " and " + createMatcherDebugMsg(matchers));
		
		ClassMatcher cm = new ClassMatcher(clazz);
		Matcher[] allMatchers = MatcherBuilder.getInstance().addMatcher(matchers, cm);
		AndMatcher am  = new AndMatcher(allMatchers);

		WidgetIsFound found = new WidgetIsFound(refComposite, am, index);
		try{
			new WaitUntil(found);
		} catch (WaitTimeoutExpiredException ex){
			String exceptionText = "No matching widget found";
			if(clazz.isInstance(Combo.class)){
				exceptionText = "Combo not found - see https://github.com/jboss-reddeer/reddeer/issues/485";
			}
			logger.error("Active widget with class type " + clazz.getName() +  " and index " + index + " was not found");
			throw new SWTLayerException(exceptionText, ex);
		}

		logger.debug("Active widget with class type " + clazz.getName() +  " and index " + index + " was found");
		return (T)found.getWidget();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<? extends Widget> activeWidgets(Control refComposite, Matcher matcher) {
		if(refComposite == null){
			logger.trace("No parent specified, finding one");
			refComposite = getActiveWidgetParentControl();
			logger.trace("Parent found successfully");
		}

		if (refComposite == null){
			logger.error("Unable to determine active parent");
			throw new SWTLayerException("Unable to determine active parent");
		}
		logger.trace("Looking up widgets with specified parent and matchers");
		List<? extends Widget> widgets = findControls(refComposite, matcher, true);
		logger.trace(widgets.size() + " widget(s) found");
		return widgets;
	}


	/**
	 * Checks is extra shell (shell outside workbench) active 
	 * @return true if extra shell is active
	 */
	public boolean isExtraShellActive() {
		IWorkbenchPartReference activeWorkbenchReference = WorkbenchLookup.findActiveWorkbenchPart();
		Shell activeWorkbenchParentShell = getShellForActiveWorkbench(activeWorkbenchReference);

		Shell activeShell = ShellLookup.getInstance().getActiveShell();
		if (activeWorkbenchParentShell == null || activeWorkbenchParentShell != activeShell){
			return true;
		}
		return false;
	}


	/**
	 * Looks for active parent control. Either finds activeWorkbenchReference control or activeShell 
	 * @return active workbench control or active shell
	 */
	public Control getActiveWidgetParentControl() {
		Control control = null;

		IWorkbenchPartReference activeWorkbenchReference = WorkbenchLookup.findActiveWorkbenchPart();
		Shell activeWorkbenchParentShell = getShellForActiveWorkbench(activeWorkbenchReference);
		Shell activeShell = ShellLookup.getInstance().getActiveShell();

		if ((activeWorkbenchParentShell == null || !activeWorkbenchParentShell.equals(activeShell))
				&& activeShell != null){
			logger.trace("Setting active shell with title \"" + WidgetHandler.getInstance().getText(activeShell) + "\" as the parent");
			control = activeShell;	
		}			
		else {
			if (activeWorkbenchReference != null){
				logger.trace("Setting workbench part with title \"" + getTitle(activeWorkbenchReference) + "\"as the parent");
				control = WorkbenchLookup.getWorkbenchControl(activeWorkbenchReference);
			}
		}	
		return control;
	}
	
	private Shell getShellForActiveWorkbench(IWorkbenchPartReference workbenchReference) {
		if (workbenchReference == null) {
			return null;
		}
		IWorkbenchPart wPart = workbenchReference.getPart(true);
		if (wPart == null) {
			return null;
		}
		IWorkbenchSite wSite = wPart.getSite();
		if (wSite == null) {
			return null;
		}
		return wSite.getShell();
	}

	private <T extends Widget> T getProperWidget(List<T> widgets, int index) {
		T widget = null;
		if (widgets.size() > index){
			logger.trace("Selecting widget with the specified index (" + index + ")");
			widget = widgets.get(index);
		} else {
			logger.trace("The specified index is bigger than the size of found widgets (" + index + " > " + widgets.size() + ")");
		}
		return widget;
	}

	/**
	 * Finds Control for active parent
	 * @param matcher criteria matcher
	 * @param recursive true for recursive lookup
	 * @return
	 */
	public<T extends Widget> List<T> findActiveParentControls(final Matcher<T> matcher, final boolean recursive) {
		List<T> findControls = findControls(getActiveWidgetParentControl(), matcher, recursive);
		return findControls;
	}

	/**
	 * Find Controls for parent widget matching
	 * @param parentWidget given parent widget - root for lookup
	 * @param matcher criteria matcher
	 * @param recursive true if search should be recursive
	 * @return list of matching widgets
	 */
	private <T extends Widget> List<T> findControls(final Widget parentWidget, 
			final Matcher<T> matcher, final boolean recursive) {
		List<T> ret = Display.syncExec(new ResultRunnable<List<T>>() {

			@Override
			public List<T> run() {
				List<T> findControlsUI = findControlsUI(parentWidget, matcher, recursive);
				return findControlsUI;
			}
		});
		return ret;
	}

	/**
	 * Return control with focus
	 * 
	 * @return control with focus
	 */
	public Control getFocusControl() {
		Control c = Display.syncExec(new ResultRunnable<Control>() {
			@Override
			public Control run() {
				Control focusControl = Display.getDisplay().getFocusControl();
				return focusControl;
			}
		});
		return c;
	}


	/**
	 * Create lists of widget matching matcher (can be called recursively)
	 * @param parentWidget parent widget
	 * @param matcher
	 * @param recursive
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends Widget> List<T> findControlsUI(final Widget parentWidget, final Matcher<T> matcher, final boolean recursive) {

		if ((parentWidget == null) || parentWidget.isDisposed())
			return new ArrayList<T>();


		if (!visible(parentWidget)) {
			return new ArrayList<T>();
		}

		LinkedHashSet<T> controls = new LinkedHashSet<T>();

		if (matcher.matches(parentWidget) && !controls.contains(parentWidget))
			try {
				controls.add((T) parentWidget);
			} catch (ClassCastException exception) {
				throw new IllegalArgumentException("The specified matcher should only match against is declared type.", exception);
			}
		if (recursive) {
			List<Widget> children = WidgetResolver.getInstance().getChildren(parentWidget);
			controls.addAll(findControlsUI(children, matcher, recursive));
		}
		return new ArrayList<T>(controls);
	}

	/**
	 * Creates matching list of widgets from given list of widgets matching matcher, can find recursively in each child
	 * Note: Must be used in UI Thread
	 * @param widgets - given list of widgets
	 * @param matcher - given hamcrest matcher
	 * @param recursive - recursive switch for searching in children
	 * @return
	 */
	private <T extends Widget> List<T> findControlsUI(final List<Widget> widgets, final Matcher<T> matcher, final boolean recursive) {
		LinkedHashSet<T> list = new LinkedHashSet<T>();
		for (Widget w : widgets) {
			list.addAll(findControlsUI(w, matcher, recursive));
		}
		return new ArrayList<T>(list);
	}

	/**
	 * Returns true if instance is visible
	 * @param w
	 * @return
	 */
	private boolean visible(Widget w) {
		return !((w instanceof Control) && !((Control) w).getVisible());
	}

	class WidgetIsFound implements WaitCondition {

		private ReferencedComposite refComposite;
		private AndMatcher am;
		private int index;
		private Widget properWidget;

		public <T extends Widget> WidgetIsFound(ReferencedComposite refComposite, AndMatcher am, int index) {
			this.refComposite =refComposite;
			this.am=am;
			this.index=index;
		}

		public boolean test() {
			if(refComposite == null){
				properWidget = getProperWidget(activeWidgets(null, am), index);
			} else {
				properWidget = getProperWidget(activeWidgets(refComposite.getControl(), am), index);
			}
			if(properWidget == null){
				return false;
			}
			return true;
		}

		public Widget getWidget(){
			setFocus();
			return properWidget;
		}

		@Override
		public String description() {
			return "widget is found";
		}

		private void setFocus(){
			if (RunningPlatform.isWindows() && properWidget instanceof Button &&
					((WidgetHandler.getInstance().getStyle((Button)properWidget) & SWT.RADIO) != 0)){
				// do not set focus because it also select radio button on Windows
			} else {
				WidgetHandler.getInstance().setFocus(properWidget);
			}
		}
	}

	private String getTitle(final IWorkbenchPartReference part){
		return Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return part.getTitle();
			}
		});
	}
	
	private String createMatcherDebugMsg(Matcher<?>[] matchers) {
		StringBuilder sb = new StringBuilder();

		if (matchers.length == 0){
			sb.append("no matchers specified");
		} else {
			sb.append("following matchers specified (");
		}
		
		for (int ind = 0 ; ind < matchers.length ; ind++ ){
			sb.append(matchers[ind].getClass());
			if (ind < matchers.length - 1){
				sb.append(", ");
			} else {
				sb.append(")");
			}
		}
		return sb.toString();
	}
}

