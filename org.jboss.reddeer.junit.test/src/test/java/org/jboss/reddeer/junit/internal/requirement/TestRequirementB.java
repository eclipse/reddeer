package org.jboss.reddeer.junit.internal.requirement;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.Requirement;

public class TestRequirementB implements Requirement<Annotation> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface TestRequirementBAnnotation {
		
	}
	
	private Annotation declaration;
	
	public boolean canFulfill() {
		return false;
	}
	
	public void fulfill() {

	}

	@Override
	public void setDeclaration(Annotation declaration) {
		this.declaration = declaration;
	}
	
	public Annotation getDeclaration() {
		return declaration;
	}
}
