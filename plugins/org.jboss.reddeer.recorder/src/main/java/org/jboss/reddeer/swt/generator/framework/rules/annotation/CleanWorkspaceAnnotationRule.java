package org.jboss.reddeer.swt.generator.framework.rules.annotation;

import org.eclipse.swtbot.generator.framework.AnnotationRule;

public class CleanWorkspaceAnnotationRule extends AnnotationRule{
	
	public CleanWorkspaceAnnotationRule(){
		setAnnotation("CleanWorkspace");
		setImportText("org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspace");
		setClassAnnotation(true);
	}


}
