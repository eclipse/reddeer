package org.jboss.reddeer.snippet.requirements.simple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.snippet.requirements.simple.AdminUserRequirement.AdminUser;

public class AdminUserRequirement implements Requirement<AdminUser> {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface AdminUser {
    }

	public void setDeclaration(AdminUser declaration) {
		// no need to access the annotation
	}

	public boolean canFulfill() {
		 // return true if it is possible to connect to database, false otherwise
        return true;
	}

	public void fulfill() {
		// create an admin user in the database if admin user does not exists
		
	}

	public void cleanUp() {
		// perform clean up
	}
}