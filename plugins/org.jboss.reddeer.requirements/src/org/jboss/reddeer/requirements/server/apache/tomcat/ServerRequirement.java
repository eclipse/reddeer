package org.jboss.reddeer.requirements.server.apache.tomcat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.server.ConfiguredServerInfo;
import org.jboss.reddeer.requirements.server.IServerReqConfig;
import org.jboss.reddeer.requirements.server.ServerReqBase;
import org.jboss.reddeer.requirements.server.ServerReqState;
import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirement.ApacheTomcatServer;
import org.jboss.reddeer.swt.impl.text.DefaultText;


/**
 * 
 * @author Pavol Srna
 *
 */
public class ServerRequirement extends ServerReqBase 
			implements Requirement<ApacheTomcatServer>, CustomConfiguration<ServerRequirementConfig> {

	private static final Logger LOGGER = Logger.getLogger(ServerRequirement.class);
	
	
	private ServerRequirementConfig config;
	private static ConfiguredServerInfo lastServerConfiguration;
	private ApacheTomcatServer server;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface ApacheTomcatServer {
		ServerReqState state() default ServerReqState.RUNNING;
	}
	
	@Override
	public boolean canFulfill() {
		return true;
	}

	@Override
	public void fulfill() {
		
		if(lastServerConfiguration != null) {
			boolean differentConfig = !config.equals(lastServerConfiguration.getConfig());
			if(differentConfig) {
				removeLastRequiredServerAndRuntime(lastServerConfiguration);
				lastServerConfiguration = null;
			}
		}
		if(lastServerConfiguration == null || !isLastConfiguredServerPresent(lastServerConfiguration)) {
			LOGGER.info("Setup server");
			setupServerAdapter();
			lastServerConfiguration = new ConfiguredServerInfo(getServerNameLabelText(config), config);
		}
		setupServerState(server.state(), lastServerConfiguration);
	}

	@Override
	public String getServerTypeLabelText(IServerReqConfig config) {
		return config.getServerFamily().getLabel() + " v" + 
				config.getServerFamily().getVersion() + " Server";
	}
	
	@Override
	public String getServerNameLabelText(IServerReqConfig config) {
		return getServerTypeLabelText(config) + " at localhost";
	}

	@Override
	public String getRuntimeNameLabelText(IServerReqConfig config) {
		return config.getServerFamily().getCategory() + " " + 
				config.getServerFamily().getLabel() + " v" + 
				config.getServerFamily().getVersion() + " Runtime";
	}
	
	private void setupServerAdapter(){
		
		NewServerWizardDialog swd = new NewServerWizardDialog();
		swd.open();
		
		NewServerWizardPage swpage = new NewServerWizardPage();
		
		swpage.selectType(config.getServerFamily().getCategory(), getServerTypeLabelText(config));
		swpage.setName(getServerNameLabelText(config));
		swd.next();
		
		new DefaultText(0).setText(getRuntimeNameLabelText(config));
		new DefaultText(1).setText(config.getRuntime());
	
		swd.finish();
	}
	
	@Override
	public void setDeclaration(ApacheTomcatServer server) {
		this.server = server;
	}

	@Override
	public Class<ServerRequirementConfig> getConfigurationClass() {
		return ServerRequirementConfig.class;
	}

	@Override
	public void setConfiguration(ServerRequirementConfig config) {
		this.config = config;
	}

	public ServerRequirementConfig getConfig() {
		return this.config;
	}	
}