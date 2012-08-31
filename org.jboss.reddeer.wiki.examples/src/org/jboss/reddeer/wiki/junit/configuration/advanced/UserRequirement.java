package org.jboss.reddeer.wiki.junit.configuration.advanced;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.wiki.junit.configuration.advanced.UserRequirement.User;


public class UserRequirement implements Requirement<User>, CustomConfiguration<UserConfiguration> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface User {
		String name();
	}
	
	private User user;
	
	private UserConfiguration userConfiguration;
	
	public boolean canFulfill() {
		// return true if you can connect to the database
		return true;
	}

	public void fulfill() {
		user.name();
		userConfiguration.getDbName();
		userConfiguration.getPort();
		userConfiguration.getIp();
		// create an admin user in the database if it does not exist yet
	}
	
	@Override
	public void setDeclaration(User user) {
		this.user = user;
	}
	
	@Override
	public Class<UserConfiguration> getConfigurationClass() {
		return UserConfiguration.class;
	}
	
	@Override
	public void setConfiguration(UserConfiguration config) {
		this.userConfiguration = config;
	}
}
