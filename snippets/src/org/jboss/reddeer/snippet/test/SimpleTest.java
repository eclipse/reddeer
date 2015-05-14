package org.jboss.reddeer.snippet.test;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.junit.Test;

public class SimpleTest {

	private Logger logger = new Logger(SimpleTest.class);
	
    @Test
    public void TestIt() {

        new ShellMenu("Help", "About Eclipse Platform").select();
        new PushButton("Installation Details").click();

        DefaultTree ConfigTree = new DefaultTree();
        List<TreeItem> ConfigItems = ConfigTree.getAllItems();

        assertFalse ("The list is empty!", ConfigItems.isEmpty());
        for (TreeItem item : ConfigItems) {
            logger.info("Found: " + item.getText());
        }
    }
}