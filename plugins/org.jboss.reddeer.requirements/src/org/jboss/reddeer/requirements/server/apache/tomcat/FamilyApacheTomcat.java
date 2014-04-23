package org.jboss.reddeer.requirements.server.apache.tomcat;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.reddeer.requirements.server.IServerFamily;

/**
 * Server family: Apache Tomcat 
 * 
 * @author Pavol Srna
 *
 */
@XmlRootElement(name="familyApacheTomcat", namespace="http://www.jboss.org/NS/ServerReq")
public class FamilyApacheTomcat implements IServerFamily {

	private final String category = "Apache";
	
	private final String label = "Tomcat";
	
	@XmlAttribute(name="version")
	private String version;
	
	@Override
	public String getCategory() {
		return category;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getVersion() {
		return version;
	}
}
