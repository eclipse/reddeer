package org.jboss.reddeer.swt.generator.framework.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.swt.generator.framework.referencedComposite.GroupRule;
import org.jboss.reddeer.swt.generator.framework.referencedComposite.ReferencedComposite;
import org.jboss.reddeer.swt.generator.framework.referencedComposite.SectionRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellRule;

public class RedDeerUtils {
	
	private static ShellRule activeShell;
	
	public static List<ReferencedComposite> getComposites(Control widget){
		List<ReferencedComposite> toReturn = new ArrayList<ReferencedComposite>();
		widget = widget.getParent();
		if (widget != null){
			if (widget instanceof org.eclipse.ui.forms.widgets.Section) {
				org.eclipse.ui.forms.widgets.Section section = (org.eclipse.ui.forms.widgets.Section)widget;
				toReturn.add(new SectionRule(section.getText()));
			}
			else if(widget instanceof Group){
				Group group = (Group)widget;
				toReturn.add(new GroupRule(group.getText()));
			}
			toReturn.addAll(getComposites(widget));
		}
		return toReturn;
	}
	
	public static String getReferencedCompositeString(List<ReferencedComposite> composites){
		StringBuilder builder = new StringBuilder();
		for(ReferencedComposite r: composites){
			if(r instanceof SectionRule){
				builder.append("new UIFormSection(");
			} else {
				builder.append("new DefaultGroup(");
			}
		}
		Collections.reverse(composites);
		for(int i =0; i<composites.size();i++){
			builder.append("\""+WidgetUtils.cleanText(composites.get(i).getText())+"\"),");
		}
		return builder.toString();
	}
	
	public static String getSection(Control widget) {
		if(Platform.getBundle("org.eclipse.ui.forms") != null){
			while (widget != null) {
				if (widget instanceof org.eclipse.ui.forms.widgets.Section) {
					return ((org.eclipse.ui.forms.widgets.Section)widget).getText();
				} else {
					widget = widget.getParent();
				}
			}
		}
		return null;
	}

	public static Shell getWorkbench(){
		if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null){
			return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		}
		return null;
	}
	
	public static CTabItem getView(Control widget) {
		while (widget != null) {
			if (widget instanceof CTabFolder) {
				return ((CTabFolder)widget).getSelection();
			} else {
				widget = ((Control)widget).getParent();
			}
		}
		return null;
	}

	public static ShellRule getActiveShell() {
		return activeShell;
	}

	public static void setActiveShell(ShellRule activeShell) {
		RedDeerUtils.activeShell = activeShell;
	}
}
