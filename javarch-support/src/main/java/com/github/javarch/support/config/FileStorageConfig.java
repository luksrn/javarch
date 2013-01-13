package com.github.javarch.support.config;
/*
 * Copyright 2010-2011 the original author or authors.
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

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import com.github.javarch.support.data.FileStorage;
import com.github.javarch.support.data.LocalFileStorage;
import com.github.javarch.support.spring.Profiles;

/**
 * Greenhouse file store configuration.
 * Used to store user profile pictures.
 * In embedded mode, we use local file storage with "delete on VM exit".
 * In standard mode, we use Amazon S3's file storage service.
 * 
 * @author Keith Donald
 */
@Configuration
public class FileStorageConfig {


	@Inject
	private Environment environment;

	@Inject
	private ResourceLoader resourceLoader;

	@Bean
	public FileStorage fileStorage() {
		String applicationUrl = environment.getProperty("application.url");
		LocalFileStorage pictureStorage = new LocalFileStorage(applicationUrl + "/resources/", resourceLoader.getResource("/resources/"));
		if ( environment.acceptsProfiles( Profiles.DEVELOPMENT, Profiles.TEST ) ){
			pictureStorage.setDeleteOnExit(true);	
		}
		return pictureStorage;
	}
}