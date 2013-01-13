/*
* Copyright 2011 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License. You may obtain a copy of
* the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations under
* the License.
*/


package com.github.javarch.persistence.orm.hibernate.envers;

import java.io.Serializable;
import java.text.DateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.joda.time.DateTime;


/**
 * 
 * @author Lucas Oliveira
 *
 */
@Entity
@Table(name="revinfo",schema="audit")
@RevisionEntity(CustomRevisionEntityInforListener.class)
public class CustomRevisionEntityInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2934090155345850170L;

	@Id
	@GeneratedValue
	@RevisionNumber
	private int id;

	@RevisionTimestamp
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime date;

	private String username = "unknow";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public String toString() {
		return "CustomRevisionEntityInfo(id = " + id + ", revisionDate = " + DateFormat.getDateTimeInstance().format(date) + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomRevisionEntityInfo other = (CustomRevisionEntityInfo) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
}
