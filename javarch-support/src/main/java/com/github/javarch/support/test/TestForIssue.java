package com.github.javarch.support.test;


/**
* A documentation annotation for notating what JIRA/Redmine issue is being tested.
*
* @author Steve Ebersole (Hibernate)
*/
public @interface TestForIssue {
	String key();
}
