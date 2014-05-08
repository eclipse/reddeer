package org.jboss.reddeer.gef.test.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class ExampleWizard extends NewWizardDialog {

	public static final String FLOW_DIAGRAM = "Flow Diagram";
	public static final String FLOW_SUFFIX = ".flow";
	public static final String LOGIC_DIAGRAM = "Logic Diagram";
	public static final String LOGIC_SUFFIX = ".logic";
	public static final String SHAPES_DIAGRAM = "Shapes Diagram";
	public static final String SHAPES_SUFFIX = ".shapes";

	private String example;

	public ExampleWizard(String example) {
		super("Examples", example);
		this.example = example;
	}

	@Override
	public void open() {
		log.info("Opening wizard using top menu ");
		currentPage = -1;
		new ShellMenu(getMenuPath()).select();
		new DefaultShell(getDialogTitle());
		try {
			new DefaultTreeItem("Examples", example).select();
		} catch (Exception e) {
			new DefaultTreeItem("Examples", "GEF (Graphical Editing Framework)", example).select();
		}
		next();
	}

	public void setName(String name) {
		new LabeledText("File name:").setText(name);
	}

	public static void createFlowDiagram(String name) {
		ExampleWizard wizard = new ExampleWizard(FLOW_DIAGRAM);
		wizard.open();
		if (!name.endsWith(FLOW_SUFFIX)) {
			name += FLOW_SUFFIX;
		}
		wizard.setName(name);
		wizard.finish();
	}

	public static void createLogicDiagram(String name) {
		ExampleWizard wizard = new ExampleWizard(LOGIC_DIAGRAM);
		wizard.open();
		if (!name.endsWith(LOGIC_SUFFIX)) {
			name += LOGIC_SUFFIX;
		}
		wizard.setName(name);
		wizard.finish();
	}

	public static void createShapesDiagram(String name) {
		ExampleWizard wizard = new ExampleWizard(SHAPES_DIAGRAM);
		wizard.open();
		if (!name.endsWith(SHAPES_SUFFIX)) {
			name += SHAPES_SUFFIX;
		}
		wizard.setName(name);
		wizard.finish();
	}
}
