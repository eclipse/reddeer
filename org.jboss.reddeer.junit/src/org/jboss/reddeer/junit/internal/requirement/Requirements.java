package org.jboss.reddeer.junit.internal.requirement;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Aggregates {@link Requirement} objects and allows to perform tasks on them easily.
 *  
 * @author Lucia Jelinkova
 *
 */
public class Requirements implements Requirement<Annotation>, Iterable<Requirement<?>>{

	private List<Requirement<?>> requirements;
	private Logger log = Logger.getLogger(Requirements.class);
	
	/**
	 * Constructor
	 * @param requirements
	 */
	public Requirements(List<Requirement<?>> requirements) {
		super();
		if (requirements == null){
			throw new IllegalArgumentException("The requirements list was null");
		}
		this.requirements = requirements;
	}

	@Override
	public Iterator<Requirement<?>> iterator() {
		return requirements.iterator();
	}
	
	public int size(){
		return requirements.size();
	}
	
	@Override
	public boolean canFulfill() {
		boolean canFulfill = true;
		for (Requirement<?> r : requirements) {
			boolean canFulfillReq = r.canFulfill();
			log.info("Requirement " + r.getClass() + " can be fulfilled: " + canFulfillReq);
			canFulfill = canFulfill && r.canFulfill();
		}
		return canFulfill;
	}
	
	@Override
	public void fulfill() {
		for (Requirement<?> r : requirements) {
			log.info("Fulfilling requirement of " + r.getClass());
			r.fulfill();
		}
	}
	
	@Override
	public void setDeclaration(Annotation declaration) {
		throw new IllegalStateException("This method should never be called on wrapper object");
	}
}
