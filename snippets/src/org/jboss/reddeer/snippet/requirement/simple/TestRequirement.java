package org.jboss.reddeer.snippet.requirement.simple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.snippet.requirement.simple.TestRequirement.TestReq;

public class TestRequirement implements Requirement<TestReq> {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface TestReq{
    }

    public boolean canFulfill() {
        return true;
    }
    
    public void fulfill() {
        System.out.println("Fulfilling requirement.");
    }

    public void setDeclaration(TestReq declaration) {
    	// nothing to do
    }
    
    public void cleanUp() {
    	// nothing to do    	
    }
}