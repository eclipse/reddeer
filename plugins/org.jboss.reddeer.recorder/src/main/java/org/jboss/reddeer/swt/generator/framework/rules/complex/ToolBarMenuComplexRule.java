package org.jboss.reddeer.swt.generator.framework.rules.complex;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ContextMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ToolBarRule;

public class ToolBarMenuComplexRule extends GenerationComplexRule{
	
	private List<GenerationSimpleRule> rules;
	
	public ToolBarMenuComplexRule(){
		rules = new ArrayList<GenerationSimpleRule>();
		
		ToolBarRule toolBar = new ToolBarRule();
		ContextMenuRule menu = new ContextMenuRule();
		
		rules.add(toolBar);
		rules.add(menu);
	}
	
	@Override
	public List<String> getActions(){
		String parent = ((ToolBarRule)getInitializationRules().get(0)).getToolTipText();
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		builder.append("new ToolbarMenu(");
		builder.append("\""+parent+"\"");
		for(String path: ((ContextMenuRule)getInitializationRules().get(1)).getPath()){
			builder.append(",\""+path+"\"");
		}
		builder.append(",\""+((ContextMenuRule)getInitializationRules().get(1)).getMenu()+"\")");
		builder.append(".select()");
		toReturn.add(builder.toString());
		return toReturn;
	}
	

	@Override
	public boolean appliesToPartially(GenerationSimpleRule rule, int i) {
		if(this.rules.size() > i){
			return this.rules.get(i).getClass().equals(rule.getClass());
		}
		return false;
	}

	@Override
	public boolean appliesTo(List<GenerationSimpleRule> rules) {
		if(rules.size() != this.rules.size()){
			return false;
		}
		for(int i=0;i<rules.size();i++){
			if(this.rules.get(i).getClass() !=  rules.get(i).getClass()){
				return false;
			}
		}
		return true;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
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
		ToolBarMenuComplexRule other = (ToolBarMenuComplexRule) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}

	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.menu.ToolbarMenu");
		return toReturn;
	}

}
