package org.jboss.reddeer.snippet.requirement.simple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.snippet.requirement.simple.TestRequirementWithField.TestReqWithField;

public class TestRequirementWithField implements Requirement<TestReqWithField> {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface TestReqWithField{
        String name();
    }

    private TestReqWithField testReqWithField;

    public boolean canFulfill() {
        return true;
    }

    public void fulfill() {
        System.out.println("Fulfilling requirement with parameter: " + testReqWithField.name());
    }

    public void setDeclaration(TestReqWithField declaration) {
        this.testReqWithField = declaration;
    }
    
    public void cleanUp() {
    	// nothing to do
    }
}