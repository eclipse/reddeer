package org.jboss.reddeer.eclipse.jdt.ui.preferences;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.table.DefaultTableItem;
import org.jboss.reddeer.swt.impl.text.LabeledText;
/**
 * Class represents Classpath Variables.
 * 
 * @author vlado pakan
 * @since 1.0
 */
public class ClasspathVariablesPreferencePage extends PreferencePage {
	/**
	 * Constructor for classpath variables preference in preference shell.
	 */
	public ClasspathVariablesPreferencePage() {
		super(new String[] {"Java", "Build Path", "Classpath Variables"});
	}
	/**
	 * Returns list with defined classpath variables 
	 */
	public List<String> getVariables (){
		LinkedList<String> libraries = new LinkedList<String>();
		for (TableItem ti :  getClasspathVariablesTable().getItems()){
			libraries.addLast(ti.getText());
		}
		return libraries;
	}
	/**
	 * Selects classpath variable matching matcher 
	 * @param matcher
	 */
	public void selectVariable (Matcher<?> matcher){
		new DefaultTableItem(getClasspathVariablesTable(),matcher).select();
	}
	/**
	 * Returns classpath variable table
	 * @return
	 */
	private Table getClasspathVariablesTable () {
		return new DefaultTable();
	}	
	/**
	 * Adds new classpath variable
	 * @param name
	 * @param value
	 * @param overwriteIfExists
	 * @return added classpath label
	 */
	public String addVariable(String name , String value , boolean overwriteIfExists){
		boolean externalJarExists = false;
		Iterator<TableItem> itTableItem = getClasspathVariablesTable().getItems().iterator();
		while (itTableItem.hasNext() && !externalJarExists) {
			TableItem tableItem = itTableItem.next();
			if (tableItem.getText().split(" - ")[0].equals(name)) {
				tableItem.select();
				externalJarExists = true;
			}
		}
		String variableSetShellTitle;
		String preferencesShellTitle = new DefaultShell().getText();
		if (externalJarExists && overwriteIfExists) {
			new PushButton("Edit...").click();	
			variableSetShellTitle = "Edit Variable Entry";
			new DefaultShell(variableSetShellTitle);	
			new LabeledText("Path:").setText(value);
		} else {
			new PushButton("New...").click();
			variableSetShellTitle = "New Variable Entry";
			new DefaultShell(variableSetShellTitle);		
			new LabeledText("Name:").setText(name);		
			new LabeledText("Path:").setText(value);
		}
		new OkButton().click();
		new WaitWhile(new ShellWithTextIsAvailable(variableSetShellTitle));
		new DefaultShell(preferencesShellTitle);
		return new DefaultTable().getSelectetItems().get(0).getText();
	}
	/**
	 * Removes classpath variable with label from classpath variables
	 * @param label
	 */
	public void removeVariable(String label){
		selectVariable(new WithTextMatcher(label));
		new PushButton("Remove").click();
	}
}
