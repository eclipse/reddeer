package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.jboss.reddeer.swt.generator.framework.referencedComposite.ReferencedComposite;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;

public class TextRule extends GenerationSimpleRule{
	
	private String text;
	private int index;
	private String label;
	private java.util.List<ReferencedComposite> composites;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Text && event.type==SWT.Modify && !((Text)event.widget).getText().equals("") &&
				!((Text)event.widget).getMessage().equals(((Text)event.widget).getText());
	}

	@Override
	public void initializeForEvent(Event event) {
		this.setText(((Text)event.widget).getText());
		this.setIndex(WidgetUtils.getIndex((Text)event.widget));
		Shell s = WidgetUtils.getShell((Text)event.widget);
		if(s!=null){
			setShellTitle(s.getText());
		}
		this.setLabel(WidgetUtils.getLabel((Text)event.widget));
		this.setComposites(RedDeerUtils.getComposites((Text)event.widget));	
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		if(label != null){
			builder.append("new LabeledText(");
			builder.append(RedDeerUtils.getReferencedCompositeString(composites));
			builder.append("\"label\"");
		} else {
			builder.append("new DefaultText(");
			builder.append(RedDeerUtils.getReferencedCompositeString(composites));
			builder.append(index);
		}
		builder.append(").setText(\""+text+"\")");
		toReturn.add(builder.toString());
		return toReturn;
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		if(label!=null){
			toReturn.add("org.jboss.reddeer.swt.impl.text.LabeledText");
		} else {
			toReturn.add("org.jboss.reddeer.swt.impl.text.DefaultText");
		}
		for(ReferencedComposite r: composites){
			toReturn.add(r.getImport());
		}
		return toReturn;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public java.util.List<ReferencedComposite> getComposites() {
		return composites;
	}

	public void setComposites(java.util.List<ReferencedComposite> composites) {
		this.composites = composites;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((composites == null) ? 0 : composites.hashCode());
		result = prime * result + index;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		TextRule other = (TextRule) obj;
		if (composites == null) {
			if (other.composites != null)
				return false;
		} else if (!composites.equals(other.composites))
			return false;
		if (index != other.index)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	



}
