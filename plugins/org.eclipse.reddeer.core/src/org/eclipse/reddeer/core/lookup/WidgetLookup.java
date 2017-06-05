/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.core.lookup;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.AndMatcher;
import org.eclipse.reddeer.common.matcher.MatcherBuilder;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ObjectUtil;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.handler.ShellHandler;
import org.eclipse.reddeer.core.matcher.ClassMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.core.resolver.WidgetResolver;
import org.eclipse.reddeer.core.util.DiagnosticTool;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchPartLookup;

/**
 * Widget lookup provides methods for looking up eclipse widgets.
 * 
 * @author Jiri Peterka
 * @author Jaroslav Jankovic
 */
public class WidgetLookup {

	private static WidgetLookup instance = null;
	private static final Logger logger = Logger.getLogger(WidgetLookup.class);

	private WidgetLookup() {
	}

	/**
	 * Gets instance of WidgetLookup.
	 * 
	 * @return WidgetLookup instance
	 */
	public static WidgetLookup getInstance() {
		if (instance == null) instance = new WidgetLookup();
		return instance;
	}

	/**
	 * Method looks for active widget located in specified referenced composite, laying on specified index and matching specified matchers.
	 *
	 * @param <T> the generic type
	 * @param refComposite reference composite to search for widgets
	 * @param clazz class type of widget
	 * @param index index of widget within referenced composite
	 * @param matchers matchers to match widget
	 * @return widget located withing specified referenced composite, laying on specified index and matching specified matchers
	 */
	@SuppressWarnings("rawtypes")
	public <T extends Widget> T activeWidget(ReferencedComposite refComposite, Class<T> clazz, int index, Matcher... matchers) {				
		return activeWidget(refComposite, clazz, index, TimePeriod.SHORT , matchers);
	}
	
	/**
	 * Method looks for active widget located in specified referenced composite, laying on specified index and matching specified matchers.
	 *
	 * @param <T> the generic type
	 * @param refComposite reference composite to search for widgets
	 * @param clazz class type of widget
	 * @param index index of widget within referenced composite
	 * @param timePeriod defines how long should we wait for widget to be found
	 * @param matchers matchers to match widget
	 * @return widget located withing specified referenced composite, laying on specified index and matching specified matchers
	 */
	@SuppressWarnings({ "rawtypes","unchecked" })
	public <T extends Widget> T activeWidget(ReferencedComposite refComposite, Class<T> clazz, int index, TimePeriod timePeriod, Matcher... matchers) {				
		logger.debug("Looking up active widget with class type " + clazz.getName() +  ", index " + index + " and " + createMatcherDebugMsg(matchers));

		Control parentControl = getParentControl(refComposite);
		WidgetIsFound found = new WidgetIsFound(clazz, parentControl, index, matchers);
		try{
			new WaitUntil(found, timePeriod);
		} catch (WaitTimeoutExpiredException ex){
			String exceptionText = "No matching widget found with " + found.getAndMatcher().toString();
			exceptionText += "\n" + new DiagnosticTool().getDiagnosticInformation(parentControl);
			logger.error("Active widget with class type " + clazz.getName() +  " and index " + index + " was not found");
			throw new CoreLayerException(exceptionText, ex);
		}
		logger.debug("Active widget with class type " + clazz.getName() +  " and index " + index + " was found");
		return (T)found.getWidget();
	}
	
	/**
	 * Method looks for active widgets located in specified referenced composite and matching specified matchers.
	 *
	 * @param <T> the generic type
	 * @param refComposite reference composite to search for widgets
	 * @param clazz class type of widgets
	 * @param matchers matchers to match widget
	 * @return widgets located in specified referenced composite and matching specified matchers
	 */
	public <T extends Widget> List<T> activeWidgets(ReferencedComposite refComposite, Class<T> clazz, Matcher<?>... matchers) {				
		logger.debug("Looking up active widgets with class type " + clazz.getName() +  " and " + createMatcherDebugMsg(matchers));

		ClassMatcher cm = new ClassMatcher(clazz);
		Matcher<?>[] allMatchers = MatcherBuilder.getInstance().addMatcher(matchers, cm);
		AndMatcher am  = new AndMatcher(allMatchers);

		List<T> foundWidgets = activeWidgets(refComposite.getControl(), am);
		logger.debug("Found " + foundWidgets.size() + " widgets");
		return foundWidgets;
	}

