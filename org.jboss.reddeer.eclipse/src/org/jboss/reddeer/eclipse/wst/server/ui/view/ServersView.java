package org.jboss.reddeer.eclipse.wst.server.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizard;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.workbench.view.View;

/**
 * Represents the Servers view. This class contains methods that can be invoked even 
 * if no server is selected. You can invoke server specific operations on {@link Server} instances. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServersView extends View {
	
	public static final String TITLE = "Servers";

	public ServersView() {
		super(TITLE);
	}

	public NewServerWizard newServer(){
		open();
		new ContextMenu("New", "Server").select();
		return new NewServerWizard();
	}

	public List<Server> getServers(){
		List<Server> servers = new ArrayList<Server>();

		SWTBotTree tree;
		try {
			tree = new SWTBotTree(getServersTree());
		} catch (WidgetNotFoundException e){
			return new ArrayList<Server>();
		}
		for (SWTBotTreeItem item : tree.getAllItems()){
			servers.add(new Server(item.widget));
		}
		return servers;
	}

	public Server getServer(String name){
		for (Server server : getServers()){
			if (server.getName().equals(name)){
				return server;
			}
		}
		throw new EclipseLayerException("There is no server with name " + name);
	}

	public Tree getServersTree(){
		open();
		return ((Tree) Bot.get().widget(new ServerTreeMatcher()));
	}

	class ServerTreeMatcher extends TypeSafeMatcher<Widget> {

		@Override
		public void describeTo(Description description) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean matchesSafely(Widget item) {
			if (!(item instanceof Tree)) {
				return false;
			}

			return isInServerView((Tree) item);
		}

		private boolean isInServerView(Tree tree) {
			CTabFolder folder = findTabFolder(tree);
			if (folder == null){
				return false;
			}

			CTabItem tabItem = folder.getSelection();
			if (tabItem.getText().equals("Servers")){
				return true;
			}
			return false;
		}

		private CTabFolder findTabFolder(Composite composite){
			if (composite == null){
				return null;
			}

			if (composite instanceof CTabFolder) {
				return (CTabFolder) composite;
			}

			return findTabFolder(composite.getParent());
		}
	}
}
