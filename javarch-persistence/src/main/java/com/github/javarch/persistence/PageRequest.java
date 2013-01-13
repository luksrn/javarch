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
package com.github.javarch.persistence;

import org.springframework.util.Assert;

/**
 *  
 *  @author Lucas Oliveira
 *
 */
public class PageRequest {

	private int startingAt;
	
	private int maxPerPage;
	
	/**
	 * Set the first result to be retrieved and  a limit upon the number of objects to be retrieved.
	 *
	 * @param startingAt the first result to retrieve, numbered from <tt>0</tt>
	 * @param maxPerPage  the maximum number of results
	 */
	public PageRequest(int startingAt, int maxPerPage) {
		Assert.isTrue(startingAt >= 0, "startingAt must be >= 0");
		Assert.isTrue(maxPerPage >= 0, "maxPerPage must be >= 0");
		this.startingAt = startingAt;
		this.maxPerPage = maxPerPage;
	}

	public int getStartingAt() {
		return startingAt;
	}

	public int getMaxPerPage() {
		return maxPerPage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + maxPerPage;
		result = prime * result + startingAt;
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
		PageRequest other = (PageRequest) obj;
		if (maxPerPage != other.maxPerPage)
			return false;
		if (startingAt != other.startingAt)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageRequest [startingAt=").append(startingAt)
				.append(", maxPerPage=").append(maxPerPage).append("]");
		return builder.toString();
	}	
}