	private Control getParentControl(ReferencedComposite refComposite){
		if (refComposite == null){
			return findParent();
		}
		return refComposite.getControl();
	}

	/**
	 * Finds parent control of active widget .
	 *
	 * @return parent control of active widget  or throws an exception if null
	 */
	public Control findParent(){
		logger.debug("No parent specified, finding one");
		Control parent = getActiveWidgetParentControl();
		logger.debug("Parent found successfully");

		if (parent == null){
			logger.error("Unable to determine active parent");
			throw new CoreLayerException("Unable to determine active parent");
		}

		return parent;
	}

	/**
	 * Finds active widget or reference composite matching given matcher.
	 *
	 * @param <T> the generic type
	 * @param refComposite given reference composite
	 * @param matcher given matcher
	 * @return active widget
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Widget> List<T> activeWidgets(Control refComposite, Matcher matcher) {
		logger.trace("Looking up widgets with specified parent and matchers");
		List<T> widgets = findControls(refComposite, matcher, true);
		logger.trace(widgets.size() + " widget(s) found");
		return widgets;
	}

	/**
	 * Finds active widget or reference composite matching given matcher with given index in the list of all matching widgets.
	 * 
	 * @param <T> type of a widget
	 * @param refComposite given reference composite
	 * @param matcher given matcher
	 * @param index index of widget in the list built by activeWidgets(Control, Matcher) method.
	 * @return active widget
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Widget> T activeWidget(Control refComposite, Matcher matcher, int index) {
		String widgetDescription = "widget with index " + index;
		logger.trace("Looking up " + widgetDescription + " with specified parent and matchers");
		T widget = (T)findControl(refComposite, matcher, true, index);
		logger.trace(widgetDescription + (widget != null ? " is found" : "is not found"));
		return widget;
	}

	/**
	 * Finds out whether extra shell (shell different than workbench shell) is active.
	 *  
	 * @return true if extra shell is active, false otherwise
	 */
	public boolean isExtraShellActive() {
		Shell activeWorkbenchParentShell = null;
		if(getWorkbenchLookup() != null){
			activeWorkbenchParentShell = getWorkbenchLookup().getShellForActiveWorkbench();
		}

		Shell activeShell = ShellLookup.getInstance().getActiveShell();
		if (activeWorkbenchParentShell == null || activeWorkbenchParentShell != activeShell){
			return true;
		}
		return false;
	}


	/**
	 * Gets active parent control. Method finds either active workbench referenced control active shell. 
	 *
	 * @return active workbench control or active shell
	 */
	public Control getActiveWidgetParentControl() {
		Control control = null;
		
		Shell activeWorkbenchParentShell = null;
		if(getWorkbenchLookup() != null){
			activeWorkbenchParentShell = getWorkbenchLookup().getShellForActiveWorkbench();
		}
		
		Shell activeShell = ShellLookup.getInstance().getActiveShell();
		if(activeShell == null){
			logger.trace("No active shell found");
		}

		if ((activeWorkbenchParentShell == null || !activeWorkbenchParentShell.equals(activeShell))
				&& activeShell != null){
			logger.trace("Setting active shell with title \"" + ShellHandler.getInstance().getText(activeShell) + "\" as the parent");
			control = activeShell;	
		}			
		else {
			if (getWorkbenchLookup() != null && getWorkbenchLookup().getActiveWorkbenchPartTitle() != null){
				logger.trace("Setting workbench part with title \"" + getWorkbenchLookup().getActiveWorkbenchPartTitle() + "\"as the parent");
				control = getWorkbenchLookup().getActiveWorkbenchPartControl();
			}
		}	
		return control;
	}

