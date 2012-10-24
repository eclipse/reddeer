package org.jboss.reddeer.swt.api;

/**
 * API For Combo (Combobox) manipulation
 * @author Jiri Peterka
 *
 */
public interface Combo {
  
  public void setText(String str);
  public void setSelection(int index);
  public void setSelection(String selection);
  public boolean isEnabled();
  
}
