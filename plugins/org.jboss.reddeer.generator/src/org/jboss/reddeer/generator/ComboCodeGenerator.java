package org.jboss.reddeer.generator;

import static org.jboss.reddeer.generator.builder.MethodBuilder.method;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;
import org.jboss.reddeer.core.handler.WidgetHandler;

/**
 * Code generator for combo boxes.
 * 
 * @author apodhrad
 *
 */
public class ComboCodeGenerator implements CodeGenerator {

	@Override
	public boolean isSupported(Control control) {
		return control instanceof Combo;
	}

	@Override
	public String getConstructor(Control control) {
		if (!isSupported(control)) {
			throw new IllegalArgumentException("Given " + control.getClass() + "is not supported");
		}
		String type = "LabeledCombo";
		String label = WidgetHandler.getInstance().getLabel(control);
		if (label == null || label.isEmpty()) {
			type = "DefaultCombo";
			label = String.valueOf(WidgetUtils.getIndex(control));
		} else {
			label = "\"" + label + "\"";
		}
		String ref = RedDeerUtils.getReferencedCompositeString(RedDeerUtils.getComposites(control));
		return "new " + type + "(" + ref + label + ")";
	}

	@Override
	public String getGeneratedCode(Control control) {
		if (isSupported(control)) {
			// TODO you could rather parse the constructor to get the label
			String label = WidgetHandler.getInstance().getLabel(control);
			if (label == null || label.isEmpty()) {
				return null;
			}
			return method().type("Combo").get(label).returnCommand(getConstructor(control)).toString();
		}
		return null;
	}

}
