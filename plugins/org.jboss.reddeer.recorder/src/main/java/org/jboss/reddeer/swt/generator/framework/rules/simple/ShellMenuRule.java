package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;

public class ShellMenuRule extends GenerationSimpleRule{
	
	private List<String> path;
	private String menu;

	@Override
	public boolean appliesTo(Event event) {
		boolean menu = event.widget instanceof MenuItem;
		int style = 0;
		if(menu){
			MenuItem currentItem = ((MenuItem)event.widget);
			Menu parent = null;
			while (currentItem != null && (parent = currentItem.getParent()) != null) {
				style = parent.getStyle();
				currentItem = parent.getParentItem();
			}
		}
		return event.type == SWT.Selection && menu && (style & SWT.BAR)!=0;
	}

	@Override
	public void initializeForEvent(Event event) {
		MenuItem item = (MenuItem) event.widget;
		menu = WidgetUtils.cleanText(item.getText());
		path = new ArrayList<String>();
		MenuItem currentItem = item;
		Menu parent = null;
		while (currentItem != null && (parent = currentItem.getParent()) != null) {
			currentItem = parent.getParentItem();
			if (currentItem != null && currentItem.getText() != null) {
				path.add(WidgetUtils.cleanText(currentItem.getText()));
			}
		}
		Collections.reverse(path);
	}
	
	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder code = new StringBuilder();
		code.append("new ShellMenu(");
		for (String text : path) {
			code.append("\""+text+"\",");
		}
		code.append("\""+menu+"\").select()");
		toReturn.add(code.toString());
		return toReturn;
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.menu.ShellMenu");
		return toReturn;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShellMenuRule other = (ShellMenuRule) obj;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
}
