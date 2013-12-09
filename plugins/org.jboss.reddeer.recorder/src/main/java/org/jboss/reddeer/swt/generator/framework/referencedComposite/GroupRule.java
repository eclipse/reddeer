package org.jboss.reddeer.swt.generator.framework.referencedComposite;


public class GroupRule implements ReferencedComposite{
	
	private String text;
	
	public GroupRule(String text){
		this.text = text;
	}
	
	@Override
	public String getText() {
		return text;
	}

	@Override
	public String getImport() {
		return "org.jboss.reddeer.swt.impl.group.DefaultGroup";
	}


}
