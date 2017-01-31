/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.workbench.impl.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.part.WorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.text.contentassist.ContentAssistant;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.core.handler.MenuHandler;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.workbench.matcher.EditorPartTitleMatcher;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.workbench.api.Editor;
import org.jboss.reddeer.workbench.condition.ContentAssistantShellIsOpened;
import org.jboss.reddeer.workbench.core.lookup.EditorPartLookup;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.handler.EditorHandler;
import org.jboss.reddeer.workbench.handler.WorkbenchPartHandler;

/**
 * Abstract class for all Editor implementations.
 * @author rawagner
 */
public abstract class AbstractEditor implements Editor {

    protected static final Logger log = Logger.getLogger(AbstractEditor.class);
    
    protected IEditorPart editorPart;

    /**
     * Initialize editor.
     */
    public AbstractEditor() {
       editorPart = EditorPartLookup.getInstance().getEditor();
       activate();
    }

    /**
     * Initialize editor with given title and activate it.
     * @param title Title of editor to initialize and activate
     */
    public AbstractEditor(String title) {
        this(new WithTextMatcher(title));
    }

    /**
     * Initialize editor with given title matcher.
     * @param title Title matcher of editor to initialize and activate
     */
    @SuppressWarnings("unchecked")
	public AbstractEditor(Matcher<String> title) {
        this(new EditorPartTitleMatcher(title));
    }
    
    /**
     * Initialize editor that matches given matchers
     * @param matchers Matchers the editor should match
     */
    @SuppressWarnings("unchecked")
	public AbstractEditor(Matcher<IEditorPart>... matchers) {
    	this(EditorPartLookup.getInstance().getEditor(matchers));
    }
    
    protected AbstractEditor(IEditorPart part){
    	this.editorPart = part;
    	activate();
    }

    @Override
    public String getTitle() {
        return WorkbenchPartHandler.getInstance().getTitle(editorPart);
    }

    @Override
    public String getTitleToolTip() {
        return WorkbenchPartHandler.getInstance().getTitleToolTip(editorPart);
    }

    @Override
    public boolean isDirty() {
        return EditorHandler.getInstance().isDirty(editorPart);
    }

    @Override
    public void save() {
    	activate();
    	log.info("Save editor");
        EditorHandler.getInstance().save(editorPart);
    }

    @Override
    public void close() {
        close(false);
    }

    @Override
    public void close(final boolean save) {
    	activate();
    	if (save){
    		log.info("Close editor and save it");
    	} else {
    		log.info("Close editor and do not save it");
    	}
        EditorHandler.getInstance().close(save, editorPart);
    }
    
    @Override
    public boolean isActive() {
        return EditorHandler.getInstance().isActive(editorPart);
    }

    @Override
    public void maximize() {
    	activate();
    	log.info("Maximize editor");
        WorkbenchPartHandler.getInstance()
                .performAction(ActionFactory.MAXIMIZE);
    }

    @Override
    public void minimize() {
    	activate();
    	log.info("Minimize editor");
        WorkbenchPartHandler.getInstance()
                .performAction(ActionFactory.MINIMIZE);
    }

    /**
     * @see org.jboss.reddeer.workbench.api.Editor#openContentAssistant()
     */
    @Override
    public ContentAssistant openContentAssistant() {
    	Menu assistMenu = new ShellMenu("Edit", "Content Assist");
    	boolean hasAssistMenuChildren = MenuHandler.getInstance().getMenuFromMenuItem(assistMenu.getSWTWidget()) != null;
    	
    	if (hasAssistMenuChildren)
    		return openContentAssistant(ContentAssistantEnum.DEFAULT);
    	else
    		return openContentAssistant("Edit", "Content Assist");
    }
    
	public ContentAssistant openContentAssistant(ContentAssistantEnum assistantType) {
		return openContentAssistant(assistantType.getLabel());
	}

	public ContentAssistant openContentAssistant(String assistantLabel) {
		return openContentAssistant("Edit", "Content Assist", assistantLabel);
	}
	
    private ContentAssistant openContentAssistant(String... assistantMenuPath) {
    	String assistantLabel = assistantMenuPath[assistantMenuPath.length - 1];
    	
    	activate();
    	log.info("Open editor's " + assistantLabel + " content assistant");
        AbstractWait.sleep(TimePeriod.SHORT);
        Shell[] shells = ShellLookup.getInstance().getShells();
        
        ShellMenu menu;
        try {
        	menu = new ShellMenu(assistantMenuPath);        	
        } catch (Throwable e) {
        	throw new WorkbenchLayerException("Content assistant " + assistantLabel + " does not exist!", e);
		}
        
        if(!menu.isEnabled()){
        	throw new WorkbenchLayerException("Content assistant " + assistantLabel + " is disabled!");
        }
        
        menu.select();        
        ContentAssistantShellIsOpened caw = new ContentAssistantShellIsOpened(
                shells);
        new WaitUntil(caw);
        return new ContentAssistant(caw.getContentAssistTable());
    }
    
	/**
	 * Retrieves callable content assistants.
	 * @return List of assistants enums.
	 */
	public List<ContentAssistantEnum> getAvailableContentAssistants() {
    	activate();
        AbstractWait.sleep(TimePeriod.SHORT);

        List<Menu> availableMenus = new ShellMenu("Edit", "Content Assist").getAvailableChildItems();
        List<ContentAssistantEnum> result = new ArrayList<>(availableMenus.size());

        for (Menu availableMenu : availableMenus) {
        	String label = MenuHandler.getInstance().getLabelFromText(availableMenu.getText());
			ContentAssistantEnum assistantType = ContentAssistantEnum.resolveLabel(label);
			if(assistantType != null)
				result.add(assistantType);
		}
        
        return result;
    }

