package org.jboss.reddeer.junit.test.integration.configuration;

import java.lang.annotation.Annotation;

import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

public class RequirementA implements Requirement<Annotation>, PropertyConfiguration {

	private String a;
	
	public boolean canFulfill() {
		return false;
	}

	public void fulfill() {
	}
	
	public void setA(String a) {
		this.a = a;
	}
	
	public String getA() {
		return a;
	}
	
	@Override
	public void setDeclaration(Annotation declaration) {
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}
}
