package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.ExpandBar;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Expand Bar lookup containing lookup routines for ExpandBar widget type
 * @author Vlado Pakan
 * @deprecated Since 1.0.0 use {@link AbstractWidget
 */
public class ExpandBarLookup {

  private static ExpandBarLookup instance = null;
  
  private ExpandBarLookup() {
  }
  
  /**
   * Creates and returns instance of ExpandBar Lookup
   * @return ExpandBarLookup instance
   */
  public static ExpandBarLookup getInstance() {
    if (instance == null) instance = new ExpandBarLookup();
    return instance;
  }
  
  /**
   * Returns ExpandBar instance
   * @param matcher
   * @return ExpandBar Widget matching criteria
   */
  @SuppressWarnings({ "rawtypes" })
  public ExpandBar getExpandBar(ReferencedComposite refComposite, int index, Matcher... matchers) {
    return (ExpandBar)WidgetLookup.getInstance().activeWidget(refComposite, ExpandBar.class, index, matchers);
  }
  
}