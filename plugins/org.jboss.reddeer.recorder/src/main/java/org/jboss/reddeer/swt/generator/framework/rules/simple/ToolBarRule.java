package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;

public class ToolBarRule extends GenerationSimpleRule{
	
	private String toolTipText;
	public static final int WORKBENCH=1;
	public static final int VIEW=2;
	public static final int SHELL=3;
	private int type;
	

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof ToolItem && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.toolTipText = ((ToolItem)event.widget).getToolTipText();
		Shell s = WidgetUtils.getShell(((ToolItem)event.widget).getParent());
		if(s!=null){
			setShellTitle(s.getText());
		}
		CTabItem v = RedDeerUtils.getView(((ToolItem)event.widget).getParent());
		Shell workbench = RedDeerUtils.getWorkbench();
		if(v!=null){
			setViewTitle(v.getText());
		}
		if(getViewTitle() != null){
			type=VIEW;
		} else if(workbench!= null && getShellTitle() != workbench.getText()){
			type=SHELL;
		} else {
			type=WORKBENCH;
		}
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		if(type==WORKBENCH){
			builder.append("new WorkbenchToolItem(");
		} else if (type==VIEW){
			builder.append("new ViewToolItem(");
		} else if(type==SHELL){
			builder.append("new ShellToolItem(");
		}
		builder.append("\""+toolTipText+"\").click()");
		toReturn.add(builder.toString());
		return toReturn;
		
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		if(type==WORKBENCH){
			toReturn.add("org.jboss.reddeer.swt.impl.toolbar.WorkbenchToolItem");
		} else if (type==VIEW){
			toReturn.add("org.jboss.reddeer.swt.impl.toolbar.ViewToolItem");
		} else if(type==SHELL){
			toReturn.add("org.jboss.reddeer.swt.impl.toolbar.ShellToolItem");
		}
		return toReturn;
	}

	public String getToolTipText() {
		return toolTipText;
	}

	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
