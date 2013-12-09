package org.jboss.reddeer.swt.generator.framework.rules.complex;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ButtonRule;

public class CheckBoxFilterComplexRule extends GenerationComplexRule{
	
	
	private ButtonRule bRule;

	@Override
	public boolean appliesToPartially(GenerationSimpleRule rule, int i) {
		if(rule instanceof ButtonRule && ((((ButtonRule)rule).getStyle() & SWT.CHECK) != 0)){
			if(i == 0){
				bRule = (ButtonRule)rule;
			}
			if(bRule.equals(rule)){
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean appliesTo(List<GenerationSimpleRule> rules) {
		for(GenerationSimpleRule r: rules){
			if(!bRule.equals(r)){
				return false;
			}
		}
		return true;
	}

	@Override
	public List<String> getActions() {
		return getInitializationRules().get(getInitializationRules().size()-1).getActions();
	}

	@Override
	public List<String> getImports() {
		return bRule.getImports();
	}

}
