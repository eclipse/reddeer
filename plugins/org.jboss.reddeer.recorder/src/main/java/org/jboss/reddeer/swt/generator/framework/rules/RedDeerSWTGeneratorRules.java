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
package org.jboss.reddeer.swt.generator.framework.rules;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.generator.framework.AnnotationRule;
import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.Generator;
import org.jboss.reddeer.swt.generator.framework.rules.annotation.CleanWorkspaceAnnotationRule;
import org.jboss.reddeer.swt.generator.framework.rules.annotation.TestAnnotationRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.CTabWorkbenchFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.CheckBoxFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.ComboComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.ListFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.TextComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.ToolBarMenuComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.TreeFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ButtonRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.CTabRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.CTabWorkbenchRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ComboRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ContextMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.HyperlinkRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.LinkRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ListRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TabRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TableRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TextRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ToolBarRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TreeRule;

public class RedDeerSWTGeneratorRules implements Generator{

	public List<GenerationSimpleRule> createSimpleRules() {
		List<GenerationSimpleRule> res = new ArrayList<GenerationSimpleRule>();
		res.add(new ButtonRule());
		res.add(new ShellMenuRule());
		res.add(new TreeRule());
		res.add(new ToolBarRule());
		res.add(new TextRule());
		res.add(new ComboRule());
		res.add(new TabRule());
		res.add(new TableRule());
		res.add(new ShellRule());
		res.add(new ContextMenuRule());
		res.add(new CTabWorkbenchRule());
		res.add(new ListRule());
		res.add(new HyperlinkRule());
		res.add(new LinkRule());
		res.add(new CTabRule());
		return res;
	}

	public String getLabel() {
		return "RedDeer SWT";
	}

	@Override
	public List<GenerationComplexRule> createComplexRules() {
		List<GenerationComplexRule> res = new ArrayList<GenerationComplexRule>();
		res.add(new ToolBarMenuComplexRule());
		res.add(new ComboComplexRule());
		res.add(new TextComplexRule());
		res.add(new TreeFilterComplexRule());
		res.add(new CheckBoxFilterComplexRule());
		res.add(new ListFilterComplexRule());
		res.add(new CTabWorkbenchFilterComplexRule());
		return res;
	}
/*
	@Override
	public List<GenerationStackRule> createStackRules() {
		return null;
	}
*/
	@Override
	public List<AnnotationRule> createAnnotationRules() {
		List<AnnotationRule> annotations = new ArrayList<AnnotationRule>();
		annotations.add(new TestAnnotationRule());
		annotations.add(new CleanWorkspaceAnnotationRule());
		return annotations;
	}

	public Image getImage() {
		InputStream is = getClass().getResourceAsStream("/icons/reddeer_logo.png");
		Image image = new Image(Display.getCurrent(), is);
		return image;
	}
	
}
