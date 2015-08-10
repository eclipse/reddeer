package org.jboss.reddeer.snippet.requirements.withparameters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jboss.reddeer.junit.requirement.Requirement;

public class UserRequirement implements Requirement<UserRequirement.User> {
	
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
		System.out.println("Fulfilling reuirement User with name: "
				+ user.name());
		// create an user in the database if user does not exist
	}

	public void cleanUp() {
		// perform clean up
	}

	public void setDeclaration(User user) {
		this.user = user;
	}
}
