package org.jboss.reddeer.swt.impl.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotViewMenu;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatchers;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;


/**
 * ToolbarMenu implementation
 * @author Rastislav Wagner
 *
 */
@SuppressWarnings("restriction")
public class ToolbarMenu extends AbstractMenu implements Menu{
	
	protected SWTBotViewMenu viewMenu;
	
	
	public ToolbarMenu(String... path){
		this(new WithMnemonicMatchers(path).getMatchers());
	}
	
	public ToolbarMenu(Matcher<String>... matchers){
		viewMenu = lookFor(getToolbarMenus(), matchers);
		this.matchers = matchers;
	}

	private List<IContributionItem> getToolbarMenus(){
		SWTBotView view = Bot.get().activeView();
		IWorkbenchPart obj = ((WorkbenchPartReference) view.getReference()).getPart(false);
		List<IContributionItem> menuContributionItems = new ArrayList<IContributionItem>();
		IMenuManager m = ((IViewSite) obj.getSite()).getActionBars().getMenuManager();
		if (m instanceof MenuManager) {
			menuContributionItems.addAll(Arrays.asList(((MenuManager) m).getItems()));
		}
		if(menuContributionItems.isEmpty()){
			throw new WidgetNotAvailableException("No Menu found in " +view.getTitle());
		}
		return menuContributionItems;
	}
	
	private SWTBotViewMenu lookFor(final List<IContributionItem> contItems, final Matcher<String>... matchers){
		SWTBotViewMenu botMenu = Display.syncExec(new ResultRunnable<SWTBotViewMenu>(){

			@Override
			public SWTBotViewMenu run() {
				SWTBotViewMenu currentItem = null;
				List<IContributionItem> currentMenuContributionItems = contItems;
				for (Matcher<String> m : matchers) {
					currentItem = null;
					for (IContributionItem i : currentMenuContributionItems) {
						if(i instanceof ActionContributionItem){
							String normalized = ((ActionContributionItem)i).getAction().getText().replace("&", "");
							log.debug("Found menu:" + normalized);
							if (m.matches(normalized)) {
								log.info("Item match:" + normalized);
								currentItem = new SWTBotViewMenu((ActionContributionItem)i);
								break;
							} 
						} else if(i instanceof MenuManager){
							String normalized =((MenuManager)i).getMenuText().replace("&", "");
							log.debug("Found Menu Manager:" + normalized);
							if (m.matches(normalized)) {
								log.info("Menu Manager match:" + normalized);
								currentMenuContributionItems = Arrays.asList(((MenuManager) i).getItems());
							}
						}
					}
			
				}
				return currentItem;
			}
		});
		if(botMenu == null){
			throw new WidgetNotFoundException("Menu item not found");
		}
		return botMenu;
	}
	
	



	@Override
	public void select() {
		viewMenu.click();
		
	}



	@Override
	public String getText() {
		return viewMenu.getText();
	}

	
}
