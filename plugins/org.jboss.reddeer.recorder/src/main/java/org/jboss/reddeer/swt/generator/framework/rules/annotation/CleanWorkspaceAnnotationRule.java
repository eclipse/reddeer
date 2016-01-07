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
package org.jboss.reddeer.swt.generator.framework.rules.annotation;

import org.eclipse.swtbot.generator.framework.AnnotationRule;

public class CleanWorkspaceAnnotationRule extends AnnotationRule{
	
	public CleanWorkspaceAnnotationRule(){
		setAnnotation("CleanWorkspace");
		setImportText("org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspace");
		setClassAnnotation(true);
	}


}
