package org.jboss.reddeer.junit.test.integration.runner;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequence;

public class RunnerIntegrationPropertyRequirement implements Requirement<Annotation> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface RequirementAAnnotation {
		
	}
	
	public boolean canFulfill() {
		return true;
	}
	
	public void fulfill() {
		TestSequence.addFulfill(RunnerIntegrationPropertyRequirement.class);
	}
	
	@Override
	public void setDeclaration(Annotation declaration) {
		TestSequence.addSetDeclaration(RunnerIntegrationPropertyRequirement.class);
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}
}
