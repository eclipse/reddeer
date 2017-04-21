/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.generator.framework.rules.complex;

import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.reddeer.swt.generator.framework.rules.simple.ListRule;

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
