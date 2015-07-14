package org.jboss.reddeer.snippet.runner.parameterized;

import java.util.Arrays;
import java.util.Collection;

import org.jboss.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

@RunWith(RedDeerSuite.class)
@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
public class SimplestParameterizedTest {
	
	@Parameters
	public static Collection<Object> data(){
		return Arrays.asList(new Object[] {1,2,3,4,5});
	}
	
	private int fInput;
	
	public SimplestParameterizedTest(int input) {
		fInput = input;
	}
	
	@Test
	public void test(){
		System.out.println(fInput);
	}

}
