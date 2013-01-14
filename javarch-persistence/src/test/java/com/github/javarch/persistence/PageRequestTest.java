/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.javarch.persistence;

import org.junit.Test;

import com.github.javarch.support.Assert;

/**
 * Test case para classe PageRequest
 * 
 * @author lucas
 *
 */
public class PageRequestTest {

	@Test
	public void testNull(){
		PageRequest p = new PageRequest(0, 0);	
		Assert.isTrue( p.getMaxPerPage() == 0);
		Assert.isTrue( p.getStartingAt() == 0);
	}
	
	@Test(expected=IllegalArgumentException.class)		
	public void testValoresInvalidosStartingAt(){
		new PageRequest(-1, 30);		
	}
	
	@Test(expected=IllegalArgumentException.class)		
	public void testValoresInvalidosMaxPerPage(){
		new PageRequest(1, -30);		
	}
	
	@Test
	public void testCasoSucesso(){
		PageRequest p = new PageRequest(10, 30);	
		Assert.isTrue( p.getMaxPerPage() == 30);
		Assert.isTrue( p.getStartingAt() == 10);
	}
}
