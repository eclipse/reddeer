package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.jboss.reddeer.swt.generator.framework.referencedComposite.ReferencedComposite;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;

public class ComboRule extends GenerationSimpleRule{

	private String text;
	private String label;
	private int index;
	private int selection;
	private List<ReferencedComposite> composites;
	
	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Combo && event.type == SWT.Modify;
	}

	@Override
	public void initializeForEvent(Event event) {  // check combo style and decide if setText or setSelection is better ?
		Combo c = (Combo) event.widget;
		this.setText(c.getText());
		this.setLabel(WidgetUtils.getLabel(c));
		this.setIndex(WidgetUtils.getIndex(c));
		this.setSelection(c.getSelectionIndex());
		this.setComposites(RedDeerUtils.getComposites(c));
		Shell s = WidgetUtils.getShell((Combo)event.widget);
		if(s!=null){
			setShellTitle(s.getText());
		}
	}

	@Override
	public List<String> getActions() {
		StringBuilder builder = new StringBuilder();
		List<String> toReturn = new ArrayList<String>();
		builder.append("new DefaultCombo(");
		builder.append(RedDeerUtils.getReferencedCompositeString(composites));
		if(label != null){
			builder.append("\""+label+"\"");
		} else {
			builder.append(index);
		}
		builder.append(")");
		
		if(selection >= 0){
			builder.append(".setSelection("+selection+")");
		} else {
			builder.append(".setText(\""+text+"\")");
		}
		toReturn.add(builder.toString());
		return toReturn;
		
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.combo.DefaultCombo");
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
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
		ComboRule other = (ComboRule) obj;
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
