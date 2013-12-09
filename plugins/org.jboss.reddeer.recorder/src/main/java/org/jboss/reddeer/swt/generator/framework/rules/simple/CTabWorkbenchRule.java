package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.listener.WorkbenchListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartReference;

public class CTabWorkbenchRule extends GenerationSimpleRule{

	private String text;
	private int detail;
	private boolean view;
	
	@Override
	public boolean appliesTo(Event event) {
		return event.widget == null && event.data instanceof IWorkbenchPartReference && event.type==SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		IWorkbenchPartReference arg0 = (IWorkbenchPartReference)event.data;
		if (arg0.getPart(false) instanceof IViewPart){
			this.view = true;
		} else{
			this.view = false;
		}
		this.text = (String)arg0.getPartName();
		this.detail=event.detail;
		
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		if(view){
			builder.append("new WorkbenchView(\""+text+"\")");
			if(detail==WorkbenchListener.PART_CLOSED){
				builder.append(".close()");
			}
			toReturn.add(builder.toString());
		}
		return toReturn;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDetail() {
		return detail;
	}

	public void setDetail(int detail) {
		this.detail = detail;
	}
	
	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + detail;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + (view ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CTabWorkbenchRule other = (CTabWorkbenchRule) obj;
		if (detail != other.detail)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (view != other.view)
			return false;
		return true;
	}

	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.workbench.view.impl.WorkbenchView");
		return toReturn;
	}

}
