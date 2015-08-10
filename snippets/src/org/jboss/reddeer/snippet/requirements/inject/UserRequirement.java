package org.jboss.reddeer.snippet.requirements.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.snippet.requirements.inject.UserRequirement.User;

public class UserRequirement implements Requirement<User>, PropertyConfiguration{

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface User {
	}
	
	private String name;
	
	private String ip;
	
	private String password;
	
	public boolean canFulfill() {
		// return true if you can connect to the database
		return true;
	}

	public void fulfill() {
		System.out.println("Fulfilling User requirement with\nName: " + name + "\nIP: " + ip);
	}
	
	@Override
	public void setDeclaration(User user) {
		// annotation has no parameters no need to store reference to it
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	@Override
	public void cleanUp() {
		
	}
}
