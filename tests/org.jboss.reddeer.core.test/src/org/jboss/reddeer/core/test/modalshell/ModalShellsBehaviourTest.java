/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.core.test.modalshell;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.handler.WorkbenchShellHandler;
import org.junit.After;
import org.junit.Test;

public class ModalShellsBehaviourTest {
	
	private static final String BUTTON3 = "button3";
	private static final String SHELL3 = "Shell3";
	private static final String BUTTON2 = "button2";
	private static final String SHELL2 = "Shell2";
	private static final String BUTTON1 = "button1";
	private static final String SHELL1 = "Shell1";

	@After
	public void closeShells(){
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();
	}
	
	@Test
	public void testApplicationModalShell(){
		testStyleModalShell(SWT.APPLICATION_MODAL);
	}
	@Test
	public void testApplicationModalShell1(){
		testStyleModalShell1(SWT.APPLICATION_MODAL);
	}
	@Test
	public void testApplicationModalShell2(){
		testStyleModalShell2(SWT.APPLICATION_MODAL);
	}
	@Test
	public void testApplicationModalShell3(){
		testStyleModalShell3(SWT.APPLICATION_MODAL);
	}
	@Test
	public void testApplicationModalShell4(){
		testStyleModalShell4(SWT.APPLICATION_MODAL);
	}
	@Test
	public void testApplicationModalShellOpening(){
		testStyleModalShellOpening(SWT.APPLICATION_MODAL);
	}
	
	
	@Test
	public void testSystemModalShell(){
		testStyleModalShell(SWT.SYSTEM_MODAL);
	}
	@Test
	public void testSystemModalShell1(){
		testStyleModalShell1(SWT.SYSTEM_MODAL);
	}
	@Test
	public void testSystemModalShell2(){
		testStyleModalShell2(SWT.SYSTEM_MODAL);
	}
	@Test
	public void testSystemModalShell3(){
		testStyleModalShell3(SWT.SYSTEM_MODAL);
	}
	@Test
	public void testSystemModalShell4(){
		testStyleModalShell4(SWT.SYSTEM_MODAL);
	}
	@Test
	public void testSystemModalShellOpening(){
		testStyleModalShellOpening(SWT.SYSTEM_MODAL);
	}
	
	
	public void testStyleModalShell(int swtStyle){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell s = new Shell();
				s.setText(SHELL1);
				s.setLayout(new RowLayout());
				Button b = new Button(s, SWT.PUSH);
				b.setText(BUTTON1);
				
				Shell s2 = new Shell(s);
				s2.setText(SHELL2);
				s2.setLayout(new RowLayout());
				b = new Button(s2, SWT.PUSH);
				b.setText(BUTTON2);
				
				Shell s3 = new Shell(s2, swtStyle);
				s3.setText(SHELL3);
				s3.setLayout(new RowLayout());
				b = new Button(s3, SWT.PUSH);
				b.setText(BUTTON3);
				s.open();
				s2.open();
				s3.open();
				
			}
		});
		
		//blocked by shell3
		boolean defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL1);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell1 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
		
		//blocked by shell3
		defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL2);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell2 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
		
		DefaultShell s3 = new DefaultShell(SHELL3);
		new PushButton(s3, BUTTON3).click();
	}
	
	public void testStyleModalShell1(int swtStyle){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell s = new Shell();
				s.setText(SHELL1);
				s.setLayout(new RowLayout());
				Button b = new Button(s, SWT.PUSH);
				b.setText(BUTTON1);
				
				Shell s2 = new Shell(s,swtStyle);
				s2.setText(SHELL2);
				s2.setLayout(new RowLayout());
				b = new Button(s2, SWT.PUSH);
				b.setText(BUTTON2);
				
				Shell s3 = new Shell(s2,swtStyle);
				s3.setText(SHELL3);
				s3.setLayout(new RowLayout());
				b = new Button(s3, SWT.PUSH);
				b.setText(BUTTON3);
				s.open();
				s2.open();
				s3.open();
				
			}
		});
		
		//blocked by shell2 and shell3
		boolean defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL1);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell1 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
		
		//blocked by shell2 and shell3
		defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL2);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell2 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
		
		DefaultShell s3 = new DefaultShell(SHELL3);
		new PushButton(s3, BUTTON3).click();
	}
	
	public void testStyleModalShell2(int swtStyle){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell s = new Shell();
				s.setText(SHELL1);
				s.setLayout(new RowLayout());
				Button b = new Button(s, SWT.PUSH);
				b.setText(BUTTON1);
				
				Shell s2 = new Shell(s,swtStyle);
				s2.setText(SHELL2);
				s2.setLayout(new RowLayout());
				b = new Button(s2, SWT.PUSH);
				b.setText(BUTTON2);
				
				Shell s3 = new Shell(s2);
				s3.setText(SHELL3);
				s3.setLayout(new RowLayout());
				b = new Button(s3, SWT.PUSH);
				b.setText(BUTTON3);
				s.open();
				s2.open();
				s3.open();
				
			}
		});
		
		//blocked by shell2
		boolean defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL1);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell1 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
		
		//ok, not blocked
		DefaultShell s2 = new DefaultShell(SHELL2);
		new PushButton(s2, BUTTON2).click();
		
		DefaultShell s3 = new DefaultShell(SHELL3);
		new PushButton(s3, BUTTON3).click();
	}
	
	public void testStyleModalShell3(int swtStyle){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell s = new Shell(swtStyle);
				s.setText(SHELL1);
				s.setLayout(new RowLayout());
				Button b = new Button(s, SWT.PUSH);
				b.setText(BUTTON1);
				
				Shell s2 = new Shell(s);
				s2.setText(SHELL2);
				s2.setLayout(new RowLayout());
				b = new Button(s2, SWT.PUSH);
				b.setText(BUTTON2);
				
				Shell s3 = new Shell(s2);
				s3.setText(SHELL3);
				s3.setLayout(new RowLayout());
				b = new Button(s3, SWT.PUSH);
				b.setText(BUTTON3);
				s.open();
				s2.open();
				s3.open();
				
			}
		});
		
		//ok, not blocked
		DefaultShell s1 =new DefaultShell(SHELL1);
		new PushButton(s1, BUTTON1).click();
		
		DefaultShell s2 = new DefaultShell(SHELL2);
		new PushButton(s2, BUTTON2).click();
		
		DefaultShell s3 = new DefaultShell(SHELL3);
		new PushButton(s3, BUTTON3).click();
	}
	
	public void testStyleModalShell4(int swtStyle){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell s = new Shell(swtStyle);
				s.setText(SHELL1);
				s.setLayout(new RowLayout());
				Button b = new Button(s, SWT.PUSH);
				b.setText(BUTTON1);
				
				Shell s2 = new Shell(s);
				s2.setText(SHELL2);
				s2.setLayout(new RowLayout());
				b = new Button(s2, SWT.PUSH);
				b.setText(BUTTON2);
				
				Shell s3 = new Shell(s2,swtStyle);
				s3.setText(SHELL3);
				s3.setLayout(new RowLayout());
				b = new Button(s3, SWT.PUSH);
				b.setText(BUTTON3);
				s.open();
				s2.open();
				s3.open();
				
			}
		});
		
		//blocked by shell3
		boolean defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL1);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell1 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
		
		//blocked by shell3
		defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL2);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell2 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
			
		//ok, not blocked
		DefaultShell s3 = new DefaultShell(SHELL3);
		new PushButton(s3, BUTTON3).click();
	}
	
	public void testStyleModalShellOpening(int swtStyle){
		Shell s2shell = Display.syncExec(new ResultRunnable<Shell>() {
			
			@Override
			public Shell run() {
				Shell s = new Shell();
				s.setText(SHELL1);
				s.setLayout(new RowLayout());
				Button b = new Button(s, SWT.PUSH);
				b.setText(BUTTON1);
				
				Shell s2 = new Shell(s);
				s2.setText(SHELL2);
				s2.setLayout(new RowLayout());
				b = new Button(s2, SWT.PUSH);
				b.setText(BUTTON2);
				
				s.open();
				s2.open();
				return s2;
				
			}
		});
		
		//ok
		DefaultShell s1 = new DefaultShell(SHELL1);
		PushButton b1 = new PushButton(s1, BUTTON1);
		b1.click();
		
		//ok
		DefaultShell s2 = new DefaultShell(SHELL2);
		PushButton b2 = new PushButton(s2, BUTTON2);
		b2.click();
			
				
		Display.syncExec(new Runnable() {
					
			@Override
			public void run() {
				Shell s3 = new Shell(s2shell, swtStyle);
				s3.setText(SHELL3);
				s3.setLayout(new RowLayout());
				Button b = new Button(s3, SWT.PUSH);
				b.setText(BUTTON3);
				s3.open();
			}
		});
		
		//blocked by shell3
		boolean defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL1);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell1 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
		
		boolean buttonClick = false;
		try{
			b1.click();
		} catch (CoreLayerException e) {
			//ok
			buttonClick = true;
		}
		assertTrue("button in shell1 was successfully clicked but shell1 is blocked by shell3!",buttonClick);
		
		//blocked by shell3
		defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL2);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell2 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
		
		buttonClick = false;
		try{
			b2.click();
		} catch (CoreLayerException e) {
			//ok
			buttonClick = true;
		}
		assertTrue("button in shell2 was successfully clicked but shell2 is blocked by shell3!",buttonClick);
		
		//ok
		DefaultShell s3 = new DefaultShell(SHELL3);
		PushButton b3 = new PushButton(s3, BUTTON3);
		b3.click();
		
		
	}
	
	@Test
	public void testPrimaryModalShell(){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell s = new Shell();
				s.setText(SHELL1);
				s.setLayout(new RowLayout());
				Button b = new Button(s, SWT.PUSH);
				b.setText(BUTTON1);
				
				Shell s2 = new Shell(s);
				s2.setText(SHELL2);
				s2.setLayout(new RowLayout());
				b = new Button(s2, SWT.PUSH);
				b.setText(BUTTON2);
				
				Shell s3 = new Shell(s2, SWT.PRIMARY_MODAL);
				s3.setText(SHELL3);
				s3.setLayout(new RowLayout());
				b = new Button(s3, SWT.PUSH);
				b.setText(BUTTON3);
				s.open();
				s2.open();
				s3.open();
				
			}
		});
		
		new PushButton(new DefaultShell(SHELL1), BUTTON1).click();
		new PushButton(new DefaultShell(SHELL3), BUTTON3).click();
		try{
			new PushButton(new DefaultShell(SHELL2), BUTTON2).click();
		} catch (CoreLayerException e) {
			//ok
			return;
		}
		fail("button2 cannot be clicked because shell2 is blocked by shell3");
		
	}
	
	@Test
	public void testPrimaryModalShellOpening(){
		Shell s2shell = Display.syncExec(new ResultRunnable<Shell>() {
			
			@Override
			public Shell run() {
				Shell s = new Shell();
				s.setText(SHELL1);
				s.setLayout(new RowLayout());
				Button b = new Button(s, SWT.PUSH);
				b.setText(BUTTON1);
				
				Shell s2 = new Shell(s);
				s2.setText(SHELL2);
				s2.setLayout(new RowLayout());
				b = new Button(s2, SWT.PUSH);
				b.setText(BUTTON2);
				
				s.open();
				s2.open();
				return s2;
				
			}
		});
		//ok
		DefaultShell s1 = new DefaultShell(SHELL1);
		PushButton b1 = new PushButton(s1, BUTTON1);
		b1.click();
		
		//ok
		DefaultShell s2 = new DefaultShell(SHELL2);
		PushButton b2 = new PushButton(s2, BUTTON2);
		b2.click();
		
		
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell s3 = new Shell(s2shell, SWT.PRIMARY_MODAL);
				s3.setText(SHELL3);
				s3.setLayout(new RowLayout());
				Button b = new Button(s3, SWT.PUSH);
				b.setText(BUTTON3);
				s3.open();
			}
		});
		
		//ok
		DefaultShell s3 = new DefaultShell(SHELL3);
		PushButton b3 = new PushButton(s3, BUTTON3);
		b3.click();
		
		//ok
		s1 = new DefaultShell(SHELL1);
		b1 = new PushButton(s1, BUTTON1);
		b1.click();
		
		
		//blocked by shell3
		boolean defaultShellInitExc = false;
		try{
			new DefaultShell(SHELL2);
		} catch (CoreLayerException e) {
			//ok
			defaultShellInitExc = true;
		}
		assertTrue("shell2 was succesfully focused but it is blocked by shell3!",defaultShellInitExc);
		
		boolean buttonClick = false;
		try{
			b2.click();
		} catch (CoreLayerException e) {
			//ok
			buttonClick = true;
		}
		assertTrue("button in shell2 was successfully clicked but shell2 is blocked by shell3!",buttonClick);
	}
	
}