	/**
	 * Get widget with given index from list of widgets .
	 *
	 * @param <T> the generic type
	 * @param widgets list of widgets
	 * @param index widget index
	 * @return widget with given index or null if out of range
	 */
	public <T extends Widget> T getProperWidget(List<T> widgets, int index) {
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
	 * Finds list of controls matching specified matcher for active parent widget.
	 *
	 * @param <T> the generic type
	 * @param matcher matcher to match parent controls
	 * @param recursive true for recursive lookup of control widgets
	 * @return list of parent controls for active parent or single parent control
	 */
	public <T extends Widget> List<T> findActiveParentControls(final Matcher<T> matcher, final boolean recursive) {
		List<T> findControls = findControls(getActiveWidgetParentControl(), matcher, recursive);
		return findControls;
	}

	/**
	 * Finds list of controls matching specified matchers for parent widget.
	 * 
	 * @param parentWidget parent widget to search for controls
	 * @param matcher matcher to match controls
	 * @param recursive true for recursive lookup
	 * @return list of control widgets matching specified matchers of single parent control
	 */
	private <T extends Widget> List<T> findControls(final Widget parentWidget, 
			final Matcher<T> matcher, final boolean recursive) {
		return findControlsUI(parentWidget, matcher, recursive);
	}

	private <T extends Widget> T findControl(final Widget parentWidget, 
			final Matcher<T> matcher, final boolean recursive, final int index) {
		return findControlUI(parentWidget, matcher, recursive, new Index(index));
	}

	private static class Index {
		private int value;

		public Index(int index) {
			value = index;
		}

		public boolean isFirst() {
			return value <= 0;
		}

		public void passed() {
			value--;
		}
	}

	/**
	 * Gets control with focus.
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
	 * Gets list of children control widgets located within specified 
	 * parent widget matching specified matcher.
	 * 
	 * @param parentWidget parent widget
	 * @param matcher matcher to match widgets
	 * @param recursive true for recursive search, false otherwise
	 * @return children control widget matching specified matcher
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
			List<Widget> children = Display.syncExec(new ResultRunnable<List<Widget>>() {

				@Override
				public List<Widget> run() {
					List<Widget> children = null;
					try{
						children = WidgetResolver.getInstance().getChildren(parentWidget);
					} catch (SWTException e) {
						if(!parentWidget.isDisposed()){
							throw e;
						}
						//otherwise ok, parentWidget is disposed so it has no children
					}
					return children;
				}
			});
			controls.addAll(findControlsUI(children, matcher, recursive));
		}
		return new ArrayList<T>(controls);
	}

	@SuppressWarnings("unchecked")
	private <T extends Widget> T findControlUI(final Widget parentWidget, final Matcher<T> matcher, final boolean recursive, Index index) {
		if ((parentWidget == null) || parentWidget.isDisposed() || !visible(parentWidget)) {
			return null;
		}

		if (matcher.matches(parentWidget))
			try {
				T control = (T) parentWidget;
				if(index.isFirst()) {
					return control;
				} else {
					index.passed();
				}
			} catch (ClassCastException exception) {
				throw new IllegalArgumentException("The specified matcher should only match against is declared type.", exception);
			}
		if (recursive) {
			List<Widget> children = Display.syncExec(new ResultRunnable<List<Widget>>() {

				@Override
				public List<Widget> run() {
					return WidgetResolver.getInstance().getChildren(parentWidget);
				}
			});
			return findControlUI(children, matcher, recursive, index);
		}
		return null;
	}

	/**
	 * Gets list of children control widgets matching specified matcher from specified list of widgets. Method
	 * can be used recursively to get all children in descendants.
	 * 
	 * Note: Must be used in UI Thread
	 * 
	 * @param widgets list of widgets to get children from
	 * @param matcher matcher to match widgets
	 * @param recursive true for recursive search, false otherwise
	 * @return list of children widgets of widgets from specified list
	 */
	private <T extends Widget> List<T> findControlsUI(final List<Widget> widgets, final Matcher<T> matcher, final boolean recursive) {
		LinkedHashSet<T> list = new LinkedHashSet<T>();
		for (Widget w : widgets) {
			list.addAll(findControlsUI(w, matcher, recursive));
		}
		return new ArrayList<T>(list);
	}

	private <T extends Widget> T findControlUI(final List<Widget> widgets, final Matcher<T> matcher, final boolean recursive, Index index) {
		for (Widget w : widgets) {
			T control = findControlUI(w, matcher, recursive, index);
			if(control != null) {
				return control;
			}
		}
		return null;
	}

	/**
	 * Finds out whether widget is visible or not.
	 * 
	 * @param w widget to resolve
	 * @return true if widget is visible, false otherwise
	 */
	private boolean visible(final Widget w) {
		if (w.isDisposed()) {
			return false;
		}
		
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return !((w instanceof Control) && !((Control) w).getVisible());
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

	/**
	 * Find all parent widgets.
	 *
	 * @return the list
	 */
	public List<Control> findAllParentWidgets() {
		List<Control> allWidgets = findControls(findParent(), new BaseMatcher<Control>() {

			@Override
			public boolean matches(Object obj) {
				return true;
			}

			@Override
			public void describeTo(Description desc) {
				
			}
			
		}, true);
		return allWidgets;
	}
	
	private WorkbenchPartLookup getWorkbenchLookup(){
		try{
			WorkbenchPartLookup wp = WorkbenchPartLookup.getInstance();
			return wp;
		} catch (NoClassDefFoundError e) {
			logger.trace("Workbench not available");
			return null;
		}
	}
	
	/**
	 * Gets label of specified widget.
	 *
	 * @param <T> the generic type
	 * @param w widget to handle
	 * @return label of specified widget
	 */
	public <T extends Widget> String getLabel(final T w) {
		String label = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				Control parent = ((Control) w).getParent();
				java.util.List<Widget> children = WidgetResolver.getInstance()
						.getChildren(parent);
				// check whether a label is defined using form data layout
				for (Widget child : children) {
					if (child instanceof Label || child instanceof CLabel) {
						Object layoutData = ((Control) child).getLayoutData();
						if (layoutData instanceof FormData) {
							FormData formData = (FormData) layoutData;
							if (formData.right != null && w.equals(formData.right.control)) {
								if (child instanceof Label) {
									return ((Label) child).getText();
								} else if (child instanceof CLabel) {
									return ((CLabel) child).getText();
								}
							}
						}
					}
				}
				return null;
			}
		});
		
