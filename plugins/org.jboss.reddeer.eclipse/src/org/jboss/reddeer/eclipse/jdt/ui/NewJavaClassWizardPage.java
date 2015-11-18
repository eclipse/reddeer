package org.jboss.reddeer.eclipse.jdt.ui;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Wizard page for creating a java class.
 */
public class NewJavaClassWizardPage extends WizardPage {

	/**
	 * Instantiates a new new java class wizard page.
	 */
	public NewJavaClassWizardPage() {
		super();
	}
	
	/**
	 * Sets a given name.
	 * 
	 * @param name Name
	 */
	public void setName(String name){
		new LabeledText("Name:").setText(name);
	}
	
	/**
	 * Sets a given package name.
	 * 
	 * @param packageName Package name
	 */
	public void setPackage(String packageName) {
		new LabeledText("Package:").setText(packageName);
	}
	
	/**
	 * Sets a given source folder.
	 * 
	 * @param sourceFolder Source folder
	 */
	public void setSourceFolder(String sourceFolder){
		new LabeledText("Source folder:").setText(sourceFolder);
	}
	
	/**
	 * Sets generating static main method.
	 * 
	 * @param setMainMethod Indicates whether to generate static main method
	 */
	public void setStaticMainMethod(boolean setMainMethod) {
//		new CheckBox("public static void main(String[] args)").toggle(setMainMethod);
		new CheckBox(4).toggle(setMainMethod); // initiate with label doesnt work
	}
	
	/**
	 * Returns a package name.
	 * 
	 * @return package name
	 */
	public String getPackage(){
		return new LabeledText("Package:").getText();
	}
	
	/**
	 * Returns a class name.
	 * 
	 * @return Class name
	 */
	public String getName(){
		return new LabeledText("Name:").getText();
	}
	
	/**
	 * Returns a source folder.
	 * 
	 * @return Source folder
	 */
	public String getSourceFolder(){
		return new LabeledText("Source folder:").getText();
	}
}
