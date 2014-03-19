package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;
import java.util.List;

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
   * Returns {@link List}  of {@link String}s
   * which are items in the Combo's list
   * 
   * @return {@link List} containing items from Combo's list
   */
  List<String> getItems();
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
