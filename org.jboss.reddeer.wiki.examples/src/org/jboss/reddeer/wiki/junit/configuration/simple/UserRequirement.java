package org.jboss.reddeer.wiki.junit.configuration.simple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.wiki.junit.configuration.simple.UserRequirement.User;

public class UserRequirement implements Requirement<User>, PropertyConfiguration{

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface User {
		String name();
	}
	
	private User user;
	
	private String name;
	
	private String ip;
	
	public boolean canFulfill() {
		// return true if you can connect to the database
		return true;
	}

	public void fulfill() {
		user.name();
		// create an admin user in the database if it does not exist yet
	}
	
	@Override
	public void setDeclaration(User user) {
		this.user = user;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
}
