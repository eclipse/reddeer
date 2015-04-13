package org.jboss.reddeer.spy.view.internal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.spy.view.RedDeerSpy;
import org.jboss.reddeer.spy.widget.resolver.WidgetResolver;
import org.jboss.reddeer.core.handler.WidgetHandler;

/**
 * RedDeerWidgetTracker provides tracking widgets and flushing their output onto RedDeer Spy View as styled texts.
 * Tracking currently works only for descendants of Control class but siblings, parents and children are resolved as 
 * any Widget.
 * 
 * @author mlabuda@redhat.com
 * @since 0.8.0
 */
public class RedDeerWidgetTracker implements Runnable {

	private RedDeerSpy spy;
	private WidgetResolver widgetResolver;
	// Style ranges for headlines and significant information in output
	private List<StyleRange> styleRanges;
	
	private static final String ANONYMOUS_COMPOSITE = "anonymous org.eclipse.swt.widgets.Composite";
	
	public RedDeerWidgetTracker(RedDeerSpy spy) {
		this.spy= spy;
		widgetResolver = WidgetResolver.getInstance();
	}

	@Override
	public void run() {
		if (spy.getOutput() == null || spy.getOutput().isDisposed() || !spy.getAction().isChecked()) {
			return;
		} else {
			Display display = spy.getOutput().getDisplay();
			Control control = display.getCursorControl();			
			
			if (control == null) {
				spy.getOutput().setText("");
				spy.setLastWidget(null);
			} else if (control != spy.getLastWidget()) {
				spy.setLastWidget(control);
	
				StringBuffer stringBuffer = new StringBuffer();
				styleRanges = new ArrayList<StyleRange>();
				getInformation(control, stringBuffer);
				spy.getOutput().setText(stringBuffer.toString());
				spy.getOutput().setStyleRanges(styleRanges.toArray(new StyleRange[styleRanges.size()]));
			}
			display.timerExec(70, spy.getWidgetTracker());
		}
	}
	
	private void getInformation(Control control, StringBuffer stringBuffer) {
		stringBuffer.append(RedDeerSpy.SPY_VIEW_HEADER);
		getWidgetInformation(control, stringBuffer);
		getSiblingsInformation(control, stringBuffer);
		getChildrenInformation(control, stringBuffer);
		getWidgetTreeInformation(control, stringBuffer);
	}
	
	private void getWidgetInformation(Control control, StringBuffer stringBuffer) {
		addBoldText("Widget information:", stringBuffer);
		stringBuffer.append("\n");
		
		stringBuffer.append("\t");
		addBoldText("Class:", stringBuffer);
		String output = control.getClass().getCanonicalName();
		if (output == null) {
			output = ANONYMOUS_COMPOSITE;
		}
		stringBuffer.append(" " + output + "\n");
		
		String text = getText(control);
		stringBuffer.append("\t");
		addBoldText("Text:", stringBuffer);
		if (text != null) {
			stringBuffer.append(" \"" + text + "\"\n");
		} else {
			stringBuffer.append("\n");
		}

		stringBuffer.append("\t");
		addBoldText("Possible RedDeer API for widget:", stringBuffer);
		stringBuffer.append("\n");
		StringBuilder redDeerWidgets = new StringBuilder();
		for (String widget: getPossibleRedDeerWidget(control)) {
			redDeerWidgets.append("\t\t" + widget + "\n");
		}
		stringBuffer.append(redDeerWidgets.toString() + "\n");
	}
	
	private void getSiblingsInformation(Control control, StringBuffer stringBuffer) {
		addBoldText("Siblings:", stringBuffer);
		stringBuffer.append("\n");
		
		Widget parent = widgetResolver.getParent(control);
		List<Widget> siblingWidgets = new ArrayList<Widget>();
		if (parent != null) {
			siblingWidgets = widgetResolver.getChildren(parent);
		}
		
		if (siblingWidgets.size() > 1) {
			for (Widget widget: siblingWidgets) {
				if (!widget.equals(control)) {
					stringBuffer.append(getWidgetOutput(widget) + "\n");
				}
			}
		}		
		stringBuffer.append("\n");
	}
	
	private void getWidgetTreeInformation(Control control, StringBuffer stringBuffer) {
		addBoldText("Widget tree:", stringBuffer);
		stringBuffer.append("\n");
		
		boolean hasSiblings = false;
		if (widgetResolver.getParent(control) != null) {
			hasSiblings = widgetResolver.getChildren(widgetResolver.getParent(control)).size() > 0;
		}		
		
		// Add parent info
		Widget parentWidget = control;
		List<String> parentTree = new LinkedList<String>();
		while (widgetResolver.hasParent(parentWidget)) {
			parentWidget = widgetResolver.getParent(parentWidget);
			parentTree.add(getWidgetOutput(parentWidget));
		}
		if (parentTree.size() > 0) {
			stringBuffer.append(parentTree.get(parentTree.size()-1) + "\n");
		}
		String spacing = "";
		for (int i = parentTree.size()-2; i > 0; i--) {
			stringBuffer.append(spacing + "└─" + parentTree.get(i) + "\n");
			spacing += "  ";
		}
		
		// Add bold widget info
		if (hasSiblings) {
			stringBuffer.append(spacing + "├─");
		} else {
			stringBuffer.append(spacing + "└─");
		}
		if (parentTree.size() > 0) {
			addBoldText(parentTree.get(0), stringBuffer);
		} else {
			addBoldText(getWidgetOutput(control), stringBuffer);
		}
		stringBuffer.append("\n");

		// Add children info
		List<Widget> children = widgetResolver.getChildren(control);
		String childrenSpacing = hasSiblings ? spacing + "│ " : spacing + "  ";
		for (int i = 0; i < children.size(); i++) {
			if (i == children.size() - 1) {
				stringBuffer.append(childrenSpacing + "└─" + getWidgetOutput(children.get(i)) + "\n");
			} else {
				stringBuffer.append(childrenSpacing + "├─" + getWidgetOutput(children.get(i)) + "\n");
			}
		}
		
		// Add siblings info
		List<Widget> siblings = hasSiblings ? 
				widgetResolver.getChildren(widgetResolver.getParent(control)) : new ArrayList<Widget>();
		for (int i = 0; i < siblings.size(); i++) {
			if (i == siblings.size() - 1) {
				stringBuffer.append(spacing + "└─" + getWidgetOutput(siblings.get(i)) + "\n");
			} else {
				stringBuffer.append(spacing + "├─" + getWidgetOutput(siblings.get(i)) + "\n");
			}
		}
	}
	