		if(label == null){
			final List<Control> allWidgets = WidgetLookup.getInstance().findAllParentWidgets();
			label = Display.syncExec(new ResultRunnable<String>() {

				@Override
				public String run() {
					int widgetIndex = allWidgets.indexOf(w);
					if (widgetIndex < 0) {
						return null;
					}
					ListIterator<? extends Widget> listIterator = allWidgets.listIterator(widgetIndex);
					while (listIterator.hasPrevious()) {
						Widget previousWidget = listIterator.previous();
						if (previousWidget instanceof Label) {
							Label label = (Label) previousWidget;
							if (label.getImage() == null) {
								return label.getText();
							}
						}
						if (previousWidget instanceof CLabel) {
							CLabel cLabel = (CLabel) previousWidget;
							if (cLabel.getImage() == null) {
								return cLabel.getText();
							}
						}
					}
					return null;
				}
			});
		}
		if (label != null) {
			label = label.replaceAll("&", "").split("\t")[0];
		}
		return label;
	}
	
	/**
	 * Gets path to widget within widget tree including widget getting path for
	 * as last element of returned list.
	 *
	 * @param widget widget to get path for
	 * @param classFilter optional array of classes included in returned list
	 * @return ordered list of widgets
	 */
	public List<Widget> getPathToWidget(final Widget widget, final Class<?>... classFilter) {
		final Control firstParent = getParent(widget);
		List<Widget> parents = Display.syncExec(new ResultRunnable<List<Widget>>() {
			@Override
			public List<Widget> run() {
				LinkedList<Widget> result = new LinkedList<Widget>();
				if (isClassOf(widget.getClass(), classFilter)){
					result.add(widget);
				}
				Control control = firstParent;
				while (control != null){
					if (isClassOf(control.getClass(), classFilter)){
						result.addFirst(control);
					}
					control = control.getParent();
				}
				return result;
			}
		});
		return parents;
	}
	
	private boolean isClassOf(Class<?> clazz,Class<?>[] classes){
		boolean filterPassed = false;
		if (classes != null && classes.length > 0){
			int index = 0;
			while (!filterPassed && index < classes.length){
				if (clazz.getName().equals(classes[index].getName())){
					filterPassed = true;
				}
				index++;
			}
		}
		else{
			filterPassed = true;
		}
		
		return filterPassed;
	}
	
	/**
	 * Gets parent of specified widget.
	 * 
	 * @param widget widget to find parent
	 * @return parent widget of specified widget
	 */
	public Control getParent(final Widget widget) {
		Object o = ObjectUtil.invokeMethod(widget, "getParent");

		if (o == null){
			return null;
		}

		if (o instanceof Control) {
			return (Control) o;
		}

		throw new RedDeerException(
				"Return value of method getObject() on class " + o.getClass()
						+ " should be Control, but was " + o.getClass());
	}

}

