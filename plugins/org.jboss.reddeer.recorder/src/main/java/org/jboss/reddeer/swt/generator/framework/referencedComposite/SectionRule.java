package org.jboss.reddeer.swt.generator.framework.referencedComposite;


public class SectionRule implements ReferencedComposite{
	
	private String text;
	
	public SectionRule(String text){
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public String getImport() {
		return "org.jboss.reddeer.uiforms.section.UIFormSection";
	}

}
