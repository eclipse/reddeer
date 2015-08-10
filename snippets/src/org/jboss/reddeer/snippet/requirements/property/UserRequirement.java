package org.jboss.reddeer.snippet.requirements.property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.snippet.requirements.property.UserRequirement.User;
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

	@Override
	public void cleanUp() {
		// perform clenaup if needed
	}
}
