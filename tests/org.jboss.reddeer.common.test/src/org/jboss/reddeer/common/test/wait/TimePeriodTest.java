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
package org.jboss.reddeer.common.test.wait;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TimePeriodTest {

	private static String timePeriodFactor;

	private static boolean timePeriodFactorDefined = false;

	@BeforeClass
	public static void setup(){
		if (System.getProperties().containsKey(RedDeerProperties.TIME_PERIOD_FACTOR.getName())){
			timePeriodFactor = RedDeerProperties.TIME_PERIOD_FACTOR.getValue();			
			timePeriodFactorDefined = true;
		}
	}

	@AfterClass
	public static void cleanup(){
		if (timePeriodFactorDefined){
			System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), timePeriodFactor);
		} else {
			System.clearProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName());
		}
	}

	@Test
	public void getSeconds_factor1() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "1");
		TimePeriod.updateFactor();

		assertThat(TimePeriod.getCustom(5).getSeconds(), is(5L));
	}

	@Test
	public void getSeconds_factor1_period0() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "1");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(0).getSeconds(), is(0L));
	}
	
	@Test
	public void getSeconds_factor1_bignumber() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "1");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(Long.MAX_VALUE - 2).getSeconds(), is(Long.MAX_VALUE -2));
	}

	@Test
	public void getSeconds_factor1_periodmax() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "1");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(Long.MAX_VALUE).getSeconds(), is(Long.MAX_VALUE));
	}

	@Test
	public void getSeconds_factor_gt1() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "2");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(5).getSeconds(), is(10L));
	}

	@Test
	public void getSeconds_factor_gt1_period0() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "2");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(0).getSeconds(), is(0L));
	}
	
	@Test
	public void getSeconds_factor_gt1_bignumber() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "2");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(Long.MAX_VALUE - 2).getSeconds(), is(Long.MAX_VALUE));
	}

	@Test
	public void getSeconds_factor_gt1_periodmax() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "2");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(Long.MAX_VALUE).getSeconds(), is(Long.MAX_VALUE));
	}

	@Test
	public void getSeconds_factor_lt1() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "0.5");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(5).getSeconds(), is(3L));
	}
	
	@Test
	public void getSeconds_factor_lt1_period0() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "0.5");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(0).getSeconds(), is(0L));
	}
	
	@Test
	public void getSeconds_factor_lt1_smallnumber() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "0.00005");
		TimePeriod.updateFactor();
		
		assertThat(TimePeriod.getCustom(1).getSeconds(), is(0L));
	}
}
