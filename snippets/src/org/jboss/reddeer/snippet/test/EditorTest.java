package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.api.Editor;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class EditorTest {

  @Test
  public void test(){
    //Currently active editor will gain focus or the first available
    Editor editor = new DefaultEditor(); 
    // some logic here
    // This will save&close currently active editor
    editor.close(true); 
  }
}