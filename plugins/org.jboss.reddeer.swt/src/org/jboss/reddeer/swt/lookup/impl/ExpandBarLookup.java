package org.jboss.reddeer.swt.lookup.impl;

import org.eclipse.swt.widgets.ExpandBar;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

/**
 * Expand Bar lookup containing lookup routines for ExpandBar widget type
 * @author Vlado Pakan
 *
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
  public ExpandBar getExpandBar(int index, Matcher... matchers) {
    return (ExpandBar)WidgetLookup.getInstance().activeWidget(ExpandBar.class, index, matchers);
  }
  
}