package org.jboss.reddeer.snippet.runner.parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.requirements.server.ServerReqState;
import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirement;
import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirement.ApacheTomcatServer;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
@ApacheTomcatServer(state = ServerReqState.RUNNING)
public class MyParameterizedTestClass {
	
	@InjectRequirement
	ServerRequirement req;

	@Parameters(name="{index}: {0}")
	public static Collection<String> data() {
		List<String> arrayList = new ArrayList<String>();
		arrayList.add("Test1");
		arrayList.add("Test2");
		arrayList.add("Test3");
		return arrayList;
	}
	private String argument;
	
	public MyParameterizedTestClass(String argument) {
		this.argument = argument;
	}

	@Test
	public void parameterizedTest1() {
		System.out.println("ParameterizedTest1 "+argument +" "+ req.getServerNameLabelText(req.getConfig()));
	}

	@Test
	public void parameterizedTest2() {
		System.out.println("ParameterizedTest2 "+argument+" "+ req.getServerNameLabelText(req.getConfig()));
	}

}
