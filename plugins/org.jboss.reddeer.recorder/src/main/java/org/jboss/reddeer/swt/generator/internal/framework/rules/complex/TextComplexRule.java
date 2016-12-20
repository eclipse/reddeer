/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.generator.internal.framework.rules.complex;

import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.jboss.reddeer.swt.generator.internal.framework.rules.simple.TextRule;

public class TextComplexRule extends GenerationComplexRule{
	
	private TextRule tRule;

	@Override
	public boolean appliesToPartially(GenerationSimpleRule rule, int i) {
		if(rule instanceof TextRule){
			if(i == 0){
				tRule = (TextRule)rule;
			}
			return rule.equals(tRule);
		}
		return false;
	}

	@Override
	public boolean appliesTo(List<GenerationSimpleRule> rules) {
		for(GenerationSimpleRule r: rules){
			if(!r.equals(tRule)){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public List<String> getActions() {
		return getInitializationRules().get(getInitializationRules().size()-1).getActions();
	}
	
	public TextRule getTRule(){
		return tRule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tRule == null) ? 0 : tRule.hashCode());
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
		TextComplexRule other = (TextComplexRule) obj;
		if (tRule == null) {
			if (other.tRule != null)
				return false;
		} else if (!tRule.equals(other.tRule))
			return false;
		return true;
	}

	@Override
	public List<String> getImports() {
		return tRule.getImports();
	}

	
	

}
