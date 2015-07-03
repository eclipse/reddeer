package org.jboss.reddeer.swt.test.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class LabelTestUtils {

	public static Label createLabel(Shell parent, String text){
		Label swtLabel= new Label(parent, SWT.LEFT);
		swtLabel.setText(text);
		swtLabel.setSize(100,30);
		return swtLabel;
	}

	public static Label createLabel(Shell parent, String text, Image image){
		Label swtLabel= createLabel(parent, text);
		swtLabel.setImage(image);
		return swtLabel;
	}
	
	public static CLabel createCLabel(Shell parent, String text){
		CLabel swtLabel= new CLabel(parent, SWT.LEFT);
		swtLabel.setText(text);
		swtLabel.setSize(100,30);
		return swtLabel;
	}
}
