package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.group.DefaultGroup;

/** Class represents Perspectives preference page.
 * 
 * @author Vlado Pakan
 *
 */
public class PerspectivesPreferencePage extends PreferencePage {

	/** 
	 * Constructs the preference page with "General > Perspectives".
	 */
	public PerspectivesPreferencePage() {
		super(new String[] {"General", "Perspectives"});
	}

	/** 
	 * Returns Open a new perspective in the same window radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getOpenNewPerspectiveInSameWindowRadioButton() {
		return new RadioButton(new DefaultGroup("Open a new perspective"),
				"In the same window");
	}

	/** 
	 * Returns Open a new perspective in new window radio button.
	 *  
	 * @return RadioButton
	 */
	private RadioButton getOpenNewPerspectiveInNewWindowRadioButton() {
		return new RadioButton(new DefaultGroup("Open a new perspective"),
				"In a new window");
	}

	/** 
	 * Returns Open a new view within the perspective radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getOpenNewViewWithinPerspectiveRadioButton() {
		return new RadioButton(new DefaultGroup("Fast Views"),
				"Within the perspective");
	}

	/** 
	 * Returns Open a new view as fast view radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getOpenNewViewAsFastViewRadioButton() {
		return new RadioButton(new DefaultGroup("Fast Views"), "As fast view");
	}

	/** 
	 * Returns Always Open the associated perspective when creating a new
	 * project radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getAlwaysOpenAssociatedPerspectiveRadioButton() {
		return new RadioButton(new DefaultGroup(
				"Open the associated perspective when creating a new project"),
				"Always open");
	}

	/** 
	 * Returns Newer Open the associated perspective when creating a new project
	 * radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getNeverOpenAssociatedPerspectiveRadioButton() {
		return new RadioButton(new DefaultGroup(
				"Open the associated perspective when creating a new project"),
				"Never open");
	}

	/** 
	 * Returns Prompt Open the associated perspective when creating a new
	 * project radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getPromptOpenAssociatedPerspectiveRadioButton() {
		return new RadioButton(new DefaultGroup(
				"Open the associated perspective when creating a new project"),
				"Prompt");
	}

	/** 
	 * Returns Hide empty fast view bar check box.
	 * 
	 * @return CheckBox
	 */
	private CheckBox getHideEmptyFastViewBarCheckBox() {
		return new CheckBox(new DefaultGroup("Fast Views"),
				"Hide empty fast view bar ");
	}

	/** 
	 * Returns Open a new perspective in the same window value.
	 * 
	 * @return boolean
	 */
	public boolean isOpenNewPerspectiveInSameWindow() {
		return getOpenNewPerspectiveInSameWindowRadioButton().isSelected();
	}

	/** 
	 * Returns Open a new perspective in new window value.
	 * 
	 * @return boolean
	 */
	public boolean isOpenNewPerspectiveInNewWindow() {
		return getOpenNewPerspectiveInNewWindowRadioButton().isSelected();
	}

	/** 
	 * Returns Open a new view within the perspective value.
	 * 
	 * @return boolean
	 */
	public boolean isOpenNewViewWithinPerspective() {
		return getOpenNewViewWithinPerspectiveRadioButton().isSelected();
	}

	/** 
	 * Returns Open a new view as fast view value.
	 * 
	 * @return boolean
	 */
	public boolean isOpenNewViewAsFastView() {
		return getOpenNewViewAsFastViewRadioButton().isSelected();
	}

	/** 
	 * Returns Always Open the associated perspective when creating a new
	 * project value.
	 * 
	 * @return boolean
	 */
	public boolean isAlwaysOpenAssociatedPerspective() {
		return getAlwaysOpenAssociatedPerspectiveRadioButton().isSelected();
	}

	/** 
	 * Returns Newer Open the associated perspective when creating a new project
	 * value.
	 * 
	 * @return boolean
	 */
	public boolean isNeverOpenAssociatedPerspective() {
		return getNeverOpenAssociatedPerspectiveRadioButton().isSelected();
	}

	/** 
	 * Returns Prompt Open the associated perspective when creating a new
	 * project value.
	 * 
	 * @return boolean
	 */
	public boolean isPromptOpenAssociatedPerspective() {
		return getPromptOpenAssociatedPerspectiveRadioButton().isSelected();
	}

	/** 
	 * Returns Hide empty fast view bar value.
	 * 
	 * @return boolean
	 */
	public boolean isHideEmptyFastViewBar() {
		return getHideEmptyFastViewBarCheckBox().isChecked();
	}

	/** 
	 * Check Open a new perspective in the same window.
	 */
	public void checkOpenNewPerspectiveInSameWindow() {
		getOpenNewPerspectiveInSameWindowRadioButton().toggle(true);
	}

	/**
	 *  Checks Open a new perspective in new window.
	 */
	public void checkOpenNewPerspectiveInNewWindow() {
		getOpenNewPerspectiveInNewWindowRadioButton().toggle(true);
	}

	/**
	 *  Checks Open a new view within the perspective.
	 */
	public void checkOpenNewViewWithinPerspective() {
		getOpenNewViewWithinPerspectiveRadioButton().toggle(true);
	}

	/** 
	 * 
	 * Checks Open a new view as fast view.
	 */
	public void checkOpenNewViewAsFastView() {
		getOpenNewViewAsFastViewRadioButton().toggle(true);
	}

	/**
	 *  Checks Always Open the associated perspective when creating a new project.
	 */
	public void checkAlwaysOpenAssociatedPerspective() {
		getAlwaysOpenAssociatedPerspectiveRadioButton().toggle(true);
	}

	/** 
	 * Checks Newer Open the associated perspective when creating a new project.
	 */
	public void checkNeverOpenAssociatedPerspective() {
		getNeverOpenAssociatedPerspectiveRadioButton().toggle(true);
	}

	/** 
	 * Checks Prompt Open the associated perspective when creating a new project.
	 */
	public void checkPromptOpenAssociatedPerspective() {
		getPromptOpenAssociatedPerspectiveRadioButton().toggle(true);
	}

	/** 
	 * Sets Hide empty fast view bar value.
	 * 
	 * @param check - true to check Hide Empty Fast View Bar
	 */
	public void setHideEmptyFastViewBar(boolean check) {
		getHideEmptyFastViewBarCheckBox().toggle(check);
	}
}
