package org.jboss.reddeer.junit.internal.configuration.reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.junit.Test;

public class XMLReaderTest {

	private static final String CONFIG_DIR_ROOT = "src/test/resources/org/jboss/reddeer/junit/internal/configuration/reader/";
	
	private XMLReader reader;
	
	@Test
	public void getConfiguration_simple() {
		reader = new XMLReader(new File(CONFIG_DIR_ROOT + "simple.xml"));
		List<?> result = reader.getConfiguration(SimpleXML.class);
		
		assertThat(result.size(), is(2));
		assertSimpleConfiguredObject(result.get(0), "c");
		assertSimpleConfiguredObject(result.get(1), "d");
	}
	
	@Test
	public void getConfiguration_namespace() {
		reader = new XMLReader(new File(CONFIG_DIR_ROOT + "namespace.xml"));
		List<?> result = reader.getConfiguration(NamespaceXML.class);
		
		assertThat(result.size(), is(1));
		assertNamespaceConfiguredObject(result.get(0), "c");
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void noRoot(){
		reader = new XMLReader(new File(CONFIG_DIR_ROOT + "namespace.xml"));
		
		reader.getConfiguration(NoRootConfiguration.class);
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void defaultName(){
		reader = new XMLReader(new File(CONFIG_DIR_ROOT + "namespace.xml"));
		
		reader.getConfiguration(DefaultNameConfiguration.class);
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void defaultNamespace(){
		reader = new XMLReader(new File(CONFIG_DIR_ROOT + "namespace.xml"));
		
		reader.getConfiguration(DefaultNamespaceConfiguration.class);
	}
	
	private void assertSimpleConfiguredObject(Object configObject, String expectedAttribute){
		assertThat(configObject, instanceOf(SimpleXML.class));
		assertThat(((SimpleXML) configObject).getAttribute(), is(expectedAttribute));
	}
	
	private void assertNamespaceConfiguredObject(Object configObject, String expectedAttribute){
		assertThat(configObject, instanceOf(NamespaceXML.class));
		assertThat(((NamespaceXML) configObject).getAttribute(), is(expectedAttribute));
	}
	
	public static class NoRootConfiguration {
		
	}
	
	@XmlRootElement(namespace="")
	public static class DefaultNameConfiguration {
		
	}
	
	@XmlRootElement(name="abc")
	public static class DefaultNamespaceConfiguration {
		
	}
}