    /**
     * @see org.jboss.reddeer.workbench.api.Editor#openOpenOnAssistant()
     */
    @Override
    public ContentAssistant openOpenOnAssistant() {
    	activate();
    	log.info("Open editor's open on assistant");
        AbstractWait.sleep(TimePeriod.SHORT);
        Shell[] shells1 = ShellLookup.getInstance().getShells();
        new ShellMenu("Navigate", "Open Hyperlink").select();
        try {
            ContentAssistantShellIsOpened caw = new ContentAssistantShellIsOpened(
                    shells1);
            new WaitUntil(caw);
            return new ContentAssistant(caw.getContentAssistTable());
        } catch (WaitTimeoutExpiredException ex) {
            return null;
        }
    }

    /**
     * @see org.jboss.reddeer.workbench.api.Editor#openQuickFixContentAssistant()
     */
    @Override
    public ContentAssistant openQuickFixContentAssistant() {
    	activate();
    	log.info("Open editor's quick fix assistant");
        AbstractWait.sleep(TimePeriod.SHORT);
        Shell[] shells1 = ShellLookup.getInstance().getShells();
        IBindingService bindingService = (IBindingService) PlatformUI
                .getWorkbench().getAdapter(IBindingService.class);
        TriggerSequence[] sequence = bindingService
                .getActiveBindingsFor(ITextEditorActionDefinitionIds.QUICK_ASSIST);
        if (sequence.length > 0 && sequence[0].getTriggers().length > 0) {
            if (sequence[0].getTriggers()[0] instanceof KeyStroke) {
                KeyStroke k = ((KeyStroke) sequence[0].getTriggers()[0]);
                KeyboardFactory.getKeyboard().invokeKeyCombination(
                        k.getModifierKeys(), k.getNaturalKey());
            } else {
                throw new WorkbenchLayerException(
                        "Unable to find key combination which invokes Quickfix Content Assistant");
            }
        } else {
            throw new WorkbenchLayerException(
                    "Unable to find key combination which invokes Quickfix Content Assistant");
        }
        ContentAssistantShellIsOpened caw = new ContentAssistantShellIsOpened(
                shells1);
        new WaitUntil(caw);
        return new ContentAssistant(caw.getContentAssistTable());
    }

    /**
     * @see org.jboss.reddeer.workbench.api.Editor#getMarkers()
     */
    @Override
    public List<Marker> getMarkers() {
    	activate();
        return EditorHandler.getInstance().getMarkers(editorPart);
    }

    /**
     * {@link WorkbenchPart.restore}
     */
    @Override
    public void restore() {
        // in order to restore maximized window maximized action has to be
        // called
        activate();
        log.info("Restore editor");
        WorkbenchPartHandler.getInstance()
                .performAction(ActionFactory.MAXIMIZE);
    }

    /**
     * {@link WorkbenchPart.activate}
     */
    @Override
    public void activate() {
    	log.info("Activate editor with title " + getTitle());
        EditorHandler.getInstance().activate(editorPart);
    }

    /**
     * {@link Editor#closeAll}
     * @see org.jboss.reddeer.workbench.api.Editor#closeAll(boolean)
     */
	@Override
	public void closeAll(boolean save) {
		if (save){
			log.info("Close all editors and save them");			
		} else {
			log.info("Close all editors and do not save them");
		}
		EditorHandler.getInstance().closeAll(save);
		
	}
    /**
     * {@link Editor.getAutoContentAssistant}
     */
	public ContentAssistant getAutoContentAssistant(Runnable execute){
        Shell[] shells1 = ShellLookup.getInstance().getShells();
        execute.run();
        ContentAssistantShellIsOpened caw = new ContentAssistantShellIsOpened(
                shells1);
        new WaitUntil(caw,TimePeriod.NORMAL,false);
        return caw.getContentAssistTable() == null ? null : new ContentAssistant(caw.getContentAssistTable());
	}
	
    public IEditorPart getEditorPart() {
        return editorPart;
    }
    
    public enum ContentAssistantEnum {
    	DEFAULT("Default"),
    	JAVA_TYPE("Java Type Proposals"),
    	JAVA_NON_TYPE("Java Non-Type Proposals"),
    	JAVA("Java Proposals"),
    	TEMPLATE("Template Proposals"),
    	WORD("Word Proposals"),
    	SWT_TEMPLATE("SWT Template Proposals"),
    	JAXB("JAXB Proposals"),
    	JPA("JPA Proposals"),
    	JAX_RS("JAX-WS Proposals"),
    	API_TOOLS("API Tools Proposals"),
    	ADAPTIVE_TEMPLATE_RECOMMENDERS("Adaptive Template Proposals (Code Recommenders)"),
    	CHAIN_RECOMMENDERS("Chain Proposals (Code Recommenders)"),
    	JAVA_RECOMMENDERS("Java Proposals (Code Recommenders)"),
    	PARAMETER_HINTS("Parameter Hints");

    	private String label;

		public String getLabel() {
			return label;
		}

    	ContentAssistantEnum(String label) { 
    		this.label = label; 
    	}

    	public static ContentAssistantEnum resolveLabel(String label){
    		for (ContentAssistantEnum value : ContentAssistantEnum.values())
    			if (value.getLabel().equals(label)) 
    				return value;				
    		
    		return null;
    	}

    }
}
