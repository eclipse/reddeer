package org.jboss.reddeer.gef.finder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;

/**
 * 
 * @author apodhrad
 *
 */
public class PaletteEntryFinder extends Finder<PaletteEntry> {

	@SuppressWarnings("unchecked")
	@Override
	public List<PaletteEntry> getChildren(PaletteEntry child) {
		List<PaletteEntry> list = new ArrayList<PaletteEntry>();
		if (child instanceof PaletteContainer) {
			return ((PaletteContainer) child).getChildren();
		}
		return list;
	}

}
