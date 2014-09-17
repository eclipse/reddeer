package org.jboss.reddeer.junit.annotation.advanced;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.annotation.advanced.UserRequirement.User;
import org.jboss.reddeer.junit.requirement.Requirement;
/**
 * Parameterized requirement with paramer name 
 * @author vpakan
 *
 */
public class UserRequirement implements Requirement<User> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface User {
		String name();
	}
	
	private User user;
	
	public boolean canFulfill() {
		// return true if you can connect to the database
		return true;
	}

	public void fulfill() {
		System.out.println("Fulfilling reuirement User with name: " + user.name());
		// create an admin user in the database if it does not exist yet
	}
	
	public void setDeclaration(User user) {
		this.user = user;
	}
}
