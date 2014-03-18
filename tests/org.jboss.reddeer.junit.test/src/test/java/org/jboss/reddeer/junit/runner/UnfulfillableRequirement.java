package org.jboss.reddeer.junit.runner;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.Requirement;

public class UnfulfillableRequirement implements Requirement<Annotation> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface Unfulfillable {
		
	}
	
	public boolean canFulfill() {
		return false;
	}
	
	public void fulfill() {

	}

	@Override
	public void setDeclaration(Annotation declaration) {
		
	}
}
