package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.Combo;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Combo lookup containing lookup routines for Combo widget type
 * @author jjankovi
 *
 */
public class ComboLookup {

  private static ComboLookup instance = null;
  
  private ComboLookup() {
  }
  
  /**
   * Creates and returns instance of Combo Lookup
   * @return ComboLookup instance
   */
  public static ComboLookup getInstance() {
    if (instance == null) instance = new ComboLookup();
    return instance;
  }
  
  /**
   * Return Combo instance
   * @param matcher
   * @return Combo Widget matching criteria
   */
  @SuppressWarnings({ "rawtypes" })
  public Combo getCombo(ReferencedComposite refComposite, int index, Matcher... matchers) {
    return (Combo)WidgetLookup.getInstance().activeWidget(refComposite, Combo.class, index, matchers);
  }
  
}