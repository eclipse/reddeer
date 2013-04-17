package org.jboss.reddeer.uiforms.test.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

public class FormView extends ViewPart {

	private FormToolkit toolkit;
	private ScrolledForm form;
	
	@Override
	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);
		GridLayout layout = new GridLayout(1, true);
		form.getBody().setLayout(layout);
		
		createSectionWithHyperLink(form, "Section 1");
		createSectionWithHyperLink(form, "Section 2");
	}

	@Override
	public void setFocus() {
		form.setFocus();

	}
	
	private void createSectionWithHyperLink(ScrolledForm form, String text) {
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR);
		section.setText(text);
		
		Composite c = toolkit.createComposite(section);
		GridLayout l = new GridLayout(1, true);
		l.horizontalSpacing = 10;
		c.setLayout(l);
		
		toolkit.createText(c, "Value: ");
		toolkit.createHyperlink(c, text + " Hyperlink", SWT.None);
		
		section.setClient(c);
	}

}
