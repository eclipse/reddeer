package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.custom.CTabItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * CTabItem lookup containing lookup routines for CTabItem widget type
 * @author jjankovi
 * @deprecated Since 1.0.0 use {@link AbstractWidget
 *
 */
public class CTabItemLookup {

  private static CTabItemLookup instance = null;
  
  private CTabItemLookup() {
  }
  
  /**
   * Creates and returns instance of Combo Lookup
   * @return ComboLookup instance
   */
  public static CTabItemLookup getInstance() {
    if (instance == null) instance = new CTabItemLookup();
    return instance;
  }
  
  /**
   * Return Combo instance
   * @param matcher
   * @return Combo Widget matching criteria
   */
  @SuppressWarnings({ "rawtypes" })
  public CTabItem getCTabItem(ReferencedComposite refComposite, int index, Matcher... matchers) {
    return (CTabItem)WidgetLookup.getInstance().activeWidget(refComposite, CTabItem.class, index, matchers);
  }
  
}