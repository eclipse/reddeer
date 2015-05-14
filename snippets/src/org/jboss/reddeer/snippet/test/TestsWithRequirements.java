package org.jboss.reddeer.snippet.test;


import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.snippet.requirement.simple.TestRequirement.TestReq;
import org.jboss.reddeer.snippet.requirement.simple.TestRequirementWithField.TestReqWithField;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@TestReq
@TestReqWithField(name="testParameter")
public class TestsWithRequirements {
	
    @Test
    public void testCase1(){
        System.out.println("Executing test");
    }   
}