	private void getChildrenInformation(Control control, StringBuffer stringBuffer) {
		addBoldText("Children:", stringBuffer);
		stringBuffer.append("\n");
		
		for (Widget widget: widgetResolver.getChildren(control)) {
			String output = widget.getClass().getCanonicalName();
			if (output == null) {
				output = ANONYMOUS_COMPOSITE;
			}
			if (widget instanceof Control) {
				String childText = getText((Control) widget);
				if (childText != null) {
					output += " [\"" + childText + "\"]";
				}
			}
			stringBuffer.append(output + "\n");
		}
		stringBuffer.append("\n");
	}
	
	private void addBoldText(String text, StringBuffer stringBuffer) {
		addStyleRange(stringBuffer.length(), text.length());
		stringBuffer.append(text);
	}
	
	// Add bold style range for text
	private void addStyleRange(int startPosition, int length) {
		StyleRange newStyle = new StyleRange();
		newStyle.start = startPosition;
		newStyle.length = length;
		newStyle.fontStyle = SWT.BOLD;
		styleRanges.add(newStyle);
	}
	
	// Mapping of RedDeer widget to Eclipse widgets
	// List is used because there could be more mapped RedDeer widgets to one Eclipse widget
	private List<String> getPossibleRedDeerWidget(Control control) {
		List<String> widgets = new ArrayList<String>();
		Class<? extends Control> clazz = control.getClass();
		if (clazz.equals(Browser.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Browser.class.getCanonicalName());
		} else if (clazz.equals(Button.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Button.class.getCanonicalName());
		} else if (clazz.equals(CLabel.class)) {
			widgets.add(org.jboss.reddeer.swt.api.CLabel.class.getCanonicalName());
		} else if (clazz.equals(Combo.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Combo.class.getCanonicalName());
		} else if (clazz.equals(ExpandBar.class)) {
			widgets.add(org.jboss.reddeer.swt.api.ExpandBar.class.getCanonicalName());
		} else if (clazz.equals(Group.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Group.class.getCanonicalName());
		} else if (clazz.equals(Label.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Label.class.getCanonicalName());
		} else if (clazz.equals(Link.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Link.class.getCanonicalName());
		} else if (clazz.equals(org.eclipse.swt.widgets.List.class)) {
			widgets.add(org.jboss.reddeer.swt.api.List.class.getCanonicalName());
		} else if (clazz.equals(Menu.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Menu.class.getCanonicalName());
		} else if (clazz.equals(ProgressBar.class)) {
			widgets.add(org.jboss.reddeer.swt.api.ProgressBar.class.getCanonicalName());
		} else if (clazz.equals(Scale.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Scale.class.getCanonicalName());
		} else if (clazz.equals(Shell.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Shell.class.getCanonicalName());
		} else if (clazz.equals(Spinner.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Spinner.class.getCanonicalName());
		} else if (clazz.equals(StyledText.class)) {
			widgets.add(org.jboss.reddeer.swt.api.StyledText.class.getCanonicalName());
		} else if (clazz.equals(TabFolder.class)) {
			widgets.add(org.jboss.reddeer.swt.api.TabFolder.class.getCanonicalName());
		} else if (clazz.equals(Table.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Table.class.getCanonicalName());
		} else if (clazz.equals(Text.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Text.class.getCanonicalName());
		} else if (clazz.equals(ToolBar.class)) {
			widgets.add(org.jboss.reddeer.swt.api.ToolBar.class.getCanonicalName());
		} else if (clazz.equals(Tree.class)) {
			widgets.add(org.jboss.reddeer.swt.api.Tree.class.getCanonicalName());
		}
		return widgets;
	}
	
	private String getText(Control widget) {
		WidgetHandler widgetHandler = WidgetHandler.getInstance();
		String text = null;
		try {
			text = widgetHandler.getText(widget);
		} catch (RedDeerException ex) {	
		}
		return text;
	}
	
	private String getWidgetOutput(Widget widget) {
		String output = widget.getClass().getCanonicalName();
		if (output == null) {
			output = ANONYMOUS_COMPOSITE;
		}
		if (widget instanceof Control) {
			String widgetText = getText((Control) widget);
			if (widgetText != null) {
				output += " [\"" + widgetText + "\"]";
			}
		}
		return output;
	}	
}
