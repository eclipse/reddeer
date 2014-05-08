package org.jboss.reddeer.gef.finder;

import java.util.List;

import org.eclipse.gef.EditPart;

/**
 * 
 * @author apodhrad
 *
 */
public class EditPartFinder extends Finder<EditPart> {

	@SuppressWarnings("unchecked")
	@Override
	public List<EditPart> getChildren(EditPart child) {
		return child.getChildren();
	}

}
