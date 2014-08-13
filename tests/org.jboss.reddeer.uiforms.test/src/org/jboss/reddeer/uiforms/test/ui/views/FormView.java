package org.jboss.reddeer.uiforms.test.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

public class FormView extends ViewPart {

	public static final String HYPERLINK_PREFIX = "Hyperlink ";
	
	public static final String FORMTEXT_PREFIX= "FormText ";
	
	public static final String ACTIVATED_HYPERLINK = "Hyperlink clicked";
	
	public static final String ACTIVATED_FORMTEXT = "FormText clicked";

	public static final String EXPANDABLE_COMPOSITE_A = "Expandable composite A";

	public static final String EXPANDABLE_COMPOSITE_B = "Expandable composite B";

	public static final String EXPANDABLE_COMPOSITE_C = "Expandable composite C";

	public static final String EXPANDABLE_COMPOSITE_D = "Expandable composite D";

	public static final String SECTION_A = "Section A";

	public static final String SECTION_B = "Section B";
	
	public static final String SECTION_C = "Section C";

	public static final String SECTION_D = "Section D";

	public static final String FORM_A_TITLE = "Form A";

	public static final String FORM_B_TITLE = "Form B";

	public static final String FORM_C_TITLE = "Form C";
	
	public static final String FORM_D_TITLE = "Form D";

	private FormToolkit toolkit;

	private ScrolledForm formA;

	private ScrolledForm formB;

	// in form B
	private ScrolledForm formC;

	// in form B
	private ScrolledForm formD;

	@Override
	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());

		formA = toolkit.createScrolledForm(parent);
		formA.setText(FORM_A_TITLE);
		formA.getBody().setLayout(new GridLayout(1, true));

		formB = toolkit.createScrolledForm(parent);
		formB.setText(FORM_B_TITLE);
		formB.getBody().setLayout(new GridLayout(1, true));

		formC = toolkit.createScrolledForm(formB.getBody());
		formC.setText(FORM_C_TITLE);

		formD = toolkit.createScrolledForm(formB.getBody());
		formD.setText(FORM_D_TITLE);
		
		createSectionWithHyperLink(formA, SECTION_A);
		createSectionWithHyperLink(formA, SECTION_B);
		
		createSectionWithHyperLink(formB, SECTION_C);
		createSectionWithHyperLink(formB, SECTION_D);

		createExpandableCompositeWithHyperLink(formA, EXPANDABLE_COMPOSITE_A);
		createExpandableCompositeWithHyperLink(formA, EXPANDABLE_COMPOSITE_B);

		createExpandableCompositeWithHyperLink(formB, EXPANDABLE_COMPOSITE_C);
		createExpandableCompositeWithHyperLink(formB, EXPANDABLE_COMPOSITE_D);
	}

	@Override
	public void setFocus() {
		formA.setFocus();

	}

	private void createExpandableCompositeWithHyperLink(final ScrolledForm form, String text) {
		ExpandableComposite composite = toolkit.createExpandableComposite(form.getBody(), ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.CLIENT_INDENT);
		composite.setText(text);

		Composite c = toolkit.createComposite(composite);
		GridLayout l = new GridLayout(1, true);
		l.horizontalSpacing = 10;
		c.setLayout(l);

		toolkit.createText(c, "Value: ");
		createHyperlink(text, c);
		createFormText(text, c);

		composite.setClient(c);

		composite.addExpansionListener(new ExpansionAdapter() {
			  public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			  }
		});

		composite.setExpanded(true);
	}

	private void createSectionWithHyperLink(ScrolledForm form, String text) {
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR);
		section.setText(text);

		Composite c = toolkit.createComposite(section);
		GridLayout l = new GridLayout(1, true);
		l.horizontalSpacing = 10;
		c.setLayout(l);

		toolkit.createText(c, "Value: ");
		createHyperlink(text, c);
		createFormText(text, c);

		section.setClient(c);
	}

	private void createHyperlink(String text, final Composite c) {
		final Hyperlink link = toolkit.createHyperlink(c, HYPERLINK_PREFIX + text, SWT.None);
		link.addHyperlinkListener(new IHyperlinkListener() {
			
			@Override
			public void linkExited(HyperlinkEvent e) {
			}
			
			@Override
			public void linkEntered(HyperlinkEvent arg0) {
			}
			
			@Override
			public void linkActivated(HyperlinkEvent arg0) {
				new Label(c, SWT.LEFT).setText(ACTIVATED_HYPERLINK);
				c.layout();
			}
		});
	}
	
	private void createFormText(String text, final Composite c) {
		final FormText formText = toolkit.createFormText(c, true);
		formText.setText("<form><p><a href=\"link\">" + FORMTEXT_PREFIX + text
				+ "</a></p></form>", true, false);
		formText.setToolTipText(FORMTEXT_PREFIX + text + "tooltip");
		formText.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				Object object = e.data;
				if (object instanceof String) {
					new Label(c, SWT.LEFT).setText(ACTIVATED_FORMTEXT);
					c.layout();
				}
			}
		});
	}

}
