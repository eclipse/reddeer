package org.jboss.reddeer.swt.generator.framework.rules.complex;

import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ListRule;

public class ListFilterComplexRule extends GenerationComplexRule{
	
	private ListRule lRule;

	@Override
	public boolean appliesToPartially(GenerationSimpleRule rule, int i) {
		if(rule instanceof ListRule){
			if(i == 0){
				lRule = (ListRule)rule;
			}
			return rule.equals(lRule);
		}
		return false;
	}

	@Override
	public boolean appliesTo(List<GenerationSimpleRule> rules) {
		for(GenerationSimpleRule r: rules){
			if(!r.equals(lRule)){
				return false;
			}
		}
		return true;
	}

	@Override
	public List<String> getActions() {
		return ((ListRule)getInitializationRules().get(getInitializationRules().size()-1)).getActions();
	}

	@Override
	public List<String> getImports() {
		return ((ListRule)getInitializationRules().get(getInitializationRules().size()-1)).getImports();
	}

}
