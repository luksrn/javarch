package com.github.javarch.support;

import static org.junit.Assert.*;

import org.junit.Test;

public class SlugGeneratorTest {

	SlugGenerator slug = new SlugGenerator();
	
	@Test
	public void test() {
		String encoded = slug.encode("A vovó ç e & também fez isso&teste");
		assertEquals("a-vovo-c-e-tambem-fez-isso-teste", encoded );
	}

}
