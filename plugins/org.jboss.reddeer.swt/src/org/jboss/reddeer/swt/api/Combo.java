package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For Combo (Combobox) manipulation
 * @author Jiri Peterka
 *
 */
public interface Combo extends Widget{
  /**
   * Sets combo text to str
   * @param str
   */
  void setText(String str);
  /**
   * Returns combo text
   * @return
   */
  String getText();
  /**
   * Sets combo selection to item with index
   * @param index
   */
  void setSelection(int index);
  /**
   * Sets combo selection to selection
   * @param selection
   */
  void setSelection(String selection);
  /**
   * Returns selected text in combo widget
   * @return
   */
  String getSelection();
  /**
   * Returns index of selected item within combo
   * @return
   */
  int getSelectionIndex();
  /**
   * Returns true when combo is enabled
   * @return
   */
  boolean isEnabled();
  
  org.eclipse.swt.widgets.Combo getSWTWidget();
  
}
