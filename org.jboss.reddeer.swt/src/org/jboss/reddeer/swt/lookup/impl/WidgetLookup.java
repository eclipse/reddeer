package org.jboss.reddeer.swt.lookup.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchSite;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.WidgetResolver;
import org.jboss.reddeer.swt.reference.ReferenceComposite;
import org.jboss.reddeer.swt.matcher.AndMatcher;
import org.jboss.reddeer.swt.matcher.ClassMatcher;
import org.jboss.reddeer.swt.matcher.MatcherBuilder;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ObjectUtil;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Widget Lookup methods contains core lookup and resolving widgets
 * @author Jiri Peterka
 * @author Jaroslav Jankovic
 *
 */
public class WidgetLookup {
	
	private static WidgetLookup instance = null;
	
	private WidgetLookup() {
	}
	
	public static WidgetLookup getInstance() {
		if (instance == null) instance = new WidgetLookup();
		return instance;
	}
	
	/**
	 * Checks if widget is enabled
	 * @param widget
	 * @return
	 */
	public boolean isEnabled(Widget widget) {
		boolean ret = true;
		Object o = null;
		try {
			o = ObjectUtil.invokeMethod(widget, "isEnabled");
		} catch (RuntimeException e) {
			return true;
		}
		if (o == null) return ret;
		if (o instanceof Boolean) {
			ret = ((Boolean)o).booleanValue();
		}
		return ret;
	}
	
	/**
	 * Send click notification to a widget
	 * @param widget
	 */
	public void sendClickNotifications(Widget widget) {
		notify(SWT.MouseEnter, widget);
		notify(SWT.MouseMove, widget);
		notify(SWT.Activate, widget);
		notify(SWT.MouseDown, widget);
		notify(SWT.MouseUp, widget);
		notify(SWT.Selection, widget);
		notify(SWT.MouseHover, widget);
		notify(SWT.MouseMove, widget);
		notify(SWT.MouseExit, widget);
		notify(SWT.Deactivate, widget);
		notify(SWT.FocusOut, widget);
	}

	public void notify(int eventType, Widget widget) {
		Event event = createEvent(widget);
		notify(eventType, event, widget);
		
	}

	private Event createEvent(Widget widget) {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = Display.getDisplay();
		return event;
	}
	
	private void notify(final int eventType, final Event createEvent, final Widget widget) {
		createEvent.type = eventType;
		
		Display.syncExec(new Runnable() {
			public void run() {
				if ((widget == null) || widget.isDisposed()) {
					return;
				}
				if (!WidgetLookup.getInstance().isEnabled(widget)) {
					// do nothing here (it may be expected state (e.g Clear Console))
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
	 * @deprecated As of release 0.4, replaced by {@link #activeWidget(Class, int, Matcher...)}
	 */
	@Deprecated
	public Widget activeShellWidget(Matcher<? extends Widget> matcher, int index) {
		List<? extends Widget> widgets = activeWidgets(new ShellLookup().getActiveShell(), matcher);
		return getProperWidget(widgets, index);
	}

	/**
	 * @deprecated As of release 0.4, replaced by {@link #activeWidget(Class, int, Matcher...)}
	 */
	@Deprecated
	public Widget activeViewWidget(Matcher<? extends Widget> matcher, int index) {
		List<? extends Widget> widgets = activeWidgets(getFocusControl(), matcher);
		return getProperWidget(widgets, index);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by {@link #activeWidget(Class, int, Matcher...)}
	 */
	@Deprecated
	public Widget activeWidget(Matcher<? extends Widget> matcher, Control activeControl, int index) {
		List<? extends Widget> widgets = activeWidgets(activeControl, matcher);
		return getProperWidget(widgets, index);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by {@link #activeWidget(Class, int, Matcher...)}}
	 */
	@Deprecated
	public Widget activeWidget(Matcher<? extends Widget> matcher, int index) {
		return getProperWidget(activeWidgets(matcher), index);
	}
	
	@SuppressWarnings({ "rawtypes","unchecked" })
	public <T extends Widget> T activeWidget(Class<T> clazz, int index, Matcher... matchers) {
		ClassMatcher cm = new ClassMatcher(clazz);
		Matcher[] allMatchers = MatcherBuilder.getInstance().addMatcher(matchers, cm);
		AndMatcher am  = new AndMatcher(allMatchers);
		return (T)getProperWidget(activeWidgets(am), index);
	}
	
	@SuppressWarnings("rawtypes")
	private List<? extends Widget> activeWidgets(Matcher matcher) {
		return activeWidgets(getActiveWidgetParentControl(), matcher);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<? extends Widget> activeWidgets(Control activeControl, Matcher matcher) {
		List<? extends Widget> widgets = findControls(activeControl, matcher, true);
		return widgets;
	}
	
	public Control getActiveWidgetParentControl() {
		Control compositeWidget = ReferenceComposite.getComposite();

		if (compositeWidget != null) {
			return compositeWidget;
		}
		IWorkbenchPartReference activeWorkbenchReference = WorkbenchLookup.findActiveWorkbenchPart();
		Shell activeWorkbenchParentShell = getShellForActiveWorkbench(activeWorkbenchReference);
		Shell activeShell = new ShellLookup().getActiveShell();
		if (activeWorkbenchParentShell == null || activeWorkbenchParentShell != activeShell)
			return activeShell;
		else {
			return WorkbenchLookup.getWorkbenchControl(activeWorkbenchReference);
		} 
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
		if (widgets.size() > index)
			widget = widgets.get(index);
		else
			throw new SWTLayerException("No matching widget found");
		
		if (widget == null) throw new SWTLayerException("Matching widget was null");
		return widget;
	}
	
	/**
	 * Finds Control for active parent control
	 * @param matcher
	 * @param recursive
	 * @return
	 */
	public<T extends Widget> List<T> findActiveParentControls(final Matcher<T> matcher, final boolean recursive) {
		return findControls(getActiveWidgetParentControl(), matcher, recursive);
	}
	
	/**
	 * Find Controls for parent widget matching
	 * @param parentWidget
	 * @param matcher
	 * @param recursive
	 * @return
	 */
	private <T extends Widget> List<T> findControls(final Widget parentWidget, 
			final Matcher<T> matcher, final boolean recursive) {
		List<T> ret = Display.syncExec(new ResultRunnable<List<T>>() {

			@Override
			public List<T> run() {
				return findControlsUI(parentWidget, matcher, recursive);
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
}

