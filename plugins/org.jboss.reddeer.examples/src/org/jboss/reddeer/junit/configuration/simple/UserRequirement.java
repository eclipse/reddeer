package org.jboss.reddeer.junit.configuration.simple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.configuration.simple.UserRequirement.User;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
/**
 * User requirement using configuration from simple xml file
 * @author lucia jelinkova
 *
 */
public class UserRequirement implements Requirement<User>, PropertyConfiguration{

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface User {
	}
	private String name;
	
	private String ip;
	
	public boolean canFulfill() {
		// return true if you can connect to the database
		return true;
	}

	public void fulfill() {
		System.out.println("Fulfilling User requirement with\nName: " + name + "\nIP: " + ip);
		// create an admin user in the database if it does not exist yet
	}
	
	public void setDeclaration(User user) {
		// annotation has no parameters so no need to store it
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
}
