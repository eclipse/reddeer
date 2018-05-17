/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.generator.framework.rules.annotation;

import org.eclipse.swtbot.generator.framework.AnnotationRule;

public class TestAnnotationRule extends AnnotationRule{
	
	public TestAnnotationRule(){
		setAnnotation("Test");
		setImportText("org.junit.Test");
	}

}
