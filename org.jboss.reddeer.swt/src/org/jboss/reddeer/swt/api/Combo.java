package org.jboss.reddeer.swt.api;

/**
 * API For Combo (Combobox) manipulation
 * @author Jiri Peterka
 *
 */
public interface Combo {
  /**
   * Sets combo text to str
   * @param str
   */
  public void setText(String str);
  /**
   * Returns combo text
   * @return
   */
  public String getText();
  /**
   * Sets combo selection to item with index
   * @param index
   */
  public void setSelection(int index);
  /**
   * Sets combo selection to selection
   * @param selection
   */
  public void setSelection(String selection);
  /**
   * Returns text of selected item within combo
   * @return
   */
  public String getSelection();
  /**
   * Returns index of selected item within combo
   * @return
   */
  public int getSelectionIndex();
  /**
   * Returns true when combo is enabled
   * @return
   */
  public boolean isEnabled();
  
}
