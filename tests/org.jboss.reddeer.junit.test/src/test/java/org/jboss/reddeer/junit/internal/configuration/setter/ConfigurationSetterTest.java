package org.jboss.reddeer.junit.internal.configuration.setter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.lang.annotation.Annotation;

import org.jboss.reddeer.junit.internal.configuration.entity.Property;
import org.jboss.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.junit.Test;

public class ConfigurationSetterTest {

	private ConfigurationSetter setter = new ConfigurationSetter();
	
	@Test
	public void set_propertyBasedConfiguration() {
		PropertyBasedConfiguration configuration = new PropertyBasedConfiguration();
		configuration.getProperties().add(new Property("a", "1"));
		configuration.getProperties().add(new Property("b", "2"));
		
		PropertyBasedRequirement requirement = new PropertyBasedRequirement();
		
		setter.set(requirement, configuration);
		
		assertThat(requirement.getA(), is("1"));
		assertThat(requirement.getB(), is("2"));
	}
	
	public static class PropertyBasedRequirement implements Requirement<Annotation>, PropertyConfiguration{

		private String a;
		
		private String b;
		
		public boolean canFulfill() {
			return false;
		}

		public void fulfill() {
		}
		
		public void setA(String a) {
			this.a = a;
		}
		
		public String getA() {
			return a;
		}
		
		public void setB(String b) {
			this.b = b;
		}
		
		public String getB() {
			return b;
		}
		
		@Override
		public void setDeclaration(Annotation declaration) {
		}
	}
}
