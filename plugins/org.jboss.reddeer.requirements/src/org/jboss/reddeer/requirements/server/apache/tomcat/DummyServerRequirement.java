package org.jboss.reddeer.requirements.server.apache.tomcat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.server.ConfiguredServerInfo;
import org.jboss.reddeer.requirements.server.ServerReqBase;
import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirementConfig;
import org.jboss.reddeer.requirements.server.apache.tomcat.DummyServerRequirement.DummyServer;

public class DummyServerRequirement extends ServerReqBase
	implements Requirement<DummyServer>, CustomConfiguration<ServerRequirementConfig> {

	private static final Logger log = Logger.getLogger(DummyServerRequirement.class);
	
	private ServerRequirementConfig config;
	private DummyServer server;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DummyServer {
		
	}


	@Override
	public boolean canFulfill() {
		return true;
	}

	@Override
	public void fulfill() {
		log.info("Fulfilling requirement");
		log.info(this.server.annotationType().getAnnotations().toString());
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#setDeclaration(java.lang.annotation.Annotation)
	 */
	@Override
	public void setDeclaration(DummyServer server) {
		this.server = server;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.CustomConfiguration#getConfigurationClass()
	 */
	@Override
	public Class<ServerRequirementConfig> getConfigurationClass() {
		return ServerRequirementConfig.class;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.CustomConfiguration#setConfiguration(java.lang.Object)
	 */
	@Override
	public void setConfiguration(ServerRequirementConfig config) {
		this.config = config;
	}

	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	public ServerRequirementConfig getConfig() {
		return this.config;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ConfiguredServerInfo getConfiguredConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}
