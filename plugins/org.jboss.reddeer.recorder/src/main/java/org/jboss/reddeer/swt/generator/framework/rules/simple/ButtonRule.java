package org.jboss.reddeer.swt.generator.framework.rules.simple;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.jboss.reddeer.swt.generator.framework.referencedComposite.ReferencedComposite;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;

public class ButtonRule extends GenerationSimpleRule {

	private String text;
	private int index;
	private int style;
	private boolean toggle;
	private List<ReferencedComposite> composites;
	
	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Button && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.text = WidgetUtils.cleanText(((Button)event.widget).getText());
		this.composites = RedDeerUtils.getComposites((Button)event.widget);
		this.index = WidgetUtils.getIndex((Button)event.widget);
		Shell s = WidgetUtils.getShell((Button)event.widget);
		if(s!=null){
			setShellTitle(s.getText());
		}
		this.style = ((Button)event.widget).getStyle();
		if((style & SWT.CHECK) != 0){
			this.toggle=((Button)event.widget).getSelection();
		}
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		if((style & SWT.PUSH)!=0){
			builder.append("new PushButton(");
		} else if((style & SWT.CHECK)!=0){
			builder.append("new CheckBox(");
		} else if((style & SWT.ARROW)!=0){
			builder.append("new ArrowButton(");
		} else if((style & SWT.RADIO)!=0){
			builder.append("new RadioButton(");
		} else if((style & SWT.TOGGLE)!=0){
			builder.append("new ToggleButton(");
		}
		builder.append(RedDeerUtils.getReferencedCompositeString(composites));
		if(text == null || text.isEmpty()){
			builder.append(index);
		} else {
			builder.append("\""+text+"\"");
		}
		builder.append(")");
		if((style & SWT.CHECK)!=0){
			builder.append(".toggle("+toggle+")");
		} else {
			builder.append(".click()");
		}
		toReturn.add(builder.toString());
		return toReturn;
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		for(ReferencedComposite r: composites){
			toReturn.add(r.getImport());
		}
		
		if((style & SWT.PUSH)!=0){
			toReturn.add("org.jboss.reddeer.swt.impl.button.PushButton");
		} else if((style & SWT.CHECK)!=0){
			toReturn.add("org.jboss.reddeer.swt.impl.button.CheckBox");
		} else if((style & SWT.ARROW)!=0){
			toReturn.add("org.jboss.reddeer.swt.impl.button.ArrowButton");
		} else if((style & SWT.RADIO)!=0){
			toReturn.add("org.jboss.reddeer.swt.impl.button.RadioButton");
		} else if((style & SWT.TOGGLE)!=0){
			toReturn.add("org.jboss.reddeer.swt.impl.button.ToggleButton");
		}
		return toReturn;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean getToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public List<ReferencedComposite> getComposites() {
		return composites;
	}

	public void setComposites(List<ReferencedComposite> composites) {
		this.composites = composites;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((composites == null) ? 0 : composites.hashCode());
		result = prime * result + index;
		result = prime * result + style;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		ButtonRule other = (ButtonRule) obj;
		if (composites == null) {
			if (other.composites != null)
				return false;
		} else if (!composites.equals(other.composites))
			return false;
		if (index != other.index)
			return false;
		if (style != other.style)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	
	

}
