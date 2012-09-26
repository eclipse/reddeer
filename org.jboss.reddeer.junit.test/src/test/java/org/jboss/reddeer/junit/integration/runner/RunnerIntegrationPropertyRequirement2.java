package org.jboss.reddeer.junit.integration.runner;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.Requirement;

public class RunnerIntegrationPropertyRequirement2 implements Requirement<Annotation> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface RequirementAAnnotation2 {
		
	}
	
	public boolean canFulfill() {
		return true;
	}
	
	public void fulfill() {
	}
	
	@Override
	public void setDeclaration(Annotation declaration) {
	}
}
