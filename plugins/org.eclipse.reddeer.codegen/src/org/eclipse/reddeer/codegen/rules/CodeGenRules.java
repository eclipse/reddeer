/*******************************************************************************
 * Copyright (C) 2017 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.reddeer.codegen.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.reddeer.codegen.rules.simple.ButtonCodeGenRule;
import org.eclipse.reddeer.codegen.rules.simple.ComboCodeGenRule;
import org.eclipse.reddeer.codegen.rules.simple.ShellCodeGenRule;
import org.eclipse.reddeer.codegen.rules.simple.TextCodeGenRule;
import org.eclipse.reddeer.swt.generator.framework.rules.RedDeerSWTGeneratorRules;

/**
 * This class contains all available rules for RedDeer CodeGen.
 * 
 * @author djelinek
 */
public class CodeGenRules extends RedDeerSWTGeneratorRules {

	public static final String BUTTON_PUSH_SUFFIX = "BTN";

	public static final String BUTTON_CHECK_SUFFIX = "CHB";

	public static final String BUTTON_RADIO_SUFFIX = "RDB";

	public static final String BUTTON_ARROW_SUFFIX = "ARR";

	public static final String BUTTON_TOGGLE_SUFFIX = "TGB";

	public static final String COMBO_SUFFIX = "CMB";

	public static final String SHELL_SUFFIX = "SHL";

	public static final String TEXT_SUFFIX = "TXT";

	@Override
	public List<GenerationSimpleRule> createSimpleRules() {
		List<GenerationSimpleRule> rules = new ArrayList<GenerationSimpleRule>();
		rules.add(new ButtonCodeGenRule());
		rules.add(new TextCodeGenRule());
		rules.add(new ComboCodeGenRule());
		rules.add(new ShellCodeGenRule());
		return rules;
	}

	public String getLabel() {
		return "RedDeer CodeGen SWT";
	}
}